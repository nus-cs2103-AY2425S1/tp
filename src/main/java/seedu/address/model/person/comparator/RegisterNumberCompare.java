package seedu.address.model.person.comparator;

import seedu.address.model.person.Person;

/**
 * Compares two Person objects based on their RegisterNumber.
 */
public class RegisterNumberCompare extends PersonCompare {

    @Override
    public int compare(Person p1, Person p2) {
        return p1.getRegisterNumber().compareTo(p2.getRegisterNumber());
    }

}
