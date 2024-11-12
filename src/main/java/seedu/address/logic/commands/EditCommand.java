package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameMatchesKeywordPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.role.Role;
import seedu.address.model.wedding.Client;
import seedu.address.model.wedding.Wedding;

/**
 * Edits the details of an existing person in the address book.
 * The details that can be edited are name, phone, email and address.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) or KEYWORD (the name of contact) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] \n"
            + "Example: \n"
            + COMMAND_WORD + " 1 " + PREFIX_PHONE + "91234567 " + PREFIX_EMAIL + "johndoe@example.com\n"
            + COMMAND_WORD + " John " + PREFIX_NAME + "John Tan " + PREFIX_PHONE + "91238923";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_NO_CHANGE = "Fields provided are the same as before, no edit is made.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_PHONE = "This number already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_EMAIL = "This email already exists in the address book.";
    public static final String MESSAGE_EDIT_EMPTY_LIST_ERROR = "There is no contact to edit.";
    public static final String MESSAGE_DUPLICATE_HANDLING =
            "Please specify the index of the contact you want to edit.\n"
                    + "Find the index from the list below and type edit INDEX ...\n"
                    + "Example: " + COMMAND_WORD + " 1 ...";

    private final Index index;
    private final NameMatchesKeywordPredicate predicate;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * Creates a {@code EditCommand} object to edit the person at the specified {@code Index} or keyword.
     *
     * @param index {@code Index} of the person in the filtered person list to delete.
     * @param predicate {@code NameMatchesKeywordPredicate} used to filter the person list to find the target person.
     * @param editPersonDescriptor details of what is to be edited in the person.
     */
    public EditCommand(Index index, NameMatchesKeywordPredicate predicate, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.predicate = predicate;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToEdit;

        if (this.index != null) {
            personToEdit = editWithIndex(model);
        } else {
            personToEdit = editWithKeyword(model);
        }

        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (personToEdit.isSamePerson(editedPerson)) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            throw new CommandException(MESSAGE_NO_CHANGE);
        }

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        if (!personToEdit.getPhone().equals(editedPerson.getPhone()) && model.hasPhone(editedPerson)) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            throw new CommandException(MESSAGE_DUPLICATE_PHONE);
        }

        if (!personToEdit.getEmail().equals(editedPerson.getEmail()) && model.hasEmail(editedPerson)) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            throw new CommandException(MESSAGE_DUPLICATE_EMAIL);
        }

        model.setPerson(personToEdit, editedPerson);
        Wedding ownWedding = editedPerson.getOwnWedding();
        if (ownWedding != null) {
            Wedding editedWedding = new Wedding(ownWedding.getName(), new Client(editedPerson), ownWedding.getDate(),
                    ownWedding.getVenue());
            model.setWedding(ownWedding, editedWedding);
            model.updatePersonEditedWedding(ownWedding, editedWedding);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Returns the target person to edit, by index.
     *
     * @param model {@code Model} which the command should operate on.
     * @return the person to be edited.
     * @throws CommandException if the list is empty or if the index is invalid.
     */
    public Person editWithIndex(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_EDIT_EMPTY_LIST_ERROR);
        }

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(
                    String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                            index.getOneBased(), lastShownList.size()));
        }

        return lastShownList.get(index.getZeroBased());
    }

    /**
     * Returns the target person to edit, by keyword.
     *
     * @param model {@code Model} which the command should operate on.
     * @return the person to be edited.
     * @throws CommandException if the filtered list using {@code predicate} is empty or contains more than 1 element.
     */
    public Person editWithKeyword(Model model) throws CommandException {
        model.updateFilteredPersonList(predicate);
        List<Person> filteredList = model.getFilteredPersonList();

        if (filteredList.isEmpty()) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            throw new CommandException(MESSAGE_EDIT_EMPTY_LIST_ERROR);
        } else if (filteredList.size() == 1) {
            return filteredList.get(0);
        } else {
            throw new CommandException(MESSAGE_DUPLICATE_HANDLING);
        }
    }

    /**
     * Returns a {@code Person} with updated name, phone, email or address.
     *
     * @param personToEdit the target person.
     * @param editPersonDescriptor details of what is to be edited in the person.
     * @return updated {@code Person} with the edited information.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Optional<Role> updatedRole = personToEdit.getRole();
        Wedding ownWedding = personToEdit.getOwnWedding();
        Set<Wedding> weddingJobs = personToEdit.getWeddingJobs();

        Person editedPerson = new Person(updatedName, updatedPhone, updatedEmail, updatedAddress,
                updatedRole, ownWedding);
        editedPerson.setWeddingJobs(weddingJobs);
        return editedPerson;
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
        return index.equals(otherEditCommand.index)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
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
        private Role role;
        private Set<Wedding> weddingJobs;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code role} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setRole(toCopy.role);
            setWeddings(toCopy.weddingJobs);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address);
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
         * Sets {@code role} to this object's {@code role}.
         * A defensive copy of {@code role} is used internally.
         *
         * @param role the role to be set.
         */
        public void setRole(Role role) {
            this.role = role;
        }

        /**
         * Returns an unmodifiable role set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code role} is null.
         */
        public Optional<Role> getRole() {
            return (role != null) ? Optional.of(role) : Optional.empty();
        }

        public void setWeddings(Set<Wedding> weddingJobs) {
            this.weddingJobs = weddingJobs;
        }

        public Optional<Set<Wedding>> getWeddingJobs() {
            return (weddingJobs != null) ? Optional.of(Collections.unmodifiableSet(weddingJobs)) : Optional.empty();
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
                    && Objects.equals(address, otherEditPersonDescriptor.address);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("role", role != null ? role.toString() : "")
                    .toString();
        }
    }
}
