package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Address;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.MedCon;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricMatchesPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the patient identified "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: NRIC (must be a valid NRIC in the system) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_NRIC + "NRIC] "
            + "[" + PREFIX_DOB + "DATE OF BIRTH] "
            + "[" + PREFIX_GENDER + "GENDER] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_ALLERGY + "ALLERGY]...\n"
            + "Example: " + COMMAND_WORD + " S1234567A "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Patient: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This patient already exists in the address book.";
    private static final Logger logger = LogsCenter.getLogger(EditCommand.class);
    private final NricMatchesPredicate predicate;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * Creates an EditCommand to edit the details of the person with the specified {@code nric}
     * using the fields in {@code editPersonDescriptor}.
     *
     * @param predicate of the person to edit.
     * @param editPersonDescriptor details to edit the person with.
     */
    public EditCommand(NricMatchesPredicate predicate, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(predicate);
        requireNonNull(editPersonDescriptor);

        this.predicate = predicate;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToEdit = model.fetchPersonIfPresent(predicate)
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_PERSON_NRIC_NOT_FOUND));
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        logger.info("Edit command has been executed successfully on a person with NRIC: " + predicate);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
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
        DateOfBirth updatedDateOfBirth = editPersonDescriptor.getDateOfBirth().orElse(personToEdit.getDateOfBirth());
        Gender updatedGender = editPersonDescriptor.getGender().orElse(personToEdit.getGender());
        Nric updatedNric = editPersonDescriptor.getNric().orElse(personToEdit.getNric());
        Set<Allergy> updatedAllergies = editPersonDescriptor.getAllergies().orElse(personToEdit.getAllergies());
        Priority updatedPriority = personToEdit.getPriority();
        Set<Appointment> updatedAppointments = editPersonDescriptor.getAppointments()
                                                                   .orElse(personToEdit.getAppointments());
        Set<MedCon> updatedMedCons = editPersonDescriptor.getMedCons().orElse(personToEdit.getMedCons());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedNric, updatedAddress, updatedDateOfBirth,
                updatedGender, updatedAllergies, updatedPriority, updatedAppointments, updatedMedCons);

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
        return editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor)
                && predicate.equals(otherEditCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
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
        private DateOfBirth dateOfBirth;
        private Gender gender;
        private Nric nric;
        private Set<Allergy> allergies;
        private Set<Appointment> appointments;
        private Set<MedCon> medCons;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code allergies} and {@code appointments} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setGender(toCopy.gender);
            setDateOfBirth(toCopy.dateOfBirth);
            setNric(toCopy.nric);
            setAllergies(toCopy.allergies);
            setAppointments(toCopy.appointments);
            setMedCons(toCopy.medCons);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, dateOfBirth, nric, gender, allergies,
                                               appointments, medCons);
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

        public Optional<DateOfBirth> getDateOfBirth() {
            return Optional.ofNullable(dateOfBirth);
        }

        public void setDateOfBirth(DateOfBirth dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
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

        /**
         * Returns an unmodifiable medCon set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code medCons} is null.
         */
        public Optional<Set<MedCon>> getMedCons() {
            return (medCons != null) ? Optional.of(Collections.unmodifiableSet(medCons)) : Optional.empty();
        }

        /**
         * Sets {@code medCons} to this object's {@code medCons}.
         * A defensive copy of {@code medCons} is used internally.
         */
        public void setMedCons(Set<MedCon> medCons) {
            this.medCons = (medCons != null) ? new HashSet<>(medCons) : null;
        }

        /**
         * Sets {@code allergies} to this object's {@code allergies}.
         * A defensive copy of {@code allergies} is used internally.
         */
        public void setAllergies(Set<Allergy> allergies) {
            this.allergies = (allergies != null) ? new HashSet<>(allergies) : null;
        }

        /**
         * Sets {@code appointments} to this object's {@code appointments}.
         * A defensive copy of {@code appointments} is used internally.
         */
        public void setAppointments(Set<Appointment> appointments) {
            this.appointments = (appointments != null) ? new HashSet<>(appointments) : null;
        }

        /**
         * Returns an unmodifiable allergy set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code allergies} is null.
         */
        public Optional<Set<Allergy>> getAllergies() {
            return (allergies != null) ? Optional.of(Collections.unmodifiableSet(allergies)) : Optional.empty();
        }

        /**
         * Returns an unmodifiable appointment set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code appointments} is null.
         */
        public Optional<Set<Appointment>> getAppointments() {
            return (appointments != null) ? Optional.of(Collections.unmodifiableSet(appointments)) : Optional.empty();
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
                    && Objects.equals(gender, otherEditPersonDescriptor.gender)
                    && Objects.equals(dateOfBirth, otherEditPersonDescriptor.dateOfBirth)
                    && Objects.equals(nric, otherEditPersonDescriptor.nric)
                    && Objects.equals(allergies, otherEditPersonDescriptor.allergies)
                    && Objects.equals(appointments, otherEditPersonDescriptor.appointments);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("nric", nric)
                    .add("gender", gender)
                    .add("dateOfBirth", dateOfBirth)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("allergies", allergies)
                    .add("appointments", appointments)
                    .toString();
        }
    }
}
