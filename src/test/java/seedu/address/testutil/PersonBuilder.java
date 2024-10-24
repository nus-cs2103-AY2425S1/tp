package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

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
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

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
    private Allergy allergy;
    private BloodType bloodType;
    private HealthRisk healthRisk;
    private HealthRecord healthRecord;
    private Note note;
    private Name nokName;
    private Phone nokPhone;
    private List<Appt> appts;

    //TODO to add default values for the rest of the fields, and add with___ methods for all fields

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        nric = new Nric(DEFAULT_NRIC);
        birthdate = new Birthdate(DEFAULT_BIRTHDATE);
        sex = new Sex(DEFAULT_SEX);
        allergy = new Allergy(DEFAULT_ALLERGY);
        bloodType = new BloodType(DEFAULT_BLOODTYPE);
        healthRisk = new HealthRisk(DEFAULT_HEALTHRISK);
        healthRecord = new HealthRecord(DEFAULT_HEALTHRECORD);
        note = new Note(DEFAULT_NOTE);
        nokName = new Name(DEFAULT_NOKNAME);
        nokPhone = new Phone(DEFAULT_NOKPHONE);
        appts = new ArrayList<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        nric = personToCopy.getNric();
        birthdate = personToCopy.getBirthdate();
        sex = personToCopy.getSex();
        allergy = personToCopy.getAllergy();
        bloodType = personToCopy.getBloodType();
        healthRisk = personToCopy.getHealthRisk();
        healthRecord = personToCopy.getHealthRecord();
        note = personToCopy.getNote();
        nokName = personToCopy.getNokName();
        nokPhone = personToCopy.getNokPhone();
        for (Appt appt: personToCopy.getAppts()) {
            appts.add(new Appt(appt.getDateTime()));
        }
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code Person} that we are building.
     */
    public PersonBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Sets the {@code Birthdate} of the {@code Person} that we are building.
     */
    public PersonBuilder withBirthdate(String birthdate) {
        this.birthdate = new Birthdate(birthdate);
        return this;
    }

    /**
     * Sets the {@code Sex} of the {@code Person} that we are building.
     */
    public PersonBuilder withSex(String sex) {
        this.sex = new Sex(sex);
        return this;
    }

    /**
     * Sets the {@code Allergy} of the {@code Person} that we are building.
     */
    public PersonBuilder withAllergy(String allergy) {
        this.allergy = new Allergy(allergy);
        return this;
    }

    /**
     * Sets the {@code Blood Type} of the {@code Person} that we are building.
     */
    public PersonBuilder withBloodType(String bloodType) {
        this.bloodType = new BloodType(bloodType);
        return this;
    }

    /**
     * Sets the {@code Health Risk} of the {@code Person} that we are building.
     */
    public PersonBuilder withHealthRisk(String healthRisk) {
        this.healthRisk = new HealthRisk(healthRisk);
        return this;
    }

    /**
     * Sets the {@code Health Record} of the {@code Person} that we are building.
     */
    public PersonBuilder withHealthRecord(String healthRecord) {
        this.healthRecord = new HealthRecord(healthRecord);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code Person} that we are building.
     */
    public PersonBuilder withNote(String note) {
        this.note = new Note(note);
        return this;
    }

    /**
     * Sets the {@code NokName} of the {@code Person} that we are building.
     */
    public PersonBuilder withNokName(String nokName) {
        this.nokName = new Name(nokName);
        return this;
    }

    /**
     * Sets the {@code NokPhone} of the {@code Person} that we are building.
     */
    public PersonBuilder withNokPhone(String nokPhone) {
        this.nokPhone = new Phone(nokPhone);
        return this;
    }

    /**
     * Builds a person based on the fields that were set.
     */
    public Person build() {
        return new Person(name, nric, birthdate, sex, phone, email, address, allergy, bloodType,
                healthRisk, healthRecord, note, nokName, nokPhone, appts);
    }

}
