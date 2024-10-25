package tahub.contacts.model;

import javafx.collections.ObservableList;
import tahub.contacts.model.person.Person;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    Person getPersonByMatricNumber(String matricNumber);
}
