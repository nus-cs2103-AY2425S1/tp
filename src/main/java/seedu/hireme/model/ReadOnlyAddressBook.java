package seedu.hireme.model;

import javafx.collections.ObservableList;
import seedu.hireme.model.internshipapplication.InternshipApplication;

/**
 * Unmodifiable view of a book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the list.
     * This list will not contain any duplicate items.
     */
    ObservableList<InternshipApplication> getList();

}
