package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.ConfirmationHandler;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Frequency;
import seedu.address.model.person.LastPaidDate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfilePicFilePath;
import seedu.address.model.tag.Tag;
import seedu.address.ui.DuplicateWarningWindow;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_BIRTHDAY + "BIRTHDAY] "
            + "[" + PREFIX_TAG + "TAG]..."
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    private ConfirmationHandler confirmationHandler;


    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
        this.confirmationHandler = new DefaultConfirmationHandler();
    }

    /**
     * Overloaded constructor for testing purposes
     * @param index
     * @param editPersonDescriptor
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor,
                       ConfirmationHandler confirmationHandler) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
        this.confirmationHandler = confirmationHandler;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            boolean isConfirmed = confirmationHandler.confirm(editedPerson);
            if (!isConfirmed) {
                throw new CommandException(Messages.MESSAGE_USER_CANCEL);
            }
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     *
     * If the new tags contain a net worth status tag (either "highnetworth", "midnetworth", or "lownetworth"),
     * any existing net worth status tags on the person will be removed to ensure that only one net worth status tag
     * is associated with the person at a time. This logic is enforced to prevent conflicting net worth statuses
     * (i.e., a person cannot be classified as both "highnetworth" and "lownetworth").
     *
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Birthday updatedBirthday = editPersonDescriptor.getBirthday().orElse(personToEdit.getBirthday());
        Set<Tag> updatedTags = new HashSet<>(editPersonDescriptor.getTags().orElse(personToEdit.getTags()));

        Set<String> netWorthTags = Set.of("highnetworth", "midnetworth", "lownetworth");

        Optional<Tag> newNetWorthTag = updatedTags.stream()
                .filter(tag -> netWorthTags.contains(tag.tagName.toLowerCase()))
                .findFirst();

        if (newNetWorthTag.isPresent()) {
            updatedTags.removeIf(tag -> netWorthTags.contains(tag.tagName.toLowerCase()));
            updatedTags.add(newNetWorthTag.get());
        }

        Boolean updatedHasPaid = editPersonDescriptor.getHasPaid().orElse(personToEdit.getHasPaid());
        LastPaidDate updatedLastPaidDate = personToEdit.getLastPaidDate();
        Frequency updatedFrequency = personToEdit.getFrequency();
        ProfilePicFilePath updatedProfilePicFilePath = personToEdit.getProfilePicFilePath(); // edit does not update pic

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedBirthday,
                updatedTags, updatedHasPaid, updatedLastPaidDate, updatedFrequency, updatedProfilePicFilePath);
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
        private Birthday birthday;
        private Set<Tag> tags;
        private Boolean hasPaid;

        public EditPersonDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setBirthday(toCopy.birthday);
            setTags(toCopy.tags);
            this.hasPaid = toCopy.hasPaid;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, birthday, tags, hasPaid);
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

        public void setBirthday(Birthday birthday) {
            this.birthday = birthday;
        }

        public Optional<Birthday> getBirthday() {
            return Optional.ofNullable(birthday);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(tags) : Optional.empty();
        }

        public void setHasPaid(Boolean hasPaid) { // needed for EditPersonDescriptorTest.java
            this.hasPaid = hasPaid;
        }

        public Optional<Boolean> getHasPaid() { // needed for EditPersonDescriptorTest.java
            return Optional.ofNullable(hasPaid);
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
                    && Objects.equals(birthday, otherEditPersonDescriptor.birthday)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags)
                    && Objects.equals(hasPaid, otherEditPersonDescriptor.hasPaid);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("birthday", birthday)
                    .add("tags", tags)
                    .add("hasPaid", hasPaid)
                    .toString();
        }
    }

    /**
     * Nested class for testing purposes
     */
    public static class DefaultConfirmationHandler implements ConfirmationHandler {
        /**
         * Bypasses UI popup for testing purposes
         * @param person The duplicated person to be edited
         * @return Whether the edit action proceeds or not
         */
        public boolean confirm(Person person) {
            DuplicateWarningWindow duplicateWarningWindow = new DuplicateWarningWindow();
            duplicateWarningWindow.show(person);
            return duplicateWarningWindow.isConfirmed();
        }
    }
}
