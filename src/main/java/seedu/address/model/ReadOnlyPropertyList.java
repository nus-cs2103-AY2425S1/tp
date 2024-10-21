package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.property.Property;

/**
 * Unmodifiable view of a property list
 */
public interface ReadOnlyPropertyList {

    /**
     * Returns an unmodifiable view of the properties list.
     * This list will not contain any duplicate properties.
     */
    ObservableList<Property> getPropertyList();
}
