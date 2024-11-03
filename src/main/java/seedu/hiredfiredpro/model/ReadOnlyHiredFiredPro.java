package seedu.hiredfiredpro.model;

import javafx.collections.ObservableList;
import seedu.hiredfiredpro.model.person.Person;

/**
 * Unmodifiable view of an hiredfiredpro
 */
public interface ReadOnlyHiredFiredPro {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

}
