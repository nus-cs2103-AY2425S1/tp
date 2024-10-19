package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Property;
import seedu.address.model.property.Type;
import seedu.address.model.property.Unit;

/**
 * Jackson-friendly version of {@link Property}.
 */
public class JsonAdaptedProperty {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Property's %s field is missing!";

    private final String postalCode;
    private final String unit;
    private final String type;

    /**
     * Constructs a {@code JsonAdaptedProperty} with the given property details.
     */
    @JsonCreator
    public JsonAdaptedProperty(@JsonProperty("postalCode") String postalCode, @JsonProperty("unit") String unit,
                               @JsonProperty("type") String type) {
        this.postalCode = postalCode;
        this.unit = unit;
        this.type = type;
    }

    /**
     * Converts a given {@code Property} into this class for Jackson use.
     */
    public JsonAdaptedProperty(Property source) {
        postalCode = source.getPostalCode().value;
        unit = source.getUnit().value;
        type = source.getType().value;
    }

    /**
     * Converts this Jackson-friendly adapted property object into the model's {@code Property} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted property.
     */
    public Property toModelType() throws IllegalValueException {
        if (postalCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PostalCode.class.getSimpleName()));
        }
        if (!PostalCode.isValidPostalCode(postalCode)) {
            throw new IllegalValueException(PostalCode.MESSAGE_CONSTRAINTS);
        }
        final PostalCode modelPostalCode = new PostalCode(postalCode);

        if (unit == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Unit.class.getSimpleName()));
        }
        if (!Unit.isValidUnit(unit)) {
            throw new IllegalValueException(Unit.MESSAGE_CONSTRAINTS);
        }
        final Unit modelUnit = new Unit(unit);

        if (type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Type.class.getSimpleName()));
        }
        if (!Type.isValidType(type)) {
            throw new IllegalValueException(Type.MESSAGE_CONSTRAINTS);
        }
        final Type modelType = new Type(type);
        return new Property(modelPostalCode, modelUnit, modelType);
    }
}
