package seedu.address.model.person.comparator;

import seedu.address.model.person.Person;

/**
 * A class to compare two Person objects based on their EcNumber
 */
public class EcNumberCompare extends PersonCompare {

    @Override
    public int compare(Person p1, Person p2) {
        return p1.getEcNumber().compareTo(p2.getEcNumber());
    }

}
