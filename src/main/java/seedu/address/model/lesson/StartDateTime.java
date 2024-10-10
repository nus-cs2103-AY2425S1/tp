package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.DateTimeUtil.INVALID_DATETIME_FORMAT;
import static seedu.address.commons.util.DateTimeUtil.isValidDateTime;
import static seedu.address.commons.util.DateTimeUtil.parseDateTime;

import java.time.LocalDateTime;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents the start date and time of a lesson.
 */
public class StartDateTime extends DateTime {
    public static final String START_DATE_MESSAGE_CONSTRAINTS = String.format(INVALID_DATETIME_FORMAT, "Start");

    private StartDateTime(LocalDateTime dateTime) {
        super(dateTime);
    }

    /**
     * Creates a StartDateTime object from a given string.
     * @param dateTime The string representing the start date and time.
     * @return A StartDateTime object.
     * @throws ParseException If the string is not a valid start date and time.
     */
    public static StartDateTime createStartDateTime(String dateTime) throws ParseException {
        dateTime = dateTime.trim();
        requireNonNull(dateTime);
        checkArgument(isValidStartDateTime(dateTime), START_DATE_MESSAGE_CONSTRAINTS);
        return new StartDateTime(parseDateTime(dateTime));
    }

    /**
     * Returns true if a given string is a valid start date and time.
     * @param dateTime The start date and time to be checked.
     * @return True if the start date and time is valid, false otherwise.
     */
    public static boolean isValidStartDateTime(String dateTime) {
        return isValidDateTime(dateTime);
    }
}
