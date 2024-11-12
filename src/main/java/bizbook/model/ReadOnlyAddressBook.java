package bizbook.model;

import bizbook.model.person.Person;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the pinned person list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPinnedPersonList();

}
