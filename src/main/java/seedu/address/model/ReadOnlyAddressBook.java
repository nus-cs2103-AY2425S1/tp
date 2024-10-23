package seedu.address.model;

import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

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
     * Returns the person with corresponding personId if
     * it exists in the person observable list.
     */
    Optional<Person> findPerson(int personId);

    /**
     * Returns the next person ID
     */
    int getNextPersonId();
}
