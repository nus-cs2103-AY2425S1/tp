package seedu.address.model;

import java.util.Comparator;

import seedu.address.model.person.Person;

/**
 * A comparator class that sorts the person contacts by name in terms of alphabetical order. If the full
 * name of the person contacts is the same, the nickname will be used as the basis for sorting in
 * alphabetical order as well.
 */
public class ComparatorName implements Comparator<Person> {
    static final int EQUAL_STATUS = 0;

    /**
     * Compares the person contacts by name and then nickname in alphabetical order.
     * @param contact1 the first object to be compared.
     * @param contact2 the second object to be compared.
     * @return
     */
    public int compare(Person contact1, Person contact2) {
        int compareNumberByName = contact1.getName().compareTo(contact2.getName());
        if (compareNumberByName == EQUAL_STATUS) {
            return contact1.getNickname().compareTo(contact2.getNickname());
        }
        return compareNumberByName;
    }
}
