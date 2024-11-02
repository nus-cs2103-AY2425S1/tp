package seedu.address.model.person;

import java.util.Comparator;

/**
 * A Comparator object that compares two persons based on their role.
 */
public class PersonRoleComparator implements Comparator<Person> {
    /**
     * Compares two persons based on their role.
     */
    public int compare(Person p1, Person p2) {
        String p1Role = p1.getRole().value.toLowerCase();
        String p2Role = p2.getRole().value.toLowerCase();
        return p1Role.compareTo(p2Role);
    }
}
