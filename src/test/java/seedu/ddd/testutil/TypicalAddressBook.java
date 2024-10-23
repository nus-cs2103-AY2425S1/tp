package seedu.ddd.testutil;

import static seedu.ddd.testutil.TypicalContacts.getTypicalContacts;
import static seedu.ddd.testutil.TypicalEvents.getTypicalEvents;

import seedu.ddd.model.AddressBook;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.event.common.Event;

/**
 * A utility class containing a list of {@code AddressBook} objects to be used in tests.
 */
public class TypicalAddressBook {
    private TypicalAddressBook() {
    } // Prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical contacts.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Contact contact : getTypicalContacts()) {
            ab.addContact(contact);
        }
        for (Event event : getTypicalEvents()) {
            ab.addEvent(event);
        }
        return ab;
    }
}
