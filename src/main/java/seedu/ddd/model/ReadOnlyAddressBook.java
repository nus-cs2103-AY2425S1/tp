package seedu.ddd.model;

import javafx.collections.ObservableList;
import seedu.ddd.model.person.Contact;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate contacts.
     */
    ObservableList<Contact> getContactList();
}
