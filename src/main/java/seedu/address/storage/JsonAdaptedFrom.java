package seedu.address.storage;

import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.From;

/**
 * Jackson-friendly version of {@link From}.
 */
public class JsonAdaptedFrom {

    private final String value;

    /**
     * Constructs a {@code JsonAdaptedFrom} with the given date.
     */
    @JsonCreator
    public JsonAdaptedFrom(@JsonProperty("from") String from) {
        this.value = from;
    }

    /**
     * Converts a given {@code From} into this class for Jackson use.
     */
    public JsonAdaptedFrom(From source) {
        this.value = source.toString();
    }


    /**
     * Converts this Jackson-friendly adapted appointment object into the model's {@code From} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted from.
     */

    public From toModelType() throws IllegalValueException {
        if (value == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, From.class.getSimpleName()));
        }
        return new From(value);
    }
}
