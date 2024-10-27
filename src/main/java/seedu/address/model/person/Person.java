package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.role.Role;
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
    private final Optional<Role> role;
    private Wedding ownWedding;
    private final Set<Wedding> weddingJobs = new HashSet<>();

    //    /**
    //     * Every field must be present and not null.
    //     */
    //    public Person(Name name, Phone phone, Email email, Address address, Role role) {
    //        requireAllNonNull(name, phone, email, address, role);
    //        this.name = name;
    //        this.phone = phone;
    //        this.email = email;
    //        this.address = address;
    //        this.role = role;
    //        this.ownWedding = null;
    //    }

    /**
     * Every field, except tag and wedding, must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Optional<Role> role, Wedding ownWedding) {
        requireAllNonNull(name, phone, email, address, role);
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

    /**
     * Returns an immutable role set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Optional<Role> getRole() {
        return role;
    }

    public Wedding getOwnWedding() {
        return ownWedding;
    }

    public Set<Wedding> getWeddingJobs() {
        return weddingJobs;
    }

    /**
     * Adds a wedding to the list of wedding jobs.
     *
     * @param wedding {@code Wedding} to be added to the list of wedding jobs
     */
    public void addWeddingJob(Wedding wedding) {
        if (ownWedding == null || !ownWedding.isSameWedding(wedding)) {
            weddingJobs.add(wedding);
        } else {
            throw new IllegalArgumentException("Cannot add own wedding as a job.");
        }
    }

    /**
     * Adds a list of wedding jobs to the pre-existing list.
     *
     * @param weddingJobs {@code Set<Wedding>} to be added to the list of wedding jobs
     */
    public void setWeddingJobs(Set<Wedding> weddingJobs) {
        for (Wedding wedding : weddingJobs) {
            this.addWeddingJob(wedding);
        }
    }

    /**
     * Returns true if both persons have the same name, phone, email, address.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == null) {
            return false;
        }
        return this.name.equals(otherPerson.name)
                && this.phone.equals(otherPerson.phone)
                && this.email.equals(otherPerson.email)
                && this.address.equals(otherPerson.address);
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
                && address.equals(otherPerson.address)
                // && role.equals(otherPerson.role)
                // && ownWedding.equals(otherPerson.ownWedding)
                && weddingJobs.equals(otherPerson.weddingJobs);

        // commented them out since they give null pointer exception
        // need to use Optional
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, role);
    }

    @Override
    public String toString() {
        String nullString = "";

        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("roles", role)
                .add("wedding", ownWedding == null ? "null" : ownWedding)
                .add("wedding jobs", weddingJobs)
                .toString();
    }
}
