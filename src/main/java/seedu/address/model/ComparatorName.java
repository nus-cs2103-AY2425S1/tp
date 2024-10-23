package seedu.address.model;

import seedu.address.model.person.Person;

import java.util.Comparator;


/**
 * A comparator class that sorts the person contacts by name in terms of alphabetical order. If the full
 * name of the person contacts is the same, the nickname will be used as the basis for sorting in
 * alphabetical order as well.
 */
public class ComparatorName implements Comparator<Person> {
    final int EQUAL_STATUS = 0;

    public int compare(Person contact1, Person contact2) {
        int compareNumberByName = contact1.getName().compareTo(contact2.getName());
        if (compareNumberByName == EQUAL_STATUS) {
            return contact1.getNickname().compareTo(contact2.getNickname());
        }
        return compareNumberByName;
    }
}
