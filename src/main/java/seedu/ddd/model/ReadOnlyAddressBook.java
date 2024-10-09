package seedu.ddd.model;

import javafx.collections.ObservableList;
import seedu.ddd.model.person.Contact;
import seedu.ddd.model.person.Person;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    ObservableList<Contact> getContactList();
}
