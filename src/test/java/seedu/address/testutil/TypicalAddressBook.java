package seedu.address.testutil;

import static seedu.address.testutil.TypicalEvents.getTypicalEvents;
import static seedu.address.testutil.TypicalPersonEventManager.getTypicalPersonEventManager;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import seedu.address.model.AddressBook;
import seedu.address.model.types.common.LinkedPersonsEntry;
import seedu.address.model.types.event.Event;
import seedu.address.model.types.person.Person;

/**
 * A utility class containing a list of {@code Person} and {@code Event} objects to be used in tests.
 */
public class TypicalAddressBook {
    private TypicalAddressBook() {}; //prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons and events,
     * as well as typical linked persons entries.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }

        for (Event event : getTypicalEvents()) {
            ab.addEvent(event);
        }

        for (LinkedPersonsEntry entry : getTypicalPersonEventManager().getLinkedPersonsEntryList()) {
            ab.addLinkedPersonsEntry(entry);
        }
        return ab;
    }

}
