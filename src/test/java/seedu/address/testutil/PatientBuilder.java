package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.patient.Address;
import seedu.address.model.patient.Allergy;
import seedu.address.model.patient.Appt;
import seedu.address.model.patient.Birthdate;
import seedu.address.model.patient.BloodType;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.HealthRecord;
import seedu.address.model.patient.HealthRisk;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Note;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.Sex;

import static seedu.address.logic.parser.ParserUtil.parseSingleAppt;

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
    public static final String DEFAULT_HEALTHRECORD = "Undergoing treatment for cancer";
    public static final String DEFAULT_NOTE = "Requires special care";
    public static final String DEFAULT_NOKNAME = "Tay Bee";
    public static final String DEFAULT_NOKPHONE = "90184718";
    public static final String DEFAULT_APPOINTMENT = "2001-12-10 T 14:30";

    private Name name;
    private Phone phone;
    private Email email;
    private Nric nric;
    private Birthdate birthdate;
    private Sex sex;
    private Address address;
    private Set<Allergy> allergies;
    private BloodType bloodType;
    private HealthRisk healthRisk;
    private HealthRecord healthRecord;
    private Note note;
    private Name nokName;
    private Phone nokPhone;
    private List<Appt> appts;

    //TODO to add default values for the rest of the fields, and add with___ methods for all fields

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
        allergies = new HashSet<>();

        bloodType = new BloodType(DEFAULT_BLOODTYPE);
        healthRisk = new HealthRisk(DEFAULT_HEALTHRISK);
        healthRecord = new HealthRecord(DEFAULT_HEALTHRECORD);
        note = new Note(DEFAULT_NOTE);
        nokName = new Name(DEFAULT_NOKNAME);
        nokPhone = new Phone(DEFAULT_NOKPHONE);
        appts = new ArrayList<>();
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
        allergies = patientToCopy.getAllergies();
        bloodType = patientToCopy.getBloodType();
        healthRisk = patientToCopy.getHealthRisk();
        healthRecord = patientToCopy.getHealthRecord();
        note = patientToCopy.getNote();
        nokName = patientToCopy.getNokName();
        nokPhone = patientToCopy.getNokPhone();
        for (Appt appt: patientToCopy.getAppts()) {
            appts.add(new Appt(appt.getDateTime()));
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
    public PatientBuilder withAllergy(String allergy) {
        this.allergies.add(new Allergy(allergy));
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
    public PatientBuilder withHealthRecord(String healthRecord) {
        this.healthRecord = new HealthRecord(healthRecord);
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
     * Builds a patient based on the fields that were set.
     */
    public Patient build() {
        return new Patient(name, nric, birthdate, sex, phone, email, address, allergies, bloodType,
                healthRisk, healthRecord, note, nokName, nokPhone, appts);
    }

}
