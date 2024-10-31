package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.OperatingHours;

/**
 * Jackson-friendly version of {@link OperatingHours}.
 */
class JsonAdaptedOperatingHours {

    private final String operatingHours;

    /**
     * Constructs a {@code JsonAdaptedOperatingHours} with the given operatingHours.
     */
    public JsonAdaptedOperatingHours(OperatingHours source) {
        operatingHours = source.toString();
    }

    /**
     * Constructs a {@code JsonAdaptedOperatingHours} with the given operatingHours in String format.
     */
    @JsonCreator
    public JsonAdaptedOperatingHours(String source) {
        operatingHours = source;
    }

    @JsonValue
    public String getOperatingHours() {
        return operatingHours;
    }

    /**
     * Converts this Jackson-friendly adapted operatingHours object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted operatingHours.
     */
    public OperatingHours toModelType() throws IllegalValueException {
        if (!OperatingHours.isValidOperatingHours(operatingHours)) {
            throw new IllegalValueException(OperatingHours.MESSAGE_CONSTRAINTS);
        }
        return new OperatingHours(operatingHours);
    }

}
