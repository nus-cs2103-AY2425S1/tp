package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.wedding.Wedding;

/**
 * Unmodifiable view of a wedding book
 */
public interface ReadOnlyWeddingBook {

    /**
     * Returns an unmodifiable view of the wedding list.
     * This list will not contain any duplicate weddings.
     */
    ObservableList<Wedding> getWeddingList();

}
