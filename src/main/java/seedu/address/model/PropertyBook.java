package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.property.Property;
import seedu.address.model.property.UniquePropertiesList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameProperty comparison)
 */
public class PropertyBook implements ReadOnlyPropertyBook {
    private final UniquePropertiesList properties;

    {
        properties = new UniquePropertiesList();
    }

    public PropertyBook() {}

    /**
     * Creates a PropertyBook using the Property in the {@code toBeCopied}
     */
    public PropertyBook(ReadOnlyPropertyBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code PropertyBook} with {@code newData}.
     */
    public void resetData(ReadOnlyPropertyBook newData) {
        requireNonNull(newData);
        setProperty(newData.getPropertyList());
    }

    /**
     * Replaces the given property {@code target} in the list with {@code editedProperty}.
     * {@code target} must exist in the address book.
     * The property identity of {@code editedProperty} must not be the same as another existing property
     * in the address book.
     */
    public void setProperty(List<Property> persons) {
        this.properties.setProperties(persons);
    }


    /**
     * Adds a property to the address book.
     * The property must not already exist in the address book.
     */
    public void addProperty(Property p) {
        properties.add(p);
    }


    /**
     * Returns true if a property with the same identity as {@code property} exists in the address book.
     */
    public boolean hasProperty(Property property) {
        requireNonNull(property);
        return properties.contains(property);
    }

    @Override
    public ObservableList<Property> getPropertyList() {
        return properties.asUnmodifiableObservableList();
    }
}
