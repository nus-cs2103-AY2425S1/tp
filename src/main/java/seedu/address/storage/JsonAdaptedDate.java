package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.ParseException;
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
        if (dateString == null || dateString.isEmpty()) {
            return new Date(LocalDateTime.MIN); // Treat as no appointment
        }

        String[] dateParts = getDateParts();

        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);

        // Check month-day combinations, including leap year validation
        if (month == 2) {
            if (day > 29 || (day == 29 && year % 4 != 0)) {
                throw new ParseException("Invalid date: February " + day + " is only valid in leap years.");
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day > 30) {
                throw new ParseException("Invalid date: " + Month.of(month) + " cannot have more than 30 days.");
            }
        }


        return new Date(LocalDateTime.parse(dateString, DATE_FORMATTER));
    }

    /**
     * Parses the date string into individual date components (day, month, year) if the format and values are valid.
     *
     * <p>If the provided {@code dateString} does not match the expected format or contains out-of-range values,
     * a {@code ParseException} is thrown to indicate the specific issue.</p>
     *
     * <ul>
     *     <li>Expected format: {@code "d/M/yyyy HHmm"} (e.g., "2/12/2024 1800").</li>
     *     <li>The method first checks for a valid format using {@code FORMAT_PATTERN}.</li>
     *     <li>Then, it validates date and time values using {@code DATE_PATTERN}.</li>
     * </ul>
     *
     * @return An array of strings representing the day, month, and year, extracted from the {@code dateString}.
     * @throws ParseException if the date format is incorrect or if date/time values are invalid.
     *      <ul>
     *          <li>If the format is incorrect, an exception message indicates the expected format.</li>
     *          <li>If the date or time values are out of range, an exception message highlights this.</li>
     *      </ul>
     */
    private String[] getDateParts() throws ParseException {
        if (!dateString.matches(DATE_PATTERN)) {
            if (!dateString.matches(FORMAT_PATTERN)) {
                throw new ParseException("Invalid date format! Please use 'd/M/yyyy HHmm'. "
                        + "For example, '2/12/2024 1800'.");
            } else {
                throw new ParseException("Invalid date or time values! "
                        + "Ensure day, month, hour, and minute ranges are correct.");
            }
        }

        String[] dateAndTime = dateString.split(" ");
        String[] dateParts = dateAndTime[0].split("/");
        return dateParts;
    }

}
