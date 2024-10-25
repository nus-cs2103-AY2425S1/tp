package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.model.person.Property;
import seedu.address.model.person.PropertyList;

import java.util.ArrayList;
import java.util.List;

/**
 * Jackson-friendly version of a PropertyList.
 * This class is used to store a list of propertyList in a format
 * that can be serialized and deserialized to and from JSON.
 */
public class JsonAdaptedPropertyList {

    // A list of propertyList stored in the PropertyList
    private final List<JsonAdaptedProperty> properties; // Renamed for clarity

    /**
     * Constructs a {@code JsonAdaptedPropertyList} with the given details.
     *
     * @param properties A list of properties represented as JsonAdaptedProperty.
     */
    @JsonCreator
    public JsonAdaptedPropertyList(@JsonProperty("properties") List<JsonAdaptedProperty> properties) {
        this.properties = properties != null ? new ArrayList<>(properties) : new ArrayList<>();
    }

    /**
     * Converts a {@code PropertyList} into a {@code JsonAdaptedPropertyList}.
     *
     * @param propertyList The PropertyList to adapt.
     */
    public JsonAdaptedPropertyList(PropertyList propertyList) {
        this.properties = new ArrayList<>();
        for (Property property : propertyList.getProperties()) {
            this.properties.add(new JsonAdaptedProperty(property));
        }
    }

    /**
     * Converts this {@code JsonAdaptedPropertyList} back into a {@code PropertyList}.
     *
     * @return The adapted PropertyList.
     */
    public PropertyList toModelType() {
        PropertyList propertyList = new PropertyList();
        for (JsonAdaptedProperty jsonAdaptedProperty : properties) {
            propertyList.addProperty(jsonAdaptedProperty.toModelType());
        }
        return propertyList;
    }

    /**
     * Returns the list of properties as JsonAdaptedProperty.
     *
     * @return A list of properties in JsonAdapted format.
     */
    public List<JsonAdaptedProperty> getProperties() {
        return new ArrayList<>(properties);
    }
}
