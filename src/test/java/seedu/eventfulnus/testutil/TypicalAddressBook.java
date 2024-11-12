package seedu.eventfulnus.testutil;

import static seedu.eventfulnus.testutil.TypicalEvents.getTypicalEvents;
import static seedu.eventfulnus.testutil.TypicalPersons.getTypicalPersons;

import seedu.eventfulnus.model.AddressBook;
import seedu.eventfulnus.model.event.Event;
import seedu.eventfulnus.model.person.Person;

/**
 * A utility class containing an {@link AddressBook} objects to be used in tests.
 */
public class TypicalAddressBook {
    /**
     * Returns an {@link AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        for (Event event : getTypicalEvents()) {
            ab.addEvent(event);
        }
        return ab;
    }
}
