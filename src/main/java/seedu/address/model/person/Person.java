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
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Nric nric;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Role> roles = new HashSet<>();

    // Dependents
    private final Set<Person> caregivers = new HashSet<>();
    private final Set<Person> patients = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Nric nric, Phone phone, Email email, Address address, Set<Tag> tags, Set<Role> roles) {
        requireAllNonNull(name, nric, phone, email, address, tags, roles);
        this.name = name;
        this.nric = nric;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.roles.addAll(roles);
    }

    /**
     * Constructor to make a deep copy of a Person object.
     */
    public Person(Person otherperson) {
        this.name = otherperson.getName();
        this.nric = otherperson.getNric();
        this.phone = otherperson.getPhone();
        this.email = otherperson.getEmail();
        this.address = otherperson.getAddress();
        this.tags.addAll(otherperson.getTags());
        this.roles.addAll(otherperson.getRoles());
    }

    public Name getName() {
        return name;
    }

    public Nric getNric() {
        return nric;
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

    public boolean hasCaregiver(Person caregiver) {
        return caregivers.contains(caregiver);
    }

    public boolean hasPatient(Person patient) {
        return patients.contains(patient);
    }

    public void addCaregiver(Person caregiver) {
        caregivers.add(caregiver);
    }

    public void addPatient(Person patient) {
        patients.add(patient);
    }

    /**
     * Removes the specified caregiver from the list of caregivers if the caregiver is present.
     *
     * @param caregiver The caregiver to be removed.
     */
    public void removeCaregiver(Person caregiver) {
        if (this.hasCaregiver(caregiver)) {
            caregivers.remove(caregiver);
        }

    }

    /**
     * Removes the specified patient from the list of patients if the patient is present.
     *
     * @param patient The patient to be removed.
     */
    public void removePatient(Person patient) {
        if (this.hasPatient(patient)) {
            patients.remove(patient);
        }
    }

    /**
     * Returns an immutable tag set, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable role set, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
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
                && nric.equals(otherPerson.nric)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, nric, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("nric", nric)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("roles", roles)
                .toString();
    }

}
