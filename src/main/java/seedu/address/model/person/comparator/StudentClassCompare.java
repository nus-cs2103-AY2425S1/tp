package seedu.address.model.person.comparator;

import seedu.address.model.person.Person;

/**
 * Compares two Person objects based on their StudentClass.
 */
public class StudentClassCompare extends PersonCompare {

    @Override
    public int compare(Person p1, Person p2) {
        return p1.getStudentClass().compareTo(p2.getStudentClass());
    }

}
