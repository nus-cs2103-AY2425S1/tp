package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

import java.util.List;

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
