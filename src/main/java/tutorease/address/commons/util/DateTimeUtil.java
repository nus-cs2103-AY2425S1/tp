package tutorease.address.commons.util;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;

import tutorease.address.logic.parser.exceptions.ParseException;
/**
 * Contains utility methods for handling date times.
 */
public class DateTimeUtil {
    private static String dateTimeFormat = "dd-MM-yyyy HH:mm";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
    public static final String INVALID_DATETIME_FORMAT = "%s date time must be in the format of "
            + dateTimeFormat;
    public static final String INVALID_DAY = "Invalid Day: %d. "
            + "dd must be between 1 and %d for your given month";
    public static final String INVALID_MONTH = "Invalid Month: %d. MM must be between 1 and 12";
    public static final String INVALID_HOUR = "Invalid Hour: %d. HH must be between 0 and 23";
    public static final String INVALID_MINUTE = "Invalid Minute: %d. mm must be between 0 and 59";


    /**
     * Returns true if a given string is a valid date time.
     *
     * @param dateTime The date time to be checked.
     * @return True if the date time is valid, false otherwise.
     */
    public static void checkValidDateTime(String dateTime) throws ParseException {
        String trimmedStartDateTime = dateTime.trim();
        if (!trimmedStartDateTime.matches("\\d{2}/\\d{2}/\\d{4}\\s\\d{4}")) {
            throw new ParseException(String.format(INVALID_DATETIME_FORMAT, dateTime));
        }

        // Parse the string to LocalDateTime to check for validity
        LocalDateTime parsedDateTime = LocalDateTime.parse(trimmedStartDateTime, formatter);

        // Validate individual components
        validateDateTime(parsedDateTime);
    }

    private static void validateDateTime(LocalDateTime dateTime) throws ParseException {
        int year = dateTime.getYear();
        int month = dateTime.getMonthValue();
        int day = dateTime.getDayOfMonth();
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        if (month < 1 || month > 12) {
            throw new ParseException(String.format(INVALID_MONTH, month));
        }
        int maxDayOfMonth = dateTime.getMonth().length(Year.isLeap(year));
        if (day < 1 || day > maxDayOfMonth) {
            throw new ParseException(String.format(INVALID_DAY, day, maxDayOfMonth));
        }

        if (hour < 0 || hour > 23) {
            throw new ParseException(String.format(INVALID_HOUR, hour));
        }

        if (minute < 0 || minute > 59) {
            throw new ParseException(String.format(INVALID_MINUTE, minute));
        }
    }


    /**
     * Parses a string into a LocalDateTime object.
     *
     * @param dateTime The string representing the date and time.
     * @return The LocalDateTime object.
     * @throws ParseException If the string is invalid.
     */
    public static LocalDateTime parseDateTime(String dateTime) throws ParseException {
        try {
            return LocalDateTime.parse(dateTime, formatter);
        } catch (Exception e) {
            throw new ParseException(INVALID_DATETIME_FORMAT);
        }
    }

    /**
     * Converts a LocalDateTime object into a string.
     *
     * @param dateTime The LocalDateTime object.
     * @return The string representing the date and time.
     */
    public static String dateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }

    /**
     * Returns the current date and time as a string.
     *
     * @return The current date and time as a string.
     */
    public static String dateTimeNowString() {
        return LocalDateTime.now().format(formatter);
    }

    /**
     * Returns the date time format.
     *
     * @return The date time format.
     */
    public static String getDateTimeFormat() {
        return dateTimeFormat;
    }
}
