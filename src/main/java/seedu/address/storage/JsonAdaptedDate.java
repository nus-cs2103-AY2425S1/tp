package seedu.address.storage;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Date;
import seedu.address.model.tag.Tag;


/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedDate {
    private final String dateString;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedDate(String dateString) {
        this.dateString = dateString;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedDate(Date source) {
        dateString = source.toString();
    }

    @JsonValue
    public String getDateName() {
        return dateString;
    }

    /**
     * Converts this Jackson-friendly adapted date object into a {@code LocalDateTime} object.
     *
     * @throws IllegalValueException if the date format is invalid.
     */
    public Date toModelType() throws IllegalValueException {
        if (dateString == null || dateString.isEmpty()) {
            return new Date(LocalDateTime.MIN); // Treat as no appointment
        }
        return new Date(Date.parseDateString(dateString));
    }


}
