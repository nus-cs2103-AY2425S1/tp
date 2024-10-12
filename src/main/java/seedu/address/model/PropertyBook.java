package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.property.Property;
import seedu.address.model.property.UniquePropertiesList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameProperty comparison)
 */
public class PropertyBook implements ReadOnlyPropertyBook {
    private final UniquePropertiesList properties;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */

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
     * Replaces the contents of the property list with {@code properties}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setProperty(List<Property> properties) {
        this.properties.setProperties(properties);
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
    public String toString() {
        return new ToStringBuilder(this)
                .add("properties", properties)
                .toString();
    }

    @Override
    public ObservableList<Property> getPropertyList() {
        return properties.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PropertyBook)) {
            return false;
        }

        PropertyBook otherPropertyBook = (PropertyBook) other;
        return properties.equals(otherPropertyBook.properties);
    }

    @Override
    public int hashCode() {
        return properties.hashCode();
    }
}
