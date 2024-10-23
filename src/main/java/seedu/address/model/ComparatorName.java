package seedu.address.model;

import seedu.address.model.person.Person;

import java.util.Comparator;

// javadoc comments?
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
