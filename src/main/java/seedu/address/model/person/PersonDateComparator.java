package seedu.address.model.person;

import java.util.Comparator;

/**
 * A Comparator object that compares two persons based on their name.
 */
public class PersonDateComparator implements Comparator<Person> {
    /**
     * Compares two persons based on their names.
     */
    public int compare(Person p1, Person p2) {
        return p1.getContractEndDate().compareTo(p2.getContractEndDate());
    }
}
