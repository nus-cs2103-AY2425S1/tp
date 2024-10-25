package tutorease.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static tutorease.address.commons.util.AppUtil.checkArgument;
import static tutorease.address.commons.util.DateTimeUtil.INVALID_DATETIME_FORMAT;
import static tutorease.address.commons.util.DateTimeUtil.checkValidDateTime;
import static tutorease.address.commons.util.DateTimeUtil.parseDateTime;

import java.time.LocalDateTime;

import tutorease.address.logic.parser.exceptions.ParseException;

/**
 * Represents the start date and time of a lesson.
 */
public class StartDateTime extends DateTime {
    public static final String START_DATE_MESSAGE_CONSTRAINTS = String.format(INVALID_DATETIME_FORMAT, "Start");
    public static final String START_IS_AFTER_END = "Start date and time must be before end date and time.";
    private StartDateTime(LocalDateTime dateTime) throws ParseException {
        super(dateTime);
    }

    /**
     * Creates a StartDateTime object from a given string.
     *
     * @param dateTime The string representing the start date and time.
     * @return A StartDateTime object.
     * @throws ParseException If the string is not a valid start date and time.
     */
    public static StartDateTime createStartDateTime(String dateTime) throws ParseException {
        dateTime = dateTime.trim();
        requireNonNull(dateTime);
        checkValidDateTime(dateTime);
        return new StartDateTime(parseDateTime(dateTime));
    }
}
