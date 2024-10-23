package seedu.address.model.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Represents a Contact Map of a Wedding.
 * Guarantees: details are present and not null, field values are validated.
 */
public class ContactMap {
    private Map<Tag, Person> map;

    /**
     * Creates an empty ContactMap.
     */
    public ContactMap() {
        this.map = new HashMap<>();
    }

    /**
     * Returns true if the role is assigned.
     *
     * @param role role to check.
     * @return whether the role is assigned.
     */
    public boolean hasRole(Tag role) {
        return map.containsKey(role);
    }

    /**
     * Adds a role and person to the map.
     *
     * @param role role to be added.
     * @param person person with the role.
     * @throws IllegalArgumentException if the role is already assigned.
     */
    public void addToMap(Tag role, Person person) {
        requireAllNonNull(role, person);
        if (this.hasRole(role)) {
            throw new IllegalArgumentException("This role is already assigned.");
        }
        map.put(role, person);
    }

    /**
     * Removes a role and person from the map.
     *
     * @param role role to be removed.
     * @param person person with the role.
     */
    public void removeFromMap(Tag role, Person person) {
        requireAllNonNull(role, person);
        if (!this.hasRole(role)) {
            throw new IllegalArgumentException("This role is not assigned.");
        }
        map.remove(role, person);
    }

    /**
     * Retrieves person of the specified role.
     *
     * @param role person of this role.
     * @return person of role.
     */
    public Optional<Person> getPersonOfRole(Tag role) {
        requireNonNull(role);
        return Optional.ofNullable(map.get(role));
    }

    @Override
    public String toString() {
        ToStringBuilder str = new ToStringBuilder(this);
        for (Map.Entry<Tag, Person> entry : map.entrySet()) {
            Tag role = entry.getKey();
            Person person = entry.getValue();
            str.add("role", role.toString())
                    .add("person", person.toString());
        }
        return str.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ContactMap)) {
            return false;
        }

        ContactMap otherContactMap = (ContactMap) other;
        return map.equals(otherContactMap.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }
}
