package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddCommand.MESSAGE_APPOINTMENT_TAKEN;
import static seedu.address.logic.commands.AddCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
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
import seedu.address.model.person.Age;
//import seedu.address.model.person.Appointment;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;


/**
 * Edits the details of an existing person in the address book.
 */
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the details of the person identified "
            + "by the NRIC number or name used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: NRIC OR INDEX (must be a positive integer)"
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_AGE + "AGE] "
            + "[" + PREFIX_GENDER + "GENDER] "
            + "[" + PREFIX_NRIC + "NRIC] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_APPOINTMENT + "APPOINTMENT] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example 1: " + COMMAND_WORD + " S1234567Z "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com\n"
            + "Example 2: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_UPDATE_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_SAME_NRIC = "Multiple persons with the same NRIC found. Please specify further.";

    private final Nric nric;
    private final Index index;
    private final UpdatePersonDescriptor updatePersonDescriptor;

    /**
     * @param nric                 of the person in the filtered person list to edit
     * @param updatePersonDescriptor details to edit the person with
     */
    public UpdateCommand(Nric nric, UpdatePersonDescriptor updatePersonDescriptor) {
        requireNonNull(nric);
        requireNonNull(updatePersonDescriptor);

        this.nric = nric;
        this.index = null;
        this.updatePersonDescriptor = new UpdatePersonDescriptor(updatePersonDescriptor);
    }

    /**
     * @param index                 of the person in the filtered person list to edit
     * @param updatePersonDescriptor  details to edit the person with
     */
    public UpdateCommand(Index index, UpdatePersonDescriptor updatePersonDescriptor) {
        requireNonNull(index);
        requireNonNull(updatePersonDescriptor);

        this.nric = null;
        this.index = index;
        this.updatePersonDescriptor = new UpdatePersonDescriptor(updatePersonDescriptor);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code updatePersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, UpdatePersonDescriptor updatePersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = updatePersonDescriptor.getName().orElse(personToEdit.getName());
        Age updatedAge = updatePersonDescriptor.getAge().orElse(personToEdit.getAge());
        Gender updatedGender = updatePersonDescriptor.getGender().orElse(personToEdit.getGender());
        Nric updatedNric = updatePersonDescriptor.getNric().orElse(personToEdit.getNric());
        Phone updatedPhone = updatePersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = updatePersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = updatePersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Appointment updateAppointment = updatePersonDescriptor.getAppointment().orElse(personToEdit.getAppointment());
        Set<Tag> updatedTags = updatePersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedName, updatedAge, updatedGender, updatedNric,
                updatedPhone, updatedEmail, updatedAddress, updateAppointment, updatedTags);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToEdit;

        if (nric != null) {
            List<Person> matchingPersons = lastShownList.stream()
                    .filter(person -> person.getNric().equals(nric))
                    .toList();

            if (matchingPersons.isEmpty()) {
                throw new CommandException(MESSAGE_NOT_EDITED);
            } else if (matchingPersons.size() == 1) {
                personToEdit = matchingPersons.get(0);
            } else {
                // Handle multiple matches for NRIC
                throw new CommandException(MESSAGE_SAME_NRIC);
            }
        } else { // for index
            assert index != null;
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            personToEdit = lastShownList.get(index.getZeroBased());
        }

        Person editedPerson = createEditedPerson(personToEdit, updatePersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        if (!personToEdit.getAppointment().equals(editedPerson.getAppointment())
                && model.hasAppointment(editedPerson)) {
            throw new CommandException(MESSAGE_APPOINTMENT_TAKEN);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_UPDATE_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateCommand otherEditCommand)) {
            return false;
        }

        if (nric != null) {
            return nric.equals(otherEditCommand.nric)
                    && updatePersonDescriptor.equals(otherEditCommand.updatePersonDescriptor);
        } else {
            assert index != null;
            return index.equals(otherEditCommand.index)
                    && updatePersonDescriptor.equals(otherEditCommand.updatePersonDescriptor);
        }
    }

    @Override
    public String toString() {
        if (nric != null) {
            return new ToStringBuilder(this)
                    .add("nric", nric)
                    .add("editPersonDescriptor", updatePersonDescriptor)
                    .toString();
        } else {
            return new ToStringBuilder(this)
                    .add("index", index)
                    .add("editPersonDescriptor", updatePersonDescriptor)
                    .toString();
        }
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class UpdatePersonDescriptor {
        private Name name;
        private Age age;
        private Gender gender;
        private Nric nric;
        private Phone phone;
        private Email email;
        private Address address;
        private Appointment appointment;
        private Set<Tag> tags;

        public UpdatePersonDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public UpdatePersonDescriptor(UpdatePersonDescriptor toCopy) {
            setName(toCopy.name);
            setAge(toCopy.age);
            setGender(toCopy.gender);
            setNric(toCopy.nric);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setAppointment(toCopy.appointment);

            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, age, gender, nric,
                    phone, email, address, tags);
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Age> getAge() {
            return Optional.ofNullable(age);
        }

        public void setAge(Age age) {
            this.age = age;
        }

        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Optional<Nric> getNric() {
            return Optional.ofNullable(nric);
        }

        public void setNric(Nric nric) {
            this.nric = nric;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Appointment> getAppointment() {
            return Optional.ofNullable(appointment);
        }

        public void setAppointment(Appointment appointment) {
            this.appointment = appointment;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof UpdatePersonDescriptor otherEditPersonDescriptor)) {
                return false;
            }

            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(age, otherEditPersonDescriptor.age)
                    && Objects.equals(gender, otherEditPersonDescriptor.gender)
                    && Objects.equals(nric, otherEditPersonDescriptor.nric)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(appointment, otherEditPersonDescriptor.appointment)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("age", age)
                    .add("gender", gender)
                    .add("nric", nric)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("appointment", appointment)
                    .add("tags", tags)
                    .toString();
        }
    }
}
