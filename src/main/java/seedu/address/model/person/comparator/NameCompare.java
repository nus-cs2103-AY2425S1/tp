package seedu.address.model.person.comparator;

import seedu.address.model.person.Person;

/**
 * Compares two Person objects based on their Name.
 */
public class NameCompare extends PersonCompare {

    @Override
    public int compare(Person p1, Person p2) {
        return p1.getName().compareTo(p2.getName());
    }

}
