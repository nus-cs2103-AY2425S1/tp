package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.Wedding;

/**
 * Represents a Person in the address book.
 * Guarantees: Field values are validated and immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Tag role;

    private final Set<Tag> tags = new HashSet<>(); // to remove
    private Wedding ownWedding;
    private final Set<Wedding> weddingJobs = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) { // to remove
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.role = null;
        this.ownWedding = null;
    }

    /**
     * Every field, except tag and wedding, must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Tag role, Wedding ownWedding) {
        requireAllNonNull(name, phone, email, address);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.role = role;
        this.ownWedding = ownWedding;
    }

    public void setOwnWedding(Wedding wedding) {
        ownWedding = wedding;
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

    public Tag getRole() {
        return role;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() { // to remove
        return Collections.unmodifiableSet(tags);
    } // to remove

    public Wedding getOwnWedding() {
        return ownWedding;
    }

    public Set<Wedding> getWeddingJobs() {
        return weddingJobs;
    }

    public void addWeddingJob(Wedding wedding) {
        weddingJobs.add(wedding);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        return this.equals(otherPerson);
    }

    /**
     * Returns true if both persons have the same phone number.
     */
    public boolean hasSamePhone(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getPhone().equals(getPhone());
    }

    /**
     * Returns true if both persons have the same email address.
     */
    public boolean hasSameEmail(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getEmail().equals(getEmail());
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
                && address.equals(otherPerson.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email, address);
    }

    @Override
    public String toString() {
        String nullString = "";

        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tag", role == null ? nullString : role)
                .add("wedding", ownWedding == null ? nullString : ownWedding)
                .add("wedding jobs", weddingJobs)
                .toString();
    }

}
