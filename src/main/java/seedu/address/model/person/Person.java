package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.log.Log;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final IdentityNumber identityNumber;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    //private final LogsList logsList;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Log> logs = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, IdentityNumber identityNumber, Phone phone, Email email, Address address, Set<Tag> tags,
                  Set<Log> logs) {
        requireAllNonNull(name, identityNumber, phone, email, address, tags);
        this.name = name;
        this.identityNumber = identityNumber;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.logs.addAll(logs);
    }

    public Name getName() {
        return name;
    }
    public IdentityNumber getIdentityNumber() {
        return identityNumber;
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
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable log set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Log> getLogs() {
        return Collections.unmodifiableSet(logs);
    }

    /**
     * Returns true if both persons have the same Identity Number.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getIdentityNumber().equals(getIdentityNumber());
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
                && identityNumber.equals(otherPerson.identityNumber)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && logs.equals(otherPerson.logs);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, identityNumber, phone, email, address, tags, logs);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("identity number", identityNumber)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

}
