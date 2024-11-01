package seedu.academyassist.model;

import javafx.collections.ObservableList;
import seedu.academyassist.model.person.Person;

/**
 * Unmodifiable view of academy assist
 */
public interface ReadOnlyAcademyAssist {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns the {@code idGeneratedCount} in the contact book.
     */
    int getIdGeneratedCount();

}
