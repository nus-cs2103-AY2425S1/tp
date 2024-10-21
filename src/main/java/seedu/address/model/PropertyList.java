package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.property.Property;
import seedu.address.model.property.UniquePropertyList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameBuyer comparison)
 */
public class PropertyList implements ReadOnlyPropertyList {

    private final UniquePropertyList properties;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        properties = new UniquePropertyList();
    }

    public PropertyList() {}

    /**
     * Creates an BuyerList using the Buyers in the {@code toBeCopied}
     */
    public PropertyList(ReadOnlyPropertyList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the buyer list with {@code buyers}.
     * {@code buyers} must not contain duplicate buyers.
     */
    public void setProperties(List<Property> properties) {
        this.properties.setProperties(properties);
    }

    /**
     * Resets the existing data of this {@code PropertyList} with {@code newData}.
     */
    public void resetData(ReadOnlyPropertyList newData) {
        requireNonNull(newData);

        setProperties(newData.getPropertyList());
    }

    //// buyer-level operations

    /**
     * Returns true if a property with the same identity as {@code property} exists in the property list.
     */
    public boolean hasProperty(Property property) {
        requireNonNull(property);
        return properties.contains(property);
    }

    /**
     * Adds a property to the property list.
     * The property must not already exist in the property list.
     */
    public void addProperty(Property p) {
        properties.add(p);
    }

    /**
     * Replaces the given property {@code target} in the list with {@code editedProperty}.
     * {@code target} must exist in the property list.
     * The property identity of {@code editedProperty} must not be the same as another existing property
     * in the property list.
     */
    public void setProperty(Property target, Property editedProperty) {
        requireNonNull(editedProperty);

        properties.setProperties(target, editedProperty);
    }

    /**
     * Removes {@code key} from this {@code PropertyList}.
     * {@code key} must exist in the property list.
     */
    public void removeProperty(Property key) {
        properties.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("property", properties)
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
        if (!(other instanceof PropertyList)) {
            return false;
        }

        PropertyList otherPropertyList = (PropertyList) other;
        return properties.equals(otherPropertyList.properties);
    }

    @Override
    public int hashCode() {
        return properties.hashCode();
    }
}
