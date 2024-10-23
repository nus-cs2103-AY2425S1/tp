package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.property.Property;

/**
 * Unmodifiable view of a buyer list
 */
public interface ReadOnlyPropertyList {
    /**
     * Returns an unmodifiable view of the buyers list.
     * This list will not contain any duplicate buyers.
     */
    ObservableList<Property> getPropertyList();
}
