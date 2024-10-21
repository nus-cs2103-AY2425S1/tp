package seedu.address.model.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Represents a Contact Map of a Wedding.
 * Guarantees: details are present and not null, field values are validated.
 */
public class ContactMap {
    private Map<Role, Person> map;
    private Person husband; // Add husband
    private Person wife;    // Add wife

    /**
     * Creates an empty ContactList and assigns husband and wife.
     */
    public ContactMap(Person husband, Person wife) {
        requireAllNonNull(husband, wife);
        this.husband = husband;
        this.wife = wife;
        this.map = new HashMap<>();
    }

    /**
     * Returns true if the role is assigned.
     *
     * @param role role to check.
     * @return whether the role is assigned.
     */
    public boolean hasRole(Role role) {
        return map.containsKey(role);
    }

    /**
     * Adds a role and person to the map, preventing husband and wife from taking other roles.
     *
     * @param role role to be added.
     * @param person person with the role.
     */
    public void addToMap(Role role, Person person) {
        requireAllNonNull(role, person);
        if (this.hasRole(role)) {
            throw new IllegalArgumentException("This role is already assigned.");
        }
        if (person.equals(husband) || person.equals(wife)) {
            throw new IllegalArgumentException("This person is a spouse and cannot have another role.");
        }
        map.put(role, person);
    }

    /**
     * Removes a role and person from the map.
     *
     * @param role role to be removed.
     * @param person person with the role.
     */
    public void removeFromMap(Role role, Person person) {
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
    public Optional<Person> getPersonOfRole(Role role) {
        requireNonNull(role);
        return Optional.ofNullable(map.get(role));
    }

    @Override
    public String toString() {
        ToStringBuilder str = new ToStringBuilder(this);
        for (Map.Entry<Role, Person> entry : map.entrySet()) {
            Role role = entry.getKey();
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
        return map.equals(otherContactMap.map)
                && husband.equals(otherContactMap.husband)
                && wife.equals(otherContactMap.wife);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map, husband, wife);
    }
}

