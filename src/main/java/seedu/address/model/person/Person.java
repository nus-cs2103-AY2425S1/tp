package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.ApptSorter;
import seedu.address.model.healthservice.HealthService;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    private final Nric nric;
    private final Birthdate birthdate;
    private final Sex sex;

    // Data fields
    private Address address;
    private Allergy allergy;
    private BloodType bloodType;
    private HealthRisk healthRisk;
    private HealthRecord healthRecord;
    private Note note;
    private Name nokName;
    private Phone nokPhone;
    private final Set<HealthService> healthServices = new HashSet<>();
    private List<Appt> appts = new ArrayList<>();

    /**
     * Name, Nric, Sex, Birthdate and healthservice must be present and not null
     */
    public Person(Name name, Nric nric, Birthdate birthdate, Sex sex, Set<HealthService> healthServices) {
        this(name, nric, birthdate, sex, healthServices, new Phone("123"), new Email("dummy@gmail.com"));
    }

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Nric nric, Birthdate birthdate, Sex sex, Set<HealthService> healthServices,
            Phone phone, Email email) {
        requireAllNonNull(name, nric, birthdate, sex, healthServices, phone, email);
        this.name = name;
        this.nric = nric;
        this.birthdate = birthdate;
        this.sex = sex;
        this.phone = phone;
        this.email = email;
        this.healthServices.addAll(healthServices);
        this.address = null;
        this.allergy = null;
        this.bloodType = null;
        this.healthRisk = null;
        this.healthRecord = null;
        this.note = null;
        this.nokName = null;
        this.nokPhone = null;
        this.appts.clear();
    }

    /**
     * Only Name, NRIC, Sex, BirthDate, HealthServices field need to be present.
     * The other fields can be null
     */
    public Person(Name name, Nric nric, Birthdate birthdate, Sex sex, Set<HealthService> healthServices, Phone phone,
            Email email, Address address, Allergy allergy, BloodType bloodType, HealthRisk healthRisk,
            HealthRecord healthRecord, Note note, Name nokName, Phone nokPhone, List<Appt> appts) {
        requireAllNonNull(name, nric, birthdate, sex, healthServices);
        this.name = name;
        this.nric = nric;
        this.birthdate = birthdate;
        this.sex = sex;
        this.phone = phone;
        this.email = email;
        this.healthServices.addAll(healthServices);
        this.address = address;
        this.allergy = allergy;
        this.bloodType = bloodType;
        this.healthRisk = healthRisk;
        this.healthRecord = healthRecord;
        this.note = note;
        this.nokName = nokName;
        this.nokPhone = nokPhone;
        this.appts = appts;
    }

    public Name getName() {
        return name;
    }

    public Nric getNric() {
        return nric;
    }

    public Sex getSex() {
        return sex;
    }

    public Birthdate getBirthdate() {
        return birthdate;
    }

    public Set<HealthService> getHealthService() {
        return healthServices;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Name getNokName() {
        return nokName;
    }

    public Phone getNokPhone() {
        return nokPhone;
    }

    public Allergy getAllergy() {
        return allergy;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public HealthRisk getHealthRisk() {
        return healthRisk;
    }

    public Note getNote() {
        return note;
    }

    public HealthRecord getHealthRecord() {
        return healthRecord;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Adds an appointment to the person's list of appointments.
     * The appointments will be sorted by date and time.
     *
     * @param appt
     */
    public void addAppt(Appt appt) {
        appts.add(appt);
        ApptSorter.sortAppointments(appts);
    }

    /**
     * Returns an immutable list of appointments.
     * This list will not contain any duplicate appointments.
     *
     * @return List of appointments.
     */
    public List<Appt> getAppts() {
        return Collections.unmodifiableList(appts);
    }

    /**
     * Returns a string representation of the appointments
     * in the form of a list of strings.
     *
     * @return String representation of the appointments.
     */
    public String getApptsString() {
        StringBuilder sb = new StringBuilder();
        for (Appt appt : appts) {
            sb.append(appt.toString()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Deletes an appointment from the person's list of appointments.
     *
     * @param appt
     */
    public void deleteAppt(Appt appt) {
        appts.remove(appt);
    }

    /**
     * Returns an immutable tag set, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<HealthService> getHealthServices() {
        return Collections.unmodifiableSet(healthServices);
    }

    /**
     * Returns true if both persons have the same NRIC.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getNric().equals(getNric());
    }

    // public void showDetails() {

    // }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return this.isSamePerson(otherPerson);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, nric, birthdate, sex, healthServices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("nric", nric)
                .add("sex", sex)
                .add("birthdate", birthdate)
                .add("phone", phone)
                .add("email", email)
                .add("Health Services", healthServices)
                .toString();
    }

}
