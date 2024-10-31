package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: Name and Phone are present and not null, all field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private Email email; // Optional, can be unassigned

    // Data fields
    private Address address; // Optional, can be unassigned
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Constructor for Person with Email and Address
     * All fields must not be null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    /**
     * Constructor for Person without Address
     * All fields must not be null.
     */
    public Person(Name name, Phone phone, Email email, Set<Tag> tags) {
        requireAllNonNull(name, phone, email);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.tags.addAll(tags);
    }

    /**
     * Constructor for Person without Email
     * All fields must not be null.
     */
    public Person(Name name, Phone phone, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, address);
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.tags.addAll(tags);
    }

    /**
     * Constructor for Person without Email and Address
     * All fields must not be null.
     */
    public Person(Name name, Phone phone, Set<Tag> tags) {
        requireAllNonNull(name, phone);
        this.name = name;
        this.phone = phone;
        this.tags.addAll(tags);
    }

    /**
     * Constructor for Person in general
     * Returns a Person object using the different constructors given the respective fields.
     */
    public static Person personConstructor(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        if (email == null && address == null) {
            return new Person(name, phone, tags);
        } else if (email == null) {
            return new Person(name, phone, address, tags);
        } else if (address == null) {
            return new Person(name, phone, email, tags);
        } else {
            return new Person(name, phone, email, address, tags);
        }
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Optional<Email> getEmail() {
        return Optional.ofNullable(email);
    }

    public Optional<Address> getAddress() {
        return Optional.ofNullable(address);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
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
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        ToStringBuilder result = new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone);
        if (this.getEmail().isPresent()) {
            result.add("email", getEmail().get());
        } else {
            result.add("email", null);
        }
        if (this.getAddress().isPresent()) {
            result.add("address", getAddress().get());
        } else {
            result.add("address", null);
        }
        result.add("tags", tags);

        return result.toString();
    }
}
