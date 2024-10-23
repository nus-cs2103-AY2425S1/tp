package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.From;
import seedu.address.model.appointment.To;
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
    private final Property property;
    private final Appointment appointment;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Set<Tag> tags, Appointment appointment, Property property) {
        requireAllNonNull(name, phone, appointment, property);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.appointment = appointment;
        this.property = property;
        this.tags.addAll(tags);

    }

    /**
     * Constructs a {@code Person} object with the specified name.
     * Initializes the phone number as {@code null}, sets the property to a default empty value,
     * and creates an appointment with default values for the date, start time, and end time.
     *
     * @param name The {@code Name} of the person. Must not be {@code null}.
     */
    public Person(Name name) {
        this.name = name;
        this.phone = null;
        this.email = null;
        this.property = new Property("");
        this.appointment = new Appointment(new Date(""), new From(""), new To(""));
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

    public Property getProperty() {
        return property;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
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
                && tags.equals(otherPerson.tags);

    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, appointment, property, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("tags", tags)
                .add("appointment", appointment)
                .add("property", property)
                .toString();
    }

    public abstract String getRole();
}
