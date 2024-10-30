package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXISTINGCONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHRISK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKPHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.Allergy;
import seedu.address.model.patient.ApptList;
import seedu.address.model.patient.Birthdate;
import seedu.address.model.patient.BloodType;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.ExistingCondition;
import seedu.address.model.patient.HealthRisk;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Note;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.Sex;

/**
 * Edits the details of an existing patient in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits patient's detail(s) for an existing patient "
            + "record in the system\n"
            + "Input \"help " + COMMAND_WORD + "\" for description and usage of this command";

    public static final String MESSAGE_EDIT_PATIENT_SUCCESS = "Edited Patient: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PATIENT = "This patient already exists in the address book.";

    private final Nric nric;
    private final EditPatientDescriptor editPatientDescriptor;

    /**
     * @param nric of the patient in the filtered patient list to edit
     * @param editPatientDescriptor details to edit the patient with
     */
    public EditCommand(Nric nric, EditPatientDescriptor editPatientDescriptor) {
        requireNonNull(nric);
        requireNonNull(editPatientDescriptor);

        this.nric = nric;
        this.editPatientDescriptor = new EditPatientDescriptor(editPatientDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> lastShownList = model.getFilteredPatientList();

        Patient patientToEdit = lastShownList.stream()
                .filter(patient -> patient.getNric().equals(nric))
                .findFirst()
                .orElse(null);

        if (patientToEdit == null) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PATIENT_NRIC, nric));
        }
        Patient editedPatient = createEditedPatient(patientToEdit, editPatientDescriptor);

        if (!patientToEdit.isSamePatient(editedPatient) && model.hasPatient(editedPatient)) {
            throw new CommandException(MESSAGE_DUPLICATE_PATIENT);
        }

        model.setPatient(patientToEdit, editedPatient);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_PATIENT_SUCCESS, Messages.format(editedPatient)));
    }

    /**
     * Creates and returns a {@code Patient} with the details of {@code patientToEdit}
     * edited with {@code editPatientDescriptor}.
     */
    private static Patient createEditedPatient(Patient patientToEdit, EditPatientDescriptor editPatientDescriptor) {
        assert patientToEdit != null;

        Name updatedName = editPatientDescriptor.getName().orElse(patientToEdit.getName());
        Nric updatedNric = editPatientDescriptor.getNric().orElse(patientToEdit.getNric());
        Birthdate updatedBirthDate = editPatientDescriptor.getBirthDate().orElse(patientToEdit.getBirthdate());
        Sex updatedSex = editPatientDescriptor.getSex().orElse(patientToEdit.getSex());
        Phone updatedPhone = editPatientDescriptor.getPhone().orElse(patientToEdit.getPhone());
        Email updatedEmail = editPatientDescriptor.getEmail().orElse(patientToEdit.getEmail());
        Address updatedAddress = editPatientDescriptor.getAddress().orElse(patientToEdit.getAddress());
        // Allergy updatedAllergy = editPatientDescriptor.getAllergy().orElse(patientToEdit.getAllergy());
        BloodType updatedBloodType = editPatientDescriptor.getBloodType().orElse(patientToEdit.getBloodType());
        HealthRisk updatedHealthRisk = editPatientDescriptor.getHealthRisk().orElse(patientToEdit.getHealthRisk());
        ExistingCondition updatedExistingCondition = editPatientDescriptor.getExistingCondition()
                .orElse(patientToEdit.getExistingCondition());
        Note updatedNote = editPatientDescriptor.getNote().orElse(patientToEdit.getNote());
        Name updatedNokName = editPatientDescriptor.getNokName().orElse(patientToEdit.getNokName());
        Phone updatedNokPhone = editPatientDescriptor.getNokPhone().orElse(patientToEdit.getNokPhone());
        ApptList updatedAppt = patientToEdit.getAppointments();

        // I changed the updatedAllergy, you will need to change it later @yuanch
        return new Patient(updatedName, updatedNric, updatedBirthDate, updatedSex, updatedPhone,
                updatedEmail, updatedAddress, new HashSet<>(), updatedBloodType, updatedHealthRisk,
                updatedExistingCondition, updatedNote, updatedNokName, updatedNokPhone, updatedAppt);
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
        return nric.equals(otherEditCommand.nric)
                && editPatientDescriptor.equals(otherEditCommand.editPatientDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("NRIC", nric)
                .add("editPatientDescriptor", editPatientDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the patient with. Each non-empty field value will replace the
     * corresponding field value of the patient.
     */
    public static class EditPatientDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Nric nric;
        private Birthdate birthdate;
        private Sex sex;
        private Address address;
        private Allergy allergy;
        private BloodType bloodType;
        private HealthRisk healthRisk;
        private ExistingCondition existingCondition;
        private Note note;
        private Name nokName;
        private Phone nokPhone;

        public EditPatientDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPatientDescriptor(EditPatientDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setNric(toCopy.nric);
            setBirthDate(toCopy.birthdate);
            setSex(toCopy.sex);
            setAddress(toCopy.address);
            setAllergy(toCopy.allergy);
            setBloodType(toCopy.bloodType);
            setHealthRisk(toCopy.healthRisk);
            setExistingCondition(toCopy.existingCondition);
            setNote(toCopy.note);
            setNokName(toCopy.nokName);
            setNokPhone(toCopy.nokPhone);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, nric, birthdate, sex, address, allergy, bloodType,
                    healthRisk, existingCondition, note, nokName, nokPhone);
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

        public void setNric(Nric nric) {
            this.nric = nric;
        }
        public Optional<Nric> getNric() {
            return Optional.ofNullable(nric);
        }

        public void setBirthDate(Birthdate birthdate) {
            this.birthdate = birthdate;
        }
        public Optional<Birthdate> getBirthDate() {
            return Optional.ofNullable(birthdate);
        }

        public void setSex(Sex sex) {
            this.sex = sex;
        }
        public Optional<Sex> getSex() {
            return Optional.ofNullable(sex);
        }

        public void setAddress(Address address) {
            this.address = address;
        }
        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setAllergy(Allergy allergy) {
            this.allergy = allergy;
        }
        public Optional<Allergy> getAllergy() {
            return Optional.ofNullable(allergy);
        }

        public void setBloodType(BloodType bloodtype) {
            this.bloodType = bloodtype;
        }
        public Optional<BloodType> getBloodType() {
            return Optional.ofNullable(bloodType);
        }

        public void setHealthRisk(HealthRisk healthRisk) {
            this.healthRisk = healthRisk;
        }
        public Optional<HealthRisk> getHealthRisk() {
            return Optional.ofNullable(healthRisk);
        }

        public void setExistingCondition(ExistingCondition existingCondition) {
            this.existingCondition = existingCondition;
        }
        public Optional<ExistingCondition> getExistingCondition() {
            return Optional.ofNullable(existingCondition);
        }

        public void setNote(Note note) {
            this.note = note;
        }
        public Optional<Note> getNote() {
            return Optional.ofNullable(note);
        }

        public void setNokName(Name nokName) {
            this.nokName = nokName;
        }
        public Optional<Name> getNokName() {
            return Optional.ofNullable(nokName);
        }

        public void setNokPhone(Phone nokPhone) {
            this.nokPhone = nokPhone;
        }
        public Optional<Phone> getNokPhone() {
            return Optional.ofNullable(nokPhone);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPatientDescriptor)) {
                return false;
            }

            EditPatientDescriptor otherEditPatientDescriptor = (EditPatientDescriptor) other;
            return Objects.equals(nric, otherEditPatientDescriptor.nric);
        }

        //change this
        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("nric", nric)
                    .add("sex", sex)
                    .add("birthdate", birthdate)
                    .toString();
        }
    }
}
