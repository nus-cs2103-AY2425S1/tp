package seedu.address.model.person.comparator;

import seedu.address.model.person.Person;

/**
 * A class to compare two Person objects with their Sex
 */
public class SexCompare extends PersonCompare {

    @Override
    public int compare(Person p1, Person p2) {
        return p1.getSex().compareTo(p2.getSex());
    }

}
