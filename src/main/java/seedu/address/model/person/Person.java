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
    protected final Name name;
    protected final Role role;
    protected final Phone phone;
    protected final Email email;

    // Data fields
    protected final Address address;
    protected final Set<Tag> tags = new HashSet<>();


    /**
     * Constructs a {@code Person} with the specified fields and role.
     *
     * @param name The person's name.
     * @param role The person's role (e.g., VOLUNTEER, DONOR).
     * @param phone The person's phone number.
     * @param email The person's email.
     * @param address The person's address.
     * @param tags Tags associated with the person.
     */
    public Person(Name name, Role role, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.role = role;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    /**
     * Constructs a {@code Person} with the specified fields and a default role of {@code Role.PERSON}.
     *
     * @param name The person's name.
     * @param phone The person's phone number.
     * @param email The person's email.
     * @param address The person's address.
     * @param tags Tags associated with the person.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        this(name, Role.PERSON, phone, email, address, tags);
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
                && role.equals(otherPerson.role)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, role, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

    public Role getRole() {
        return role;
    }

    /**
     * Returns the role as a lowercase string.
     *
     * @return the role name in lowercase format
     */
    public String getRoleAsLowerCaseString() {
        return role.toLowerCaseString();
    }

    public boolean isRole(Role role) {
        return getRole().equals(role);
    }

    /**
     * Compares this person's name with another person's name lexicographically, ignoring case considerations.
     *
     * @param otherPerson The other person whose name is to be compared.
     * @return A negative integer, zero, or a positive integer as this person's name
     *         is less than, equal to, or greater than the specified person's name, ignoring case.
     * @throws NullPointerException if {@code otherPerson} is null or if {@code otherPerson.name} is null.
     */
    public int compareNamesIgnorecase(Person otherPerson) {
        return name.compareToIgnoreCase(otherPerson.name);
    }

    public boolean hasPhoneNumber(String phoneNumber) {
        return this.phone.isSameValue(phoneNumber);
    }
}
