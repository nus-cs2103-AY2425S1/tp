package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code PropertyList} class stores a list of properties for sale.
 * It provides functionality to add properties, retrieve a list of properties,
 * and perform other operations on the list.
 */
public class PropertyList {
    // An ArrayList that holds Property objects
    private final ArrayList<Property> properties;

    /**
     * Constructs an empty {@code PropertyList} object.
     */
    public PropertyList() {
        this.properties = new ArrayList<>();
    }

    /**
     * Adds a new property to the list.
     *
     * @param property The {@code Property} object to be added.
     * @throws IllegalArgumentException if the property is null.
     */
    public void addProperty(Property property) {
        requireNonNull(property, "Property cannot be null.");
        this.properties.add(property);
    }

    /**
     * Constructs a {@code PropertyList} with the specified properties.
     *
     * @param properties The initial list of properties.
     */
    public PropertyList(List<Property> properties) {
        requireNonNull(properties);
        this.properties = new ArrayList<>(properties);
    }

    /**
     * Adds a property to the original {@code PropertyList} in an immutable manner.
     *
     * @param originalPropertyList The original {@code PropertyList} to copy from.
     * @param property The property to add.
     * @return A new {@code PropertyList} with the added property.
     * @throws IllegalArgumentException if the property is null.
     */
    public static PropertyList addProperty(PropertyList originalPropertyList, Property property) {
        requireNonNull(property, "Property cannot be null.");

        // Create a new list including the existing properties and the new property
        List<Property> newProperties = new ArrayList<>(originalPropertyList.properties);
        newProperties.add(property);

        // Return a new PropertyList object with the updated list
        return new PropertyList(newProperties);
    }

    /**
     * Retrieves the list of properties.
     *
     * @return A list of {@code Property} objects.
     */
    public List<Property> getProperties() {
        return new ArrayList<>(properties); // Return a copy of the properties list to avoid modification
    }

    /**
     * Retrieves a property by its index.
     *
     * @param index The index of the property to retrieve.
     * @return The {@code Property} object at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public Property getProperty(int index) {
        return properties.get(index);
    }

    /**
     * Removes a property from the list by its index.
     *
     * @param index The index of the property to remove.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public void removeProperty(int index) {
        properties.remove(index);
    }

    /**
     * Returns a string representation of the property list.
     *
     * @return A string representation of the property list.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Property List:\n");

        for (Property property : properties) {
            sb.append(property).append("\n");
        }

        return sb.toString();
    }

    /**
     * Checks if the PropertyList is equal to another object.
     *
     * @param other The object to compare with.
     * @return true if the objects are the same or have the same properties; false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        // Check if this is the same object reference
        if (this == other) {
            return true;
        }

        // Check if the other object is an instance of PropertyList
        if (!(other instanceof PropertyList)) {
            return false;
        }

        // Typecast other object to PropertyList and compare the properties
        PropertyList otherPropertyList = (PropertyList) other;
        return this.properties.equals(otherPropertyList.properties);
    }
}
