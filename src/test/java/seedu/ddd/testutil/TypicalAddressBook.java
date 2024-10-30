package seedu.ddd.testutil;

import static seedu.ddd.testutil.contact.TypicalContacts.getTypicalContacts;
import static seedu.ddd.testutil.event.TypicalEvents.getTypicalEvents;

import seedu.ddd.model.AddressBook;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.event.common.Event;

/**
 * A utility class containing a list of {@code AddressBook} objects to be used in tests.
 */
public class TypicalAddressBook {

    // Prevents instantiation
    private TypicalAddressBook() {}

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
        int maxContactId = getTypicalContacts().stream()
                .map(Contact::getId).map(id -> id.id)
                .max(Integer::compareTo).orElse(-1);
        int maxEventId = getTypicalEvents().stream()
                .map(Event::getEventId).map(id -> id.id)
                .max(Integer::compareTo).orElse(-1);
        AddressBook.setNextContactId(maxContactId + 1);
        AddressBook.setNextEventId(maxEventId + 1);
        return ab;
    }
}
