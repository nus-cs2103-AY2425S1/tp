package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRAD_YEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
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
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.GradYear;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RoomNumber;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends ConcreteCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ROOM_NUMBER + "ROOM_NUMBER] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_EMERGENCY_NAME + "EMERGENCY_NAME] "
            + "[" + PREFIX_EMERGENCY_PHONE + "EMERGENCY_PHONE] "
            + "[" + PREFIX_GRAD_YEAR + "GRADUATION_YEAR] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_NAME = "A person with this name already exists in the address book";
    public static final String MESSAGE_DUPLICATE_PHONE = "This phone number already exists in the address book";
    public static final String MESSAGE_UNDO_SUCCESS = "Reverted edit of person: %1$s";


    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;
    private Person personToEdit;
    private Person editedPerson;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNotExecuted();
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(index.getZeroBased());
        editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);


        if (!personToEdit.isSameName(editedPerson) && model.hasName(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_NAME);
        } else if (!personToEdit.isSameNumber(editedPerson) && model.hasPhone(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PHONE);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        isExecuted = true;
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    @Override
    public CommandResult undo(Model model) {
        requireExecuted();
        requireAllNonNull(model, personToEdit, editedPerson);

        model.setPerson(editedPerson, personToEdit);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        isExecuted = false;
        return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, Messages.format(personToEdit)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        RoomNumber updatedRoomNumber = editPersonDescriptor.getRoomNumber()
                .orElse(personToEdit.getRoomNumber().orElse(null));
        Name updatedEmergencyName = editPersonDescriptor.getEmergencyName()
                .orElse(personToEdit.getEmergencyContactName().orElse(null));
        Phone updatedEmergencyPhone = editPersonDescriptor.getEmergencyPhone()
                .orElse(personToEdit.getEmergencyContactPhone().orElse(null));
        EmergencyContact updatedEmergencyContact;
        if (updatedEmergencyName == null && updatedEmergencyPhone == null) {
            // emergency contact does not exist
            updatedEmergencyContact = null;
        } else {
            updatedEmergencyContact = new EmergencyContact(updatedEmergencyName, updatedEmergencyPhone);
        }

        GradYear updatedGradYear = editPersonDescriptor.getGradYear()
                .orElse(personToEdit.getGradYear().orElse(null));
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedRoomNumber,
                updatedAddress, updatedEmergencyContact, updatedGradYear, updatedTags);
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
        private RoomNumber roomNumber;
        private Address address;
        private Name emergencyName;
        private Phone emergencyPhone;
        private GradYear gradYear;
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
            setRoomNumber(toCopy.roomNumber);
            setAddress(toCopy.address);
            setEmergencyName(toCopy.emergencyName);
            setEmergencyPhone(toCopy.emergencyPhone);
            setGradYear(toCopy.gradYear);
            setTags(toCopy.tags);
        }
        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, roomNumber,
                    address, emergencyName, emergencyPhone, gradYear, tags);
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

        public void setRoomNumber(RoomNumber roomNumber) {
            this.roomNumber = roomNumber;
        }

        public void setNoRoomNumber() {
            this.roomNumber = null;
        }

        public Optional<RoomNumber> getRoomNumber() {
            return Optional.ofNullable(roomNumber);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setEmergencyName(Name emergencyName) {
            this.emergencyName = emergencyName;
        }

        public void setNoEmergencyName() {
            this.emergencyName = null;
        }

        public Optional<Name> getEmergencyName() {
            return Optional.ofNullable(emergencyName);
        }

        public void setEmergencyPhone(Phone emergencyPhone) {
            this.emergencyPhone = emergencyPhone;
        }

        public void setNoEmergencyPhone() {
            this.emergencyPhone = null;
        }

        public Optional<Phone> getEmergencyPhone() {
            return Optional.ofNullable(emergencyPhone);
        }

        public void setGradYear(GradYear gradYear) {
            this.gradYear = gradYear;
        }

        public void setNoGradYear() {
            this.gradYear = null;
        }

        public Optional<GradYear> getGradYear() {
            return Optional.ofNullable(gradYear);
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
                    && Objects.equals(roomNumber, otherEditPersonDescriptor.roomNumber)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("room number", roomNumber)
                    .add("address", address)
                    .add("emergency name", emergencyName)
                    .add("emergency phone", emergencyPhone)
                    .add("graduation year", gradYear)
                    .add("tags", tags)
                    .toString();
        }
    }
}
