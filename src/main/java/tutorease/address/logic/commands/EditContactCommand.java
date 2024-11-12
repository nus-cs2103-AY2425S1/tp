package tutorease.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorease.address.logic.Messages.MESSAGE_DUPLICATE_EMAIL;
import static tutorease.address.logic.Messages.MESSAGE_DUPLICATE_PHONE;
import static tutorease.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_NAME;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_TAG;
import static tutorease.address.model.Model.PREDICATE_SHOW_ALL_LESSONS;
import static tutorease.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import tutorease.address.commons.core.LogsCenter;
import tutorease.address.commons.core.index.Index;
import tutorease.address.commons.util.CollectionUtil;
import tutorease.address.commons.util.ToStringBuilder;
import tutorease.address.logic.Messages;
import tutorease.address.logic.commands.exceptions.CommandException;
import tutorease.address.model.Model;
import tutorease.address.model.person.Address;
import tutorease.address.model.person.Email;
import tutorease.address.model.person.Guardian;
import tutorease.address.model.person.Name;
import tutorease.address.model.person.Person;
import tutorease.address.model.person.Phone;
import tutorease.address.model.person.Role;
import tutorease.address.model.person.Student;
import tutorease.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditContactCommand extends ContactCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = ContactCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_ROLE + "ROLE] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + " " + ContactCommand.COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_ROLE_CANNOT_BE_EDITED = "Role cannot be changed!";
    private static Logger logger = LogsCenter.getLogger(EditContactCommand.class);
    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;


    /**
     * Creates an EditContactCommand to edit the details of a person in the filtered person list.
     *
     * @param index The index of the person in the filtered person list.
     * @param editPersonDescriptor A descriptor containing the details to update the person with.
     * @throws NullPointerException If {@code index} or {@code editPersonDescriptor} is null.
     */
    public EditContactCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        assert index != null : "index cannot be null";
        assert editPersonDescriptor != null : "Edit Person Descriptor cannot be null";

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
        logger.log(Level.INFO, this.toString());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.log(Level.INFO, "Executing EditContactCommand");
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            logger.warning("Invalid index: " + index.getZeroBased() + " - out of bounds for current list size");
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        assert personToEdit != null : "Person to edit cannot be null";
        assert editedPerson != null : "Edited person cannot be null";

        hasDuplicates(personToEdit, editedPerson, model);

        model.setPerson(personToEdit, editedPerson);
        logger.info("Person updated in model: " + editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);

        String formattedString = String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));
        logger.log(Level.INFO, formattedString);
        return new CommandResult(formattedString);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor)
            throws CommandException {
        assert personToEdit != null : "Person to edit cannot be null";

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Role role = personToEdit.getRole();
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        if (role.getRoleString().equals(Role.STUDENT)) {
            return new Student(updatedName, updatedPhone, updatedEmail, updatedAddress, role, updatedTags);
        }
        if (role.getRoleString().equals(Role.GUARDIAN)) {
            return new Guardian(updatedName, updatedPhone, updatedEmail, updatedAddress, role, updatedTags);
        }

        throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditContactCommand.MESSAGE_ROLE_CANNOT_BE_EDITED));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditContactCommand)) {
            return false;
        }

        EditContactCommand otherEditCommand = (EditContactCommand) other;

        boolean isIndexEqual = index.equals(otherEditCommand.index);
        boolean isEditPersonDescriptorEqual = editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);

        logger.log(Level.INFO, "Comparing EditContactCommand: " + this + " with " + otherEditCommand);
        logger.log(Level.INFO, "Comparing index: " + isIndexEqual + " Comparing edit person descriptor: "
                + isEditPersonDescriptorEqual);

        return isIndexEqual && isEditPersonDescriptorEqual;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
    * Checks if the edited person would create a duplicate entry in the model based on unique attributes.
    * This method verifies that the edited person's details do not conflict with existing persons in the model.
    *
    * @param personToEdit The original person to be edited.
    * @param editedPerson The person with updated details.
    * @param model The model containing the list of existing persons.
    * @throws CommandException if the edited person would duplicate another person in the model.
    *      - Throws {@code CommandException} with {@code MESSAGE_DUPLICATE_PERSON} if a person with the same details as
    *      {@code editedPerson} already exists in the model (excluding {@code personToEdit}).
    *      - Throws {@code CommandException} with {@code MESSAGE_DUPLICATE_EMAIL} if another person in the model has the
    *      same email as {@code editedPerson}.
    *      - Throws {@code CommandException} with {@code MESSAGE_DUPLICATE_PHONE} if another person in the model has the
    *      same phone number as {@code editedPerson}.
    */
    public void hasDuplicates(Person personToEdit, Person editedPerson, Model model) throws CommandException {
        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        if (editPersonDescriptor.email != null && model.hasSameEmail(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_EMAIL);
        }

        if (editPersonDescriptor.phone != null && model.hasSamePhone(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PHONE);
        }
    }

    /**
     * Stores the details to edit the person with.
     * Each non-empty field value will replace the corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .toString();
        }
    }
}
