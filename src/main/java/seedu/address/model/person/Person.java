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
    private final Age age;
    private final Gender gender;
    private final Nric nric;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private Appointment appointment;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Age age, Gender gender, Nric nric, Phone phone, Email email,
                  Address address, Appointment appointment, Set<Tag> tags) {

        requireAllNonNull(name, age, gender, nric, phone, email, address, tags);
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.nric = nric;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.appointment = appointment;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Age getAge() {
        return age;
    }

    public Nric getNric() {
        return nric;
    }

    public Gender getGender() {
        return gender;
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

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public void deleteAppointment() {
        appointment = new Appointment(null);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same NRIC since they are unique.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null && otherPerson.getNric().equals(getNric());
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
                && age.equals(otherPerson.age)
                && gender.equals(otherPerson.gender)
                && nric.equals(otherPerson.nric)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && appointment.equals(otherPerson.appointment)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, age, gender, nric, phone, email, address, appointment, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("age", age)
                .add("gender", gender)
                .add("nric", nric)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("appointment", appointment)
                .add("tags", tags)
                .toString();
    }

}
