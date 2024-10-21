package seedu.address.model.person;

import java.util.Comparator;

/**
 * Compares two persons based on their names.
 */
public class PersonNameComparator implements Comparator<Person> {
    public int compare(Person p1, Person p2) {
        String p1Name = p1.getName().fullName.toLowerCase();
        String p2Name = p2.getName().fullName.toLowerCase();
        return p1Name.compareTo(p2Name);
    }
}
