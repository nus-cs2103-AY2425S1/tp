package seedu.address.model.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Represents a Contact Set of a Wedding.
 * Guarantees: details are present and not null, field values are validated.
 */
public class ContactSet {
    private Set<Person> set;

    /**
     * Creates an empty ContactSet.
     */
    public ContactSet() {
        this.set = new HashSet<>();
    }

    /**
     * Returns true if the role is assigned.
     *
     * @param role role to check.
     * @return whether the role is assigned.
     */
    public boolean hasRole(Tag role) {
        return set.stream().anyMatch(x -> x.getRole().equals(role));
    }

    /**
     * Adds a person to the set.
     *
     * @param person person with the role.
     * @throws IllegalArgumentException if the role is already assigned.
     */
    public void addToMap(Tag role, Person person) {
        requireAllNonNull(role, person);
        if (this.hasRole(role)) {
            throw new IllegalArgumentException("This role is already assigned.");
        }
        set.add(person);
    }

    /**
     * Removes a person from the set.
     *
     * @param person person with the role.
     */
    public void removeFromMap(Tag role, Person person) {
        requireAllNonNull(role, person);
        if (!this.hasRole(role)) {
            throw new IllegalArgumentException("This role is not assigned.");
        }
        set.remove(person);
    }

    /**
     * Retrieves person of the specified role.
     *
     * @param role person of this role.
     * @return Optional person of role.
     */
    public Optional<Person> getPersonOfRole(Tag role) {
        requireNonNull(role);
        return set.stream().filter(x -> x.getRole().equals(role)).findFirst();
    }

    @Override
    public String toString() {
        ToStringBuilder str = new ToStringBuilder(this);
        for (Person person : set) {
            Tag role = person.getRole();
            str.add("person", person.toString());
        }
        return str.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ContactSet)) {
            return false;
        }

        ContactSet otherContactSet = (ContactSet) other;
        return set.equals(otherContactSet.set);
    }

    @Override
    public int hashCode() {
        return Objects.hash(set);
    }
}
