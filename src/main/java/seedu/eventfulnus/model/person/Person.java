package seedu.eventfulnus.model.person;

import static seedu.eventfulnus.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.eventfulnus.commons.util.ToStringBuilder;
import seedu.eventfulnus.model.AddressBook;
import seedu.eventfulnus.model.ModelManager;
import seedu.eventfulnus.model.person.role.Role;

/**
 * Represents a {@code Person} in the {@link AddressBook} of the {@link ModelManager}.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {
    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Set<Role> roles = new HashSet<>();

    /**
     * Constructs a {@code Person}. Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Set<Role> roles) {
        requireAllNonNull(name, phone, email, roles);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.roles.addAll(roles);
    }

    public Name getName() {
        return name;
    }

    public String getNameString() {
        return name.toString();
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    /**
     * Returns an immutable {@link Set} of {@link Role}s, which throws {@link UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    /**
     * Returns true if both {@code Person}s have the same name and (phone or email).
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && (otherPerson.getPhone().equals(getPhone()) || otherPerson.getEmail().equals(getEmail()));
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
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && roles.equals(otherPerson.roles);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, roles);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("roles", roles)
                .toString();
    }
}
