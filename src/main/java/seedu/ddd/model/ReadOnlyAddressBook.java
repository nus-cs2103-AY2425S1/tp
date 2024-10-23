package seedu.ddd.model;

import javafx.collections.ObservableList;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.event.common.Event;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the contact list.
     * This list will not contain any duplicate contacts.
     */
    ObservableList<Contact> getContactList();

    /**
     * Returns an unmodifiable view of the event list.
     * This list will not contain any duplicate events.
     */
    ObservableList<Event> getEventList();
}
