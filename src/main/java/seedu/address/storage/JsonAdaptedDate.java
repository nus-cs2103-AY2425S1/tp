package seedu.address.storage;

import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Date;

/**
 * Jackson-friendly version of {@link Date}.
 */
public class JsonAdaptedDate {

    private final String value;

    /**
     * Constructs a {@code JsonAdaptedDate} with the given date.
     */
    @JsonCreator
    public JsonAdaptedDate(@JsonProperty("date") String date) {
        this.value = date;
    }

    /**
     * Converts a given {@code Date} into this class for Jackson use.
     */
    public JsonAdaptedDate(Date source) {
        this.value = source.toString();
    }


    /**
     * Converts this Jackson-friendly adapted appointment object into the model's {@code Date} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted date.
     */

    public Date toModelType() throws IllegalValueException {
        if (value == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        return new Date(value);
    }
}
