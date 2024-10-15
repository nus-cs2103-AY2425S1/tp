package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.internshipapplication.UniqueList;

/**
 * Unmodifiable view of a book
 */
public interface ReadOnlyAddressBook<T> {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<T> getList();

}
