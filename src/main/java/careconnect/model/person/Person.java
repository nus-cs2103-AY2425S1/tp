package careconnect.model.person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import careconnect.commons.util.CollectionUtil;
import careconnect.commons.util.ToStringBuilder;
import careconnect.model.log.Log;
import careconnect.model.tag.Tag;

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
    private final ArrayList<Log> logs = new ArrayList<>();
    private final AppointmentDate appointmentDate;

    /**
     * Every field in params list here must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                  ArrayList<Log> logs, AppointmentDate appointmentDate) {
        CollectionUtil.requireAllNonNull(name, phone, email, address, tags, logs);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.logs.addAll(logs);
        this.appointmentDate = appointmentDate;
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

    public AppointmentDate getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable log list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Log> getLogs() {
        return Collections.unmodifiableList(this.logs);
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
        if (!(other instanceof Person otherPerson)) {
            return false;
        }

        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && logs.equals(otherPerson.logs)
                && appointmentDate.equals(otherPerson.appointmentDate);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, appointmentDate, logs);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("appointmentDate", appointmentDate)
                .add("tags", tags)
                .add("logs", logs)
                .toString();
    }

}
