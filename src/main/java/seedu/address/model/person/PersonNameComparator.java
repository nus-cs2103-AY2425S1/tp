package seedu.address.model.person;

import java.util.Comparator;

/**
 * Compares two persons based on their names.
 */
public class PersonNameComparator implements Comparator<Person> {
    public int compare(Person p1, Person p2) {
        return p1.getName().fullName.compareTo(p2.getName().fullName);
    }
}
