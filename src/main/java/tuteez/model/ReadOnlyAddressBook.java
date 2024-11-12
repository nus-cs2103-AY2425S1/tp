package tuteez.model;

import javafx.collections.ObservableList;
import tuteez.model.person.Person;
import tuteez.model.person.lesson.LessonManager;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();
    LessonManager getLessonManager();

}
