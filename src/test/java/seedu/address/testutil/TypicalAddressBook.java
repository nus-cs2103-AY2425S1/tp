package seedu.address.testutil;

import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * A utility class generating a typical address book with {@code Person} and {@code Event} objects
 * to be used in tests.
 */
public class TypicalAddressBook {

    private TypicalAddressBook() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons and events.
     */
    public static AddressBook getTypicalAddressBook() {
        List<Event> events = TypicalEvents.getTypicalEvents();
        List<Person> persons = TypicalPersons.getTypicalPersons();

        AddressBook ab = new AddressBook();
        ab.setEvents(events);
        ab.setPersons(persons);

        return ab;
    }

}
