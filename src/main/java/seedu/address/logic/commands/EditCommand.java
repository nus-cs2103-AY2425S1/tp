package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WEDDINGS;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Job;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String COMMAND_FUNCTION = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the name used in the displayed person list.\n"
            + "Existing values will be overwritten by the input values.";

    public static final String MESSAGE_USAGE = COMMAND_FUNCTION
            + "\nParameters: " + PREFIX_NAME + "NAME "
            + "[" + PREFIX_NEW_NAME + "NEW_NAME] "
            + "[" + PREFIX_PHONE + "NEW_PHONE] "
            + "[" + PREFIX_EMAIL + "NEW_EMAIL] "
            + "[" + PREFIX_ADDRESS + "NEW_ADDRESS] "
            + "[" + PREFIX_JOB + "NEW_JOB]\n"
            + "Example: " + COMMAND_WORD + " n/Jonus " + PREFIX_NEW_NAME + "Ernus "
            + PREFIX_PHONE + "81234562 "
            + PREFIX_EMAIL + "notsiriouslyhensum@gmail.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_NO_MATCH_FOUND = "No contact with the name '%1$s' found.";
    public static final String MESSAGE_TAG_UNEDITABLE = "The tag of a contact cannot be edited.";

    private final Name currentName;
    private final Name newName;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param currentName of the person in the filtered person list to edit
     * @param newName new name of the person
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Name currentName, Name newName, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(currentName);
        requireNonNull(editPersonDescriptor);

        this.currentName = Objects.requireNonNull(currentName, "Current name cannot be null");
        this.newName = newName;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        Person personToEdit = model.getFilteredPersonList().stream()
                .filter(person -> person.getName().equals(currentName))
                .findFirst()
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_NO_MATCH_FOUND, currentName)));

        Person editedPerson = createEditedPerson(personToEdit, newName, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.updatePersonInWedding(personToEdit, editedPerson);
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredWeddingList(PREDICATE_SHOW_ALL_WEDDINGS);

        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, Name newName,
                                     EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = newName != null ? newName : editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Job updatedJob = editPersonDescriptor.getJob().orElse(personToEdit.getJob());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedJob, personToEdit.getTags());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return currentName.equals(otherEditCommand.currentName)
                && Objects.equals(newName, otherEditCommand.newName)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("currentName", currentName)
                .add("newName", newName)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Job job;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setJob(toCopy.job);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, job);
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

        public void setJob(Job job) {
            this.job = job;
        }

        public Optional<Job> getJob() {
            return Optional.ofNullable(job);
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
                    && Objects.equals(job, otherEditPersonDescriptor.job);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("job", job)
                    .toString();
        }
    }
}
