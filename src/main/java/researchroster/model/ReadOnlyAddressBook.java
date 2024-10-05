package researchroster.model;

import researchroster.model.person.Person;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an researchroster book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

}
