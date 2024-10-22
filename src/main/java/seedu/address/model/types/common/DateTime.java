package seedu.address.model.types.common;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an Event's datetime in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
public class DateTime {

    public static final String MESSAGE_CONSTRAINTS = "DateTime should be in the format YYYY-MM-DD HH:MM "
        + "(24-hour format) and must be valid.";
    /*
     * The date must follow the format YYYY-MM-DD (ISO standard date format), and the time must follow
     * the 24-hour format (HH:mm), where hours are between 00 and 23 and minutes are between 00 and 59.
     */
    public static final String VALIDATION_REGEX = "\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])"
        + " ([01][0-9]|2[0-3]):[0-5][0-9]";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public final String value;
    public final LocalDateTime localDateTimeValue;

    /**
     * Constructs a {@code DateTime}.
     *
     * @param dateTime A valid date and time.
     */
    public DateTime(String dateTime) {
        requireNonNull(dateTime);
        checkArgument(isValidDateTime(dateTime), MESSAGE_CONSTRAINTS);
        value = dateTime;
        localDateTimeValue = LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
    }

    /**
     * Returns true if a given string is a valid date and time.
     */
    public static boolean isValidDateTime(String test) {
        boolean internalValidation;
        boolean externalValidation;

        internalValidation = test.matches(VALIDATION_REGEX);

        try {
            LocalDateTime parsedValue;
            parsedValue = LocalDateTime.parse(test, DATE_TIME_FORMATTER);
            externalValidation = true;
        } catch (DateTimeParseException e) {
            externalValidation = false;
        }

        return internalValidation && externalValidation;
    }

    /**
     * Returns a Duration object (can be negative) representing the difference from another LocalDateTime
     */
    public Duration compareTo(LocalDateTime otherLocalDateTime) {
        return Duration.between(localDateTimeValue, otherLocalDateTime);
    }

    public LocalDateTime toLocalDateTime() {
        return localDateTimeValue;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DateTime)) {
            return false;
        }

        DateTime otherDateTime = (DateTime) other;
        return value.equals(otherDateTime.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
