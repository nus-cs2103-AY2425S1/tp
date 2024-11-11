package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.property.Property;

/**
 * Unmodifiable view of a property book
 */
public interface ReadOnlyPropertyBook {
    /**
     * Returns an unmodifiable view of the property list.
     * This list will not contain any duplicate property.
     */
    ObservableList<Property> getPropertyList();
}
