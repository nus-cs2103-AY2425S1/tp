package seedu.address.model;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a book
 */
public interface ReadOnlyAddressBook<T> {

    /**
     * Returns an unmodifiable view of the list.
     * This list will not contain any duplicate items.
     */
    ObservableList<T> getList();

}
