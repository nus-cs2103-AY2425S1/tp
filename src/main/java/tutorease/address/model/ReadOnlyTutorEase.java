package tutorease.address.model;

import javafx.collections.ObservableList;
import tutorease.address.model.person.Person;

/**
 * Gives an unmodifiable view of an address book.
 */
public interface ReadOnlyTutorEase {

    /**
     * Returns an unmodifiable view of the persons list.
     *
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns the person with the specified name.
     *
     * @param name The name of the person to retrieve.
     * @return The person with the specified name.
     */
    Person getPerson(String name);
}
