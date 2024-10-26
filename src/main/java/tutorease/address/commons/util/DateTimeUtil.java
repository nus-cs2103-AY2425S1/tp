package tutorease.address.commons.util;

import java.time.LocalDateTime;
import java.time.YearMonth;
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
    public static final String INVALID_DATETIME_FIELD = "%s should be an integer";
    public static final String INVALID_DAY = "Invalid Day: %d. "
            + "dd must be between 1 and %d for your given month and year";
    public static final String INVALID_MONTH = "Invalid Month: %d. MM must be between 1 and 12";
    public static final String INVALID_YEAR = "Invalid Year: %d. yyyy must be between 2000 and 2100";
    public static final String INVALID_HOUR = "Invalid Hour: %d. HH must be between 0 and 23";
    public static final String INVALID_MINUTE = "Invalid Minute: %d. mm must be between 0 and 59";


    /**
     * Checks if a given string is a valid date time.
     *
     * @param dateTime The date time to be checked.
     */
    public static void checkValidDateTime(String dateTime) throws ParseException {
        String trimmedStartDateTime = dateTime.trim();
        String regex = "\\d{2}-\\d{2}-\\d{4}\\s\\d{2}:\\d{2}";
        if (!trimmedStartDateTime.matches(regex)) {
            throw new ParseException(String.format(INVALID_DATETIME_FORMAT, dateTime));
        }

        try {
            validateDateTime(trimmedStartDateTime);
        } catch (ParseException specificFieldException) {
            throw specificFieldException;
        }
    }

    private static void validateDateTime(String trimmedStartDateTime) throws ParseException {
        String[] dateTimeParts = trimmedStartDateTime.split(" ");
        assert dateTimeParts.length == 2;

        String[] dateParts = dateTimeParts[0].split("-");
        String[] timeParts = dateTimeParts[1].split(":");
        assert dateParts.length == 3;
        assert timeParts.length == 2;

        int day = NumbersUtil.parseInt(dateParts[0], String.format(INVALID_DATETIME_FIELD, dateParts[0]));
        int month = NumbersUtil.parseInt(dateParts[1], String.format(INVALID_DATETIME_FIELD, dateParts[1]));
        int year = NumbersUtil.parseInt(dateParts[2], String.format(INVALID_DATETIME_FIELD, dateParts[2]));
        int hour = NumbersUtil.parseInt(timeParts[0], String.format(INVALID_DATETIME_FIELD, timeParts[0]));
        int minute = NumbersUtil.parseInt(timeParts[1], String.format(INVALID_DATETIME_FIELD, timeParts[1]));

        try {
            checkValidYear(year);
            checkValidMonth(month);
            YearMonth yearMonth = YearMonth.of(year, month);
            int maxDay = yearMonth.lengthOfMonth();
            checkValidDay(day, maxDay);
            checkValidHour(hour);
            checkValidMinute(minute);
        } catch (ParseException e) {
            throw e;
        }
    }

    private static void checkValidYear(int year) throws ParseException {
        if (year < 2000 || year > 2100) {
            throw new ParseException(String.format(INVALID_YEAR, year));
        }
    }

    private static void checkValidMonth(int month) throws ParseException {
        if (month < 1 || month > 12) {
            throw new ParseException(String.format(INVALID_MONTH, month));
        }
    }

    private static void checkValidDay(int day, int maxDay) throws ParseException {
        if (day < 1 || day > maxDay) {
            throw new ParseException(String.format(INVALID_DAY, day, maxDay));
        }
    }

    private static void checkValidHour(int hour) throws ParseException {
        if (hour < 0 || hour > 23) {
            throw new ParseException(String.format(INVALID_HOUR, hour));
        }
    }

    private static void checkValidMinute(int minute) throws ParseException {
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
