package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.name.Name;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private String remark = "No remarks yet.";
    private final Appointment appointment;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Set<Tag> tags,
                  Appointment appointment) {
        requireAllNonNull(name, phone, appointment);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.appointment = appointment;
        this.tags.addAll(tags);
    }

    /**
     * Every field must be present and not null, excluding remark.
     */
    public Person(Name name, Phone phone, Email email, Set<Tag> tags,
                  Appointment appointment, String remark) {
        requireAllNonNull(name, phone, appointment);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.appointment = appointment;
        this.tags.addAll(tags);
        this.remark = remark;
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

    public Appointment getAppointment() {
        return appointment;
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public String getRemark() {
        return remark;
    }
    public void updateRemark(String newRemark) {
        remark = newRemark;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        if (otherPerson == null) {
            return false;
        }

        // Case-insensitive name comparison
        return otherPerson.getName().fullName.equalsIgnoreCase(this.getName().fullName);
    }

    public boolean hasAppointment() {
        return !appointment.equals(Appointment.EMPTY_APPOINTMENT);
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

        boolean hasSameName = name.equals(otherPerson.name);
        boolean hasSamePhone = phone.equals(otherPerson.phone);
        boolean hasSameEmail = email.equals(otherPerson.email);
        boolean hasSameTags = tags.equals(otherPerson.tags);
        return hasSameName && hasSamePhone && hasSameEmail && hasSameTags;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, appointment, tags, remark);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("tags", tags)
                .add("appointment", appointment)
                .add("remark", remark)
                .toString();
    }

    public abstract Role getRole();
}
