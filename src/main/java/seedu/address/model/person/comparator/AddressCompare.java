package seedu.address.model.person.comparator;

import seedu.address.model.person.Person;

/**
 * A class to compare two Person objects with their Address
 */
public class AddressCompare extends PersonCompare {

    @Override
    public int compare(Person p1, Person p2) {
        return p1.getAddress().compareTo(p2.getAddress());
    }

}
