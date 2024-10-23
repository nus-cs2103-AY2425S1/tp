package seedu.address.model;

import seedu.address.model.person.Person;

import java.util.Comparator;

public class ComparatorName implements Comparator<Person> {
    public int compare(Person contact1, Person contact2) {
        int compareNumberByName = contact1.getName().compareTo(contact2.getName());
        if (compareNumberByName == 0) {
            return contact1.getNickname().compareTo(contact2.getNickname());
        }
        return compareNumberByName;
    }
}
