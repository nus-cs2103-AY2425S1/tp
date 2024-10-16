package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.concert.Concert;
import seedu.address.model.person.Person;

/**
 * A utility class to generate AddressBooks
 */
public abstract class TypicalAddressBook {
    /**
     * Returns an {@code AddressBook} with all typical concerts and persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : TypicalPersons.getTypicalPersons()) {
            ab.addPerson(person);
        }
        for (Concert concert : TypicalConcerts.getTypicalConcerts()) {
            ab.addConcert(concert);
        }
        return ab;
    }
}
