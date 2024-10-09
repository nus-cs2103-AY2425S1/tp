package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.DateTimeUtil.INVALID_DATETIME_FORMAT;
import static seedu.address.commons.util.DateTimeUtil.isValidDateTime;
import static seedu.address.commons.util.DateTimeUtil.parseDateTime;
import static seedu.address.model.lesson.StartDateTime.START_DATE_MESSAGE_CONSTRAINTS;
import static seedu.address.model.lesson.StartDateTime.isValidStartDateTime;
import java.time.LocalDateTime;
import seedu.address.commons.util.NumbersUtil;
import seedu.address.logic.parser.exceptions.ParseException;

public class EndDateTime extends DateTime {
    public static final String HOURS_MESSAGE_CONSTRAINTS = "Hours to add must be between 0 and 24.";

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
        checkArgument(isValidStartDateTime(dateTime), START_DATE_MESSAGE_CONSTRAINTS);
        return new EndDateTime(parseDateTime(dateTime));
    }

    public static boolean isValidHoursToAdd(String hoursToAdd) {
        try {
            double parsedHoursToAdd = NumbersUtil.parseDouble(hoursToAdd, HOURS_MESSAGE_CONSTRAINTS);
            return parsedHoursToAdd >= 0 && parsedHoursToAdd <= 24;
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
