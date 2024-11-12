package tutorease.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static tutorease.address.commons.util.AppUtil.checkArgument;
import static tutorease.address.commons.util.DateTimeUtil.INVALID_DATETIME_FORMAT_MESSAGE;
import static tutorease.address.commons.util.DateTimeUtil.checkValidDateTime;
import static tutorease.address.commons.util.DateTimeUtil.parseDateTime;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import tutorease.address.commons.core.LogsCenter;
import tutorease.address.commons.util.NumbersUtil;
import tutorease.address.logic.parser.exceptions.ParseException;

/**
 * Represents the end date and time of a lesson.
 */
public class EndDateTime extends DateTime {
    public static final String HOURS_MESSAGE_CONSTRAINTS = "Hours should only use numbers (0-9) and "
            + "a single `.` for decimal points if required. \n"
            + "No alphabets or other special characters should be used. \n"
            + "Hours to add must be in multiples of 0.5. \n"
            + "They also have to be more than 0 and be at most 24.";
    public static final String END_DATE_MESSAGE_CONSTRAINTS = String.format(INVALID_DATETIME_FORMAT_MESSAGE, "End");
    private static Logger logger = LogsCenter.getLogger(EndDateTime.class);

    private EndDateTime(LocalDateTime dateTime) throws ParseException {
        super(dateTime);
        assert dateTime != null : "DateTime cannot be null";
    }

    /**
     * Creates an EndDateTime object from a StartDateTime object and a number of hours to add.
     *
     * @param startDateTime The StartDateTime object.
     * @param hoursToAdd The number of hours to add.
     * @return The EndDateTime object.
     * @throws ParseException If the hours to add is invalid.
     */
    public static EndDateTime createEndDateTime(StartDateTime startDateTime, String hoursToAdd) throws ParseException {
        logger.log(Level.INFO, "Creating EndDateTime object with start date time: " + startDateTime
                + " and hours to add: " + hoursToAdd);

        requireNonNull(hoursToAdd);
        checkArgument(isValidHoursToAdd(hoursToAdd), HOURS_MESSAGE_CONSTRAINTS);

        double parsedHoursToAdd = NumbersUtil.parseDouble(hoursToAdd, HOURS_MESSAGE_CONSTRAINTS);
        LocalDateTime endDateTime = calculateEndDateTime(startDateTime, parsedHoursToAdd);
        logger.log(Level.INFO, "Created EndDateTime: " + endDateTime);
        return new EndDateTime(endDateTime);
    }

    /**
     * Creates an EndDateTime object from a string for loading from model.
     *
     * @param dateTime The string representing the date and time.
     * @return The EndDateTime object.
     * @throws ParseException If the string is invalid.
     */
    public static EndDateTime createEndDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        dateTime = dateTime.trim();
        checkValidDateTime(dateTime);
        return new EndDateTime(parseDateTime(dateTime));
    }

    /**
     * Returns true if a given string is a valid number of hours to add, 0 < hours <= 24.
     *
     * @param hoursToAdd The number of hours to add.
     * @return True if the number of hours to add is valid, false otherwise.
     */
    public static boolean isValidHoursToAdd(String hoursToAdd) {
        try {
            double parsedHoursToAdd = NumbersUtil.parseDouble(hoursToAdd, HOURS_MESSAGE_CONSTRAINTS);
            boolean isWithinRange = parsedHoursToAdd > 0 && parsedHoursToAdd <= 24;
            boolean isMultipleOfPointFive = (parsedHoursToAdd * 2) % 1 == 0;
            return isWithinRange && isMultipleOfPointFive;
        } catch (ParseException e) {
            return false;
        }
    }

    private static LocalDateTime calculateEndDateTime(StartDateTime startDateTime, double hoursToAdd) {
        LocalDateTime endDateTime = startDateTime.getDateTime().plusHours((long) hoursToAdd);
        endDateTime = endDateTime.plusMinutes((long) ((hoursToAdd % 1) * 60));
        return endDateTime;
    }

}
