package seedu.address.storage.property;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.PropertyList;
import seedu.address.model.ReadOnlyPropertyList;
import seedu.address.model.property.Property;




/**
 * An Immutable PropertyList that is serializable to JSON format.
 */
@JsonRootName(value = "propertylist")
public class JsonSerializablePropertyList {

    public static final String MESSAGE_DUPLICATE_PROPERTY = "Property list contains duplicate property(s).";

    private final List<JsonAdaptedProperty> properties = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablePropertyList} with the given buyers.
     */
    @JsonCreator
    public JsonSerializablePropertyList(@JsonProperty("properties") List<JsonAdaptedProperty> properties) {
        this.properties.addAll(properties);
    }

    /**
     * Converts a given {@code ReadOnlyPropertyList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablePropertyList}.
     */
    public JsonSerializablePropertyList(ReadOnlyPropertyList source) {
        properties.addAll(source.getPropertyList().stream().map(JsonAdaptedProperty::new)
             .collect(Collectors.toList()));
    }

    /**
     * Converts this buyer list into the model's {@code PropertyList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public PropertyList toModelType() throws IllegalValueException {
        PropertyList propertyList = new PropertyList();
        for (JsonAdaptedProperty jsonAdaptedProperty : properties) {
            Property property = jsonAdaptedProperty.toModelType();
            if (propertyList.hasProperty(property)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PROPERTY);
            }
            propertyList.addProperty(property);
        }
        return propertyList;
    }

}
