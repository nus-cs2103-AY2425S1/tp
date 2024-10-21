package seedu.address.storage;

import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.To;

/**
 * Jackson-friendly version of {@link To}.
 */
public class JsonAdaptedTo {

    private final String value;

    /**
     * Constructs a {@code JsonAdaptedTo} with the given date.
     */
    @JsonCreator
    public JsonAdaptedTo(@JsonProperty("date") String to) {
        this.value = to;
    }

    /**
     * Converts a given {@code From} into this class for Jackson use.
     */
    public JsonAdaptedTo(To source) {
        this.value = source.toString();
    }


    /**
     * Converts this Jackson-friendly adapted appointment object into the model's {@code To} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted to.
     */

    public To toModelType() throws IllegalValueException {
        if (value == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, To.class.getSimpleName()));
        }
        return new To(value);
    }
}
