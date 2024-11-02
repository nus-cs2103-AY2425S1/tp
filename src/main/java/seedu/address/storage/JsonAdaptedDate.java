package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Date;
import seedu.address.model.tag.Tag;


/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedDate {
    private static final String DATE_PATTERN =
            "^([1-9]|[12][0-9]|3[01])/([1-9]|1[0-2])/\\d{4} ([01][0-9]|2[0-3])[0-5][0-9]$";
    private static final String FORMAT_PATTERN = "^\\d{1,2}/\\d{1,2}/\\d{4} \\d{4}$";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
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
        if (dateString == null || dateString.isEmpty() || dateString.toLowerCase().equals("none")) {
            return new Date(LocalDateTime.MIN); // Treat as no appointment
        }
        if (!Date.isValidDate(dateString)) {
            throw new IllegalValueException(Date.getMessageConstraints());
        }
        return new Date(LocalDateTime.parse(dateString, DATE_FORMATTER));
    }

}
