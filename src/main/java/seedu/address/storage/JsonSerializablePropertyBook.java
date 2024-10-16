package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.PropertyBook;
import seedu.address.model.property.Property;

@JsonRootName(value = "propertybook")
class JsonSerializablePropertyBook {

    public static final String MESSAGE_DUPLICATE_PROPERTY = "Property list contains duplicate properties.";

    private final List<JsonAdaptedProperty> properties = new ArrayList<>();

    @JsonCreator
    public JsonSerializablePropertyBook(@JsonProperty("properties") List<JsonAdaptedProperty> properties) {
        this.properties.addAll(properties);
    }

    public JsonSerializablePropertyBook(PropertyBook source) {
        properties.addAll(source.getPropertyList().stream()
                .map(JsonAdaptedProperty::new)
                .collect(Collectors.toList()));
    }

    public PropertyBook toModelType() throws IllegalValueException {
        PropertyBook propertyBook = new PropertyBook();
        for (JsonAdaptedProperty jsonProperty : properties) {
            Property property = jsonProperty.toModelType();
            if (propertyBook.hasProperty(property)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PROPERTY);
            }
            propertyBook.addProperty(property);
        }
        return propertyBook;
    }
}
