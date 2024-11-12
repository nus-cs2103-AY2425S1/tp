package seedu.address.testutil;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.Allergy;
import seedu.address.model.patient.AllergyList;
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
 * A utility class to help with building Patient objects.
 */
public class PatientBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_NRIC = "S1234567A";
    public static final String DEFAULT_BIRTHDATE = "1990-01-01";
    public static final String DEFAULT_SEX = "F";
    public static final String DEFAULT_ALLERGY = "Nuts";
    public static final String DEFAULT_BLOODTYPE = "A+";
    public static final String DEFAULT_HEALTHRISK = "HIGH";
    public static final String DEFAULT_EXISTINGCONDITION = "Undergoing treatment for cancer";
    public static final String DEFAULT_NOTE = "Requires special care";
    public static final String DEFAULT_NOKNAME = "Bumble Bee";
    public static final String DEFAULT_NOKPHONE = "90184718";

    private Name name;
    private Phone phone;
    private Email email;
    private Nric nric;
    private Birthdate birthdate;
    private Sex sex;
    private Address address;
    private AllergyList allergies;
    private BloodType bloodType;
    private HealthRisk healthRisk;
    private ExistingCondition existingCondition;
    private Note note;
    private Name nokName;
    private Phone nokPhone;
    private ApptList appts;

    /**
     * Creates a {@code PatientBuilder} with the default details.
     */
    public PatientBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        nric = new Nric(DEFAULT_NRIC);
        birthdate = new Birthdate(DEFAULT_BIRTHDATE);
        sex = new Sex(DEFAULT_SEX);
        allergies = new AllergyList();
        bloodType = new BloodType(DEFAULT_BLOODTYPE);
        healthRisk = new HealthRisk(DEFAULT_HEALTHRISK);
        existingCondition = new ExistingCondition(DEFAULT_EXISTINGCONDITION);
        note = new Note(DEFAULT_NOTE);
        nokName = new Name(DEFAULT_NOKNAME);
        nokPhone = new Phone(DEFAULT_NOKPHONE);
        appts = new ApptList();
    }

    /**
     * Initializes the PatientBuilder with the data of {@code patientToCopy}.
     */
    public PatientBuilder(Patient patientToCopy) {
        name = patientToCopy.getName();
        phone = patientToCopy.getPhone();
        email = patientToCopy.getEmail();
        address = patientToCopy.getAddress();
        nric = patientToCopy.getNric();
        birthdate = patientToCopy.getBirthdate();
        sex = patientToCopy.getSex();
        bloodType = patientToCopy.getBloodType();
        healthRisk = patientToCopy.getHealthRisk();
        existingCondition = patientToCopy.getExistingCondition();
        note = patientToCopy.getNote();
        nokName = patientToCopy.getNokName();
        nokPhone = patientToCopy.getNokPhone();
        allergies = new AllergyList();
        appts = new ApptList();
        for (Appt appt: patientToCopy.getImmutableApptList()) {
            appts.addAppt(new Appt(appt.getDateTime(), appt.getHealthService()));
        }
        for (Allergy allergy : patientToCopy.getAllergies()) {
            allergies.addAllergy(allergy);
        }
    }

    /**
     * Sets the {@code Name} of the {@code Patient} that we are building.
     */
    public PatientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Patient} that we are building.
     */
    public PatientBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Patient} that we are building.
     */
    public PatientBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Patient} that we are building.
     */
    public PatientBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code Patient} that we are building.
     */
    public PatientBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Sets the {@code Birthdate} of the {@code Patient} that we are building.
     */
    public PatientBuilder withBirthdate(String birthdate) {
        this.birthdate = new Birthdate(birthdate);
        return this;
    }

    /**
     * Sets the {@code Sex} of the {@code Patient} that we are building.
     */
    public PatientBuilder withSex(String sex) {
        this.sex = new Sex(sex);
        return this;
    }

    /**
     * Sets the {@code Allergy} of the {@code Patient} that we are building.
     */
    public PatientBuilder withAllergies(String... allergies) {
        AllergyList allergyList = new AllergyList();
        for (String allergy : allergies) {
            String trimmedAllergy = allergy.trim();
            allergyList.addAllergy(new Allergy(trimmedAllergy));
        }
        this.allergies = allergyList;
        return this;
    }

    /**
     * Sets the {@code Blood Type} of the {@code Patient} that we are building.
     */
    public PatientBuilder withBloodType(String bloodType) {
        this.bloodType = new BloodType(bloodType);
        return this;
    }

    /**
     * Sets the {@code Health Risk} of the {@code Patient} that we are building.
     */
    public PatientBuilder withHealthRisk(String healthRisk) {
        this.healthRisk = new HealthRisk(healthRisk);
        return this;
    }

    /**
     * Sets the {@code Health Record} of the {@code Patient} that we are building.
     */
    public PatientBuilder withExistingCondition(String existingCondition) {
        this.existingCondition = new ExistingCondition(existingCondition);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code Patient} that we are building.
     */
    public PatientBuilder withNote(String note) {
        this.note = new Note(note);
        return this;
    }

    /**
     * Sets the {@code NokName} of the {@code Patient} that we are building.
     */
    public PatientBuilder withNokName(String nokName) {
        this.nokName = new Name(nokName);
        return this;
    }

    /**
     * Sets the {@code NokPhone} of the {@code Patient} that we are building.
     */
    public PatientBuilder withNokPhone(String nokPhone) {
        this.nokPhone = new Phone(nokPhone);
        return this;
    }

    /**
     * Adds a {@code Appt} to the {@code Patient} that we are building.
     */
    public PatientBuilder withAppts(String dateTime, String healthService) {
        try {
            Appt appt = ParserUtil.parseSingleAppt(dateTime, healthService);
            appts.addAppt(appt);
            return this;
        } catch (ParseException e) {
            return this;
        }
    }

    /**
     * Builds a patient based on the fields that were set.
     */
    public Patient build() {
        return new Patient(name, nric, birthdate, sex, phone, email, address, allergies, bloodType,
                healthRisk, existingCondition, note, nokName, nokPhone, appts);
    }

}
