package seedu.ddd.model;

import javafx.collections.ObservableList;
import seedu.ddd.model.contact.common.Contact;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the contact list.
     * This list will not contain any duplicate contacts.
     */
    ObservableList<Contact> getContactList();
}
