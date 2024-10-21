package seedu.address.model.wedding;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class ContactMap {
    private HashMap<Role, Person> map;

    /**
     * Creates an empty ContactList
     */
    public ContactMap() {
        this.map = new HashMap<>();
    }

    /**
     * Returns true if the role exist in the ContactList
     */
    public boolean hasRole(Role role) {
        return map.containsKey(role);
    }

    public void addToList(Role role, Person person) {
        requireAllNonNull(role, person);
        if (this.hasRole(role)) {
            // throw exception
        }
        map.put(role, person);
    }

    public void removeFromList(Role role, Person person) {
        requireAllNonNull(role, person);
        if (!this.hasRole(role)) {
            // throw exception
        }
        map.remove(role, person);
    }

    public Person getPersonOfRole(Role role) {
        requireNonNull(role);
        if (!this.hasRole(role)) {
            // throw exception
        }
        return map.get(role);
    }

    @Override
    public String toString() {
        ToStringBuilder str =  new ToStringBuilder(this);
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
        return map.equals(otherContactMap.map);
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }
}
