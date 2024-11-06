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

        if (wedding == null) {
            throw new NullPointerException("Wedding cannot be null.");
        }
        ownWedding = wedding;
        wedding.setClient(this);
    }

    /**
     * Reset the {@code ownWedding} status of person.
     *
     * @param wedding {@code Wedding} object to check against {@code ownWedding}
     */
    public void resetOwnWedding(Wedding wedding) {
        if (this.ownWedding == null) {
            return;
        }

        if (this.ownWedding.equals(wedding)) {
            this.ownWedding = null;
        }
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
     * Throws IllegalArgumentException if attempting to add own wedding as a job.
     *
     * @param wedding {@code Wedding} to be added to the list of wedding jobs
     * @throws IllegalArgumentException if the wedding is the person's own wedding
     */
    public void addWeddingJob(Wedding wedding) {
        if (ownWedding != null && ownWedding.equals(wedding)) {
            throw new IllegalArgumentException("Cannot add own wedding as a job.");
        }
        weddingJobs.add(wedding);
    }

    /**
     * Checks if this person is already assigned to the given wedding.
     *
     * @param wedding The wedding to check
     * @return true if the person is already assigned to the wedding
     */
    public boolean isAssignedToWedding(Wedding wedding) {
        return weddingJobs.contains(wedding)
                || (ownWedding != null && ownWedding.equals(wedding));
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
     * Checks if the {@code weddingJobs} of the person contains the Wedding object.
     *
     * @param target {@code Wedding} object to be found
     * @return true if {@code target} is found in {@code weddingJobs}
     */
    public boolean containsWeddingJob(Wedding target) {
        for (Wedding weddingJob : weddingJobs) {
            if (weddingJob.equals(target)) {
                return true;
            }
        }
        return false;
    }

    public void removeWeddingJob(Wedding weddingJob) {
        this.weddingJobs.remove(weddingJob);
    }

    /*
     * Returns true if person has own wedding.
     *
     */
    public boolean hasOwnWedding() {
        return ownWedding != null;
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
        //TODO HERE
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
                .add("roles", role.isPresent() ? role : "NA")
                .add("wedding", ownWedding == null ? "NA" : ownWedding)
                .add("wedding jobs", weddingJobs)
                .toString();
    }
}
