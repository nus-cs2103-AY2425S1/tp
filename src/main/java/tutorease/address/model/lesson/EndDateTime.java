package tutorease.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static tutorease.address.commons.util.AppUtil.checkArgument;
import static tutorease.address.commons.util.DateTimeUtil.INVALID_DATETIME_FORMAT;
import static tutorease.address.commons.util.DateTimeUtil.parseDateTime;
import static tutorease.address.model.lesson.StartDateTime.START_DATE_MESSAGE_CONSTRAINTS;

import java.time.LocalDateTime;

import tutorease.address.commons.util.NumbersUtil;
import tutorease.address.logic.parser.exceptions.ParseException;

/**
 * Represents the end date and time of a lesson.
 */
public class EndDateTime extends DateTime {
    public static final String HOURS_MESSAGE_CONSTRAINTS = "Hours to add must be in multiples of 0.5. "
            + "They also have to be more than 0 and be at most 24";
    public static final String END_DATE_MESSAGE_CONSTRAINTS = String.format(INVALID_DATETIME_FORMAT, "End");

    private EndDateTime(LocalDateTime dateTime) {
        super(dateTime);
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
        requireNonNull(hoursToAdd);
        checkArgument(isValidHoursToAdd(hoursToAdd), HOURS_MESSAGE_CONSTRAINTS);
        double parsedHoursToAdd = NumbersUtil.parseDouble(hoursToAdd, HOURS_MESSAGE_CONSTRAINTS);
        LocalDateTime endDateTime = calculateEndDateTime(startDateTime, parsedHoursToAdd);

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
        dateTime = dateTime.trim();
        requireNonNull(dateTime);
        checkArgument(isValidDateTime(dateTime), START_DATE_MESSAGE_CONSTRAINTS);
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
