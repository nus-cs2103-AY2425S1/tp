package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.healthservice.HealthService;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.Allergy;
import seedu.address.model.patient.AllergyMap;
import seedu.address.model.patient.Appt;
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
 * Jackson-friendly version of {@link Patient}.
 */
class JsonAdaptedPatient {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Patient's %s field is missing!";
    private static final Logger logger = Logger.getLogger(JsonAdaptedPatient.class.getName());

    private final String name;
    private final String nric;
    private final String sex;
    private final String birthDate;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedAllergy> allergies = new ArrayList<>();
    private final String bloodType;
    private final String healthRisk;
    private final String existingCondition;
    private final String note;
    private final String nokName;
    private final String nokPhone;
    private final List<JsonAdaptedAppt> appts = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPatient} with the given patient details.
     */
    @JsonCreator
    public JsonAdaptedPatient(@JsonProperty("name") String name, @JsonProperty("NRIC") String nric,
            @JsonProperty("Sex") String sex, @JsonProperty("Birth Date") String birthDate,
            @JsonProperty("phone") String phone, @JsonProperty("email") String email,
            @JsonProperty("address") String address, @JsonProperty("allergy") List<JsonAdaptedAllergy> allergies,
            @JsonProperty("bloodType") String bloodType, @JsonProperty("healthRisk") String healthRisk,
            @JsonProperty("existingCondition") String existingCondition, @JsonProperty("note") String note,
            @JsonProperty("nokName") String nokName, @JsonProperty("nokPhone") String nokPhone,
            @JsonProperty("appts") List<JsonAdaptedAppt> appts) {
        this.name = name;
        this.nric = nric;
        this.sex = sex;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        if (allergies != null) {
            this.allergies.addAll(allergies);
        }
        this.address = address;
        this.bloodType = bloodType;
        this.healthRisk = healthRisk;
        this.existingCondition = existingCondition;
        this.note = note;
        this.nokName = nokName;
        this.nokPhone = nokPhone;
        if (appts != null) {
            this.appts.addAll(appts);
        }
    }

    /**
     * Converts a given {@code Patient} into this class for Jackson use.
     */
    public JsonAdaptedPatient(Patient source) {
        name = source.getName().fullName;
        nric = source.getNric().value;
        sex = source.getSex().value;
        birthDate = source.getBirthdate().value;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress() == null ? "" : source.getAddress().value;
        allergies.addAll(source.getAllergies().stream()
                .map(JsonAdaptedAllergy::new)
                .collect(Collectors.toList()));
        bloodType = source.getBloodType() == null ? "" : source.getBloodType().value;
        healthRisk = source.getHealthRisk() == null ? "" : source.getHealthRisk().value;
        existingCondition = source.getExistingCondition() == null ? "" : source.getExistingCondition().value;
        note = source.getNote() == null ? "" : source.getNote().value;
        nokName = source.getNokName() == null ? "" : source.getNokName().fullName;
        nokPhone = source.getNokPhone() == null ? "" : source.getNokPhone().value;
        appts.addAll(source.getAppts().stream()
                .map(JsonAdaptedAppt::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted patient object into the model's {@code Patient} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted patient.
     */
    public Patient toModelType() throws IllegalValueException {
        final List<Allergy> personAllergies = new ArrayList<>();
        for (JsonAdaptedAllergy allergy : allergies) {
            personAllergies.add(allergy.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (nric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName()));
        }
        if (!Nric.isValidNric(nric)) {
            throw new IllegalValueException(Nric.MESSAGE_CONSTRAINTS);
        }
        final Nric modelNric = new Nric(nric);

        if (sex == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Sex.class.getSimpleName()));
        }
        if (!Sex.isValidSex(sex)) {
            throw new IllegalValueException(Sex.MESSAGE_CONSTRAINTS);
        }
        final Sex modelSex = new Sex(sex);

        if (birthDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Birthdate.class.getSimpleName()));
        }
        if (!Birthdate.isValidBirthdate(birthDate)) {
            throw new IllegalValueException(Birthdate.MESSAGE_CONSTRAINTS);
        }
        final Birthdate modelBirthDate = new Birthdate(birthDate);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Phone.class.getSimpleName()));
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Email.class.getSimpleName()));
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Address.class.getSimpleName()));
        }
        final Address modelAddress = address.isEmpty() ? null : new Address(address);

        if (bloodType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Address.class.getSimpleName()));
        }
        final BloodType modelBloodType = bloodType.isEmpty() ? null : new BloodType(bloodType);

        if (healthRisk == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    HealthRisk.class.getSimpleName()));
        }
        final HealthRisk modelHealthRisk = healthRisk.isEmpty() ? null : new HealthRisk(healthRisk);

        if (existingCondition == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ExistingCondition.class.getSimpleName()));
        }
        final ExistingCondition modelExistingCondition =
                existingCondition.isEmpty() ? null : new ExistingCondition(existingCondition);

        if (note == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Note.class.getSimpleName()));
        }
        final Note modelNote = note.isEmpty() ? null : new Note(note);

        if (nokPhone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Phone.class.getSimpleName()));
        }
        final Phone modelNokPhone = nokPhone.isEmpty() ? null : new Phone(nokPhone);

        if (nokName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Name.class.getSimpleName()));
        }
        final Name modelNokName = nokName.isEmpty() ? null : new Name(nokName);

        if (appts == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Appt.class.getSimpleName()));
        }
        final ApptList modelAppts = new ApptList();

        for (JsonAdaptedAppt appt : appts) {
            if (appt.getHealthServiceName() == null) {
                logger.severe("HealthServiceName is null for appointment: " + appt);
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        HealthService.class.getSimpleName()));
            }

            if (!HealthService.isValidHealthserviceName(appt.getHealthServiceName())) {
                logger.severe("Invalid health service name for appointment: " + appt);
                throw new IllegalValueException("Invalid health service name");
            }

            modelAppts.addAppt(appt.toModelType());
        }

        final AllergyMap modelAllergies = new AllergyMap();

        for (JsonAdaptedAllergy allergy : allergies) {
            if (allergy == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        Allergy.class.getSimpleName()));
            }

            modelAllergies.addAllergy(allergy.toModelType());
        }

        return new Patient(modelName, modelNric, modelBirthDate, modelSex, modelPhone, modelEmail,
                modelAddress, modelAllergies, modelBloodType, modelHealthRisk, modelExistingCondition, modelNote,
                modelNokName, modelNokPhone, modelAppts);
    }

}
