package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
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
    private final Nric nric;

    // Data fields
    private final Address address;
    private final Remark remark;
    private final Set<Tag> tags = new HashSet<>();
    private Appointment appointment; // New appointment field
    private final LogList logEntries;

    /**
     * Every field must be present and not null.
     */
    public Person(
            Name name,
            Phone phone,
            Email email,
            Nric nric,
            Address address,
            Remark remark,
            Set<Tag> tags,
            Appointment appointment) {
        requireAllNonNull(name, phone, email, nric, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.nric = nric;
        this.address = address;
        this.remark = remark;
        this.tags.addAll(tags);
        this.appointment = appointment; // Appointment field initialized
        this.logEntries =  new LogList();
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

    public Nric getNric() {
        return nric;
    }

    public Address getAddress() {
        return address;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public LogList getLogEntries() {
        return logEntries;
    }

    public void addLogEntry(Log log) {
        logEntries.addLog(log);
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
                && nric.equals(otherPerson.nric)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && Objects.equals(appointment, otherPerson.appointment) // Include appointment in equality check
                && logEntries.equals(otherPerson.logEntries);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, nric, address, tags, appointment, logEntries); // Include appointment in hashCode
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("nric", nric)
                .add("address", address)
                .add("remark", remark)
                .add("tags", tags)
                .add("appointment", appointment) // Include appointment in toString
                .add("logEntries", logEntries)
                .toString();
    }
}
