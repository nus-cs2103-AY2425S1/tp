package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.appointment.Appointment;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Nric nric;

    // Data fields
    private final Address address;
    private final DateOfBirth dateOfBirth;
    private final Gender gender;
    private final Priority priority;
    private final Set<Allergy> allergies = new HashSet<>();
    private final Set<Appointment> appointments = new HashSet<>();
    private final Set<MedCon> medCons = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Nric nric, Address address, DateOfBirth dateOfBirth,
                  Gender gender, Set<Allergy> allergies, Priority priority,
                  Set<Appointment> appointments, Set<MedCon> medCons) {
        requireAllNonNull(name, phone, email, nric, address, dateOfBirth, gender, allergies, priority, appointments,
                medCons);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.nric = nric;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.allergies.addAll(allergies);
        this.priority = priority;
        this.appointments.addAll(appointments);
        this.medCons.addAll(medCons);
    }

    public Priority getPriority() {
        return priority;
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

    public Nric getNric() {
        return nric;
    }

    public DateOfBirth getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    /**
     * Returns an immutable allergy set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Allergy> getAllergies() {
        return Collections.unmodifiableSet(allergies);
    }

    /**
     * Returns an immutable appointment set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Appointment> getAppointments() {
        return Collections.unmodifiableSet(appointments);
    }

    /**
     * Returns an immutable set of medical conditions, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<MedCon> getMedCons() {
        return Collections.unmodifiableSet(medCons);
    }

    /**
     * Returns true if both persons have the same Nric.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getNric().equals(getNric());
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
                && nric.equals(otherPerson.nric)
                && dateOfBirth.equals(otherPerson.dateOfBirth)
                && gender.equals(otherPerson.gender)
                && address.equals(otherPerson.address)
                && allergies.equals(otherPerson.allergies)
                && appointments.equals(otherPerson.appointments)
                && medCons.equals(otherPerson.medCons)
                && priority.equals(otherPerson.priority);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, nric, dateOfBirth, gender, address, allergies, priority, appointments,
                medCons);
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
                .add("priority", priority)
                .add("appointments", appointments)
                .add("medical conditions", medCons)
                .toString();
    }

}
