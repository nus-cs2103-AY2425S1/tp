package seedu.address.model.meetup;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a date time attribute
 */
public abstract class DateTime {

    public static final String MESSAGE_CONSTRAINTS_FORMAT =
            "Date-time provided should only be in the format: YYYY-MM-DD HH:mm";

    public static final String MESSAGE_CONSTRAINTS_DATETIME =
            "Date-time provided is invalid";

    /*
     * The time format must be strictly followed
     */
    public static final String VALIDATION_REGEX =
            "^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}$";

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public final LocalDateTime value;

    /**
     * Constructs a {@code MeetUpFromType}.
     */
    public DateTime(String dateTime) {
        requireNonNull(dateTime);
        checkArgument(isValidFormat(dateTime), MESSAGE_CONSTRAINTS_FORMAT);
        checkArgument(isValidDateTime(dateTime), MESSAGE_CONSTRAINTS_DATETIME);
        this.value = LocalDateTime.parse(dateTime, FORMATTER);
    }

    /**
     * Returns true if a given string is a valid format.
     */
    public static boolean isValidFormat(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid dateTime.
     */
    public static boolean isValidDateTime(String test) {
        try {
            LocalDateTime.parse(test, FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }


    /**
     * Returns pretty formatted LocalDateTime String
     */
    public String toPrettyString() {
        // Define the formatter with the desired pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy (h:mma)");

        // Format the LocalDateTime object
        String formattedDateTime = value.format(formatter);

        // Convert "AM"/"PM" to lowercase
        formattedDateTime = formattedDateTime.replace("AM", "am").replace("PM", "pm");
        return formattedDateTime;
    }

    @Override
    public String toString() {
        String formattedDateTime = value.format(FORMATTER);
        return formattedDateTime;
    }


    /**
     * Getter method for LocalDateTime value.
     */
    public LocalDateTime getDateTime() {
        return value;
    }
}
