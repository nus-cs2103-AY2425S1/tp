package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.LogicManager;
import seedu.address.model.property.Property;
import seedu.address.model.property.UniquePropertyList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameProperty comparison)
 */
public class PropertyList implements ReadOnlyPropertyList {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

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
     * Creates an PropertyList using the properties in the {@code toBeCopied}
     */
    public PropertyList(ReadOnlyPropertyList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the property list with {@code properties}.
     * {@code properties} must not contain duplicate properties.
     */
    public void setProperties(List<Property> properties) {
        this.properties.setProperties(properties);
        logger.info("set properties to " + this.properties);
    }

    /**
     * Resets the existing data of this {@code PropertyList} with {@code newData}.
     */
    public void resetData(ReadOnlyPropertyList newData) {
        requireNonNull(newData);
        setProperties(newData.getPropertyList());
    }

    //// property-level operations

    /**
     * Returns true if a property with the same identity as {@code property} exists in the meet up list.
     */
    public boolean hasProperty(Property property) {
        requireNonNull(property);
        return properties.contains(property);
    }

    /**
     * Adds a property to the meet up list.
     * The property must not already exist in the meet up list.
     */
    public void addProperty(Property m) {
        properties.add(m);
    }

    /**
     * Replaces the given property {@code target} in the list with {@code editedproperty}.
     * {@code target} must exist in the meet up list.
     * The property identity of {@code editedproperty} must not be the same as another
     * existing property in the meet up list.
     */
    public void setProperty(Property target, Property editedProperty) {
        requireNonNull(editedProperty);
        properties.setProperty(target, editedProperty);
    }

    /**
     * Removes {@code key} from this {@code PropertyList}.
     * {@code key} must exist in the meet up list.
     */
    public void removeProperty(Property key) {
        properties.remove(key);
    }

    //// util methods

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
