package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHRECORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHRISK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHSERVICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKPHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.healthservice.HealthService;
import seedu.address.model.person.Address;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.Appt;
import seedu.address.model.person.Birthdate;
import seedu.address.model.person.BloodType;
import seedu.address.model.person.Email;
import seedu.address.model.person.HealthRecord;
import seedu.address.model.person.HealthRisk;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Sex;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified by the "
            + "NRIC input. Existing values will be overwritten by the input values.\n"
            + "Parameters: NRIC (must be a valid NRIC in the system) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_NRIC + "NRIC] "
            + "[" + PREFIX_BIRTHDATE + "BIRTHDATE] "
            + "[" + PREFIX_SEX + "SEX] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_HEALTHSERVICE + "HEALTH SERVICE] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_BLOODTYPE + "BLOOD TYPE] "
            + "[" + PREFIX_NOKNAME + "NEXT-OF-KIN NAME] "
            + "[" + PREFIX_NOKPHONE + "NEXT-OF-KIN PHONE] "
            + "[" + PREFIX_ALLERGY + "ALLERGY] "
            + "[" + PREFIX_HEALTHRISK + "HEALTH RISK] "
            + "[" + PREFIX_HEALTHRECORD + "PAST HEALTH RECORD] "
            + "[" + PREFIX_APPOINTMENT + "APPOINTMENT DATE T TIME] "
            + "[" + PREFIX_NOTE + "ADDITIONAL NOTES]\n"
            + "Example: " + COMMAND_WORD + " T0489364Y "
            + PREFIX_NAME + "John Doe "
            + PREFIX_NRIC + "T0123456A "
            + PREFIX_BIRTHDATE + "2001-12-31 "
            + PREFIX_SEX + "M "
            + PREFIX_PHONE + "81234567 "
            + PREFIX_EMAIL + "johndoe123@gmail.com "
            + PREFIX_HEALTHSERVICE + "Blood Test "
            + PREFIX_ADDRESS + "Block 123, NUS Road, S123123 "
            + PREFIX_BLOODTYPE + "A+ "
            + PREFIX_NOKNAME + "Jack Doe "
            + PREFIX_NOKPHONE + "91234567 "
            + PREFIX_ALLERGY + "nuts, shellfish "
            + PREFIX_HEALTHRISK + "HIGH "
            + PREFIX_HEALTHRECORD + "Diabetes "
            + PREFIX_APPOINTMENT + "2022-12-31T14:00"
            + PREFIX_NOTE + "Patient needs extra care";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String WRONG_NRIC = "NRIC provided has to be a current valid NRIC in the system.";

    private final Nric nric;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param nric of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Nric nric, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(nric);
        requireNonNull(editPersonDescriptor);

        this.nric = nric;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Person personToEdit = lastShownList.stream()
                .filter(person -> person.getNric().equals(nric))
                .findFirst()
                .orElse(null);

        if (personToEdit == null) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PERSON_NRIC, nric));
        }
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
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
        Nric updatedNric = editPersonDescriptor.getNric().orElse(personToEdit.getNric());
        Birthdate updatedBirthDate = editPersonDescriptor.getBirthDate().orElse(personToEdit.getBirthdate());
        Sex updatedSex = editPersonDescriptor.getSex().orElse(personToEdit.getSex());
        Set<HealthService> updatedHealthServices = editPersonDescriptor.getHealthServices()
                .orElse(personToEdit.getHealthServices());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Allergy updatedAllergy = editPersonDescriptor.getAllergy().orElse(personToEdit.getAllergy());
        BloodType updatedBloodType = editPersonDescriptor.getBloodType().orElse(personToEdit.getBloodType());
        HealthRisk updatedHealthRisk = editPersonDescriptor.getHealthRisk().orElse(personToEdit.getHealthRisk());
        HealthRecord updatedHealthRecord = editPersonDescriptor.getHealthRecord()
                .orElse(personToEdit.getHealthRecord());
        Note updatedNote = editPersonDescriptor.getNote().orElse(personToEdit.getNote());
        Name updatedNokName = editPersonDescriptor.getNokName().orElse(personToEdit.getNokName());
        Phone updatedNokPhone = editPersonDescriptor.getNokPhone().orElse(personToEdit.getNokPhone());
        List<Appt> updatedAppts = editPersonDescriptor.getAppts().orElse(personToEdit.getAppts());

        return new Person(updatedName, updatedNric, updatedBirthDate, updatedSex, updatedHealthServices, updatedPhone,
                updatedEmail, updatedAddress, updatedAllergy, updatedBloodType, updatedHealthRisk, updatedHealthRecord,
                updatedNote, updatedNokName, updatedNokPhone, updatedAppts);
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
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("NRIC", nric)
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
        private Nric nric;
        private Birthdate birthdate;
        private Sex sex;
        private Address address;
        private Allergy allergy;
        private BloodType bloodType;
        private HealthRisk healthRisk;
        private HealthRecord healthRecord;
        private Note note;
        private Name nokName;
        private Phone nokPhone;
        private Set<HealthService> healthServices;
        private List<Appt> appts;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
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
            setHealthRecord(toCopy.healthRecord);
            setNote(toCopy.note);
            setNokName(toCopy.nokName);
            setNokPhone(toCopy.nokPhone);
            setHealthServices(toCopy.healthServices);
            setAppts(toCopy.appts);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, nric, birthdate, sex, address, allergy, bloodType,
                    healthRisk, healthRecord, note, nokName, nokPhone, healthServices, appts);
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

        public void setHealthRecord(HealthRecord healthRecord) {
            this.healthRecord = healthRecord;
        }
        public Optional<HealthRecord> getHealthRecord() {
            return Optional.ofNullable(healthRecord);
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

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setHealthServices(Set<HealthService> healthServices) {
            this.healthServices = (healthServices != null) ? new HashSet<>(healthServices) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<HealthService>> getHealthServices() {
            return (healthServices != null) ? Optional.of(Collections.unmodifiableSet(healthServices))
                    : Optional.empty();
        }

        public void setAppts(List<Appt> appts) {
            this.appts = (appts != null) ? new ArrayList<>(appts) : null;
        }

        public Optional<List<Appt>> getAppts() {
            return (appts != null) ? Optional.of(Collections.unmodifiableList(appts)) : Optional.empty();
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
            return Objects.equals(nric, otherEditPersonDescriptor.nric);
        }

        //change this
        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("nric", nric)
                    .add("sex", sex)
                    .add("birthdate", birthdate)
                    .add("Health Services", healthServices)
                    .toString();
        }
    }
}
