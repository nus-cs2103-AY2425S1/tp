package seedu.address.model.person;

import java.util.Comparator;

/**
 * A Comparator object that compares two persons based on their name.
 */
public class PersonNameComparator implements Comparator<Person> {
    /**
     * Compares two persons based on their names.
     */
    public int compare(Person p1, Person p2) {
        String p1Name = p1.getName().fullName.toLowerCase();
        String p2Name = p2.getName().fullName.toLowerCase();
        return p1Name.compareTo(p2Name);
    }
}
