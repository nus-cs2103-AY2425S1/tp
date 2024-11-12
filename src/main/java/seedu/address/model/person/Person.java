package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.doctor.Speciality;
import seedu.address.model.patient.DateOfBirth;
import seedu.address.model.patient.Gender;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Appointment> appointments = new HashSet<>();

    // Doctor-specific data fields
    private final Speciality speciality;

    // Patient-specific data fields
    private final DateOfBirth dateOfBirth;
    private final Gender gender;

    /**
     * Constructor to initialize all fields. Only person fields must not be null.
     */
    public Person(
        Name name,
        Phone phone,
        Email email,
        Address address,
        Speciality speciality,
        DateOfBirth dateOfBirth,
        Gender gender,
        Set<Tag> tags
    ) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);

        this.speciality = speciality;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    /**
     * Constructor with the person fields.
     */
    public Person(
        Name name,
        Phone phone,
        Email email,
        Address address,
        Set<Tag> tags
    ) {
        this(name, phone, email, address, null, null, null, tags);
    }

    /**
     * Constructor with the speciality field.
     */
    public Person(
        Name name,
        Phone phone,
        Email email,
        Address address,
        Speciality speciality,
        Set<Tag> tags
    ) {
        this(name, phone, email, address, speciality, null, null, tags);
        requireNonNull(speciality);
    }

    /**
     * Constructor with the dateOfBirth and gender fields.
     */
    public Person(
        Name name,
        Phone phone,
        Email email,
        Address address,
        DateOfBirth dateOfBirth,
        Gender gender,
        Set<Tag> tags
    ) {
        this(name, phone, email, address, null, dateOfBirth, gender, tags);
        requireAllNonNull(dateOfBirth, gender);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public DateOfBirth getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable appointment set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Appointment> getAppointments() {
        return Collections.unmodifiableSet(appointments);
    }

    /**
     * Add an appointment to the set of appointments.
     * @param appointment The appointment to add.
     * @return Whether the set did not already contain the appointment.
     */
    public boolean addAppointment(Appointment appointment) {
        return appointments.add(appointment);
    }

    /**
     * Remove an appointment from the set of appointments.
     * @param appointment The appointment to remove.
     * @return Whether the appointment was removed.
     */
    public boolean removeAppointment(Appointment appointment) {
        return appointments.remove(appointment);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

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
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

}
