package tutorease.address.commons.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import tutorease.address.logic.parser.exceptions.ParseException;
/**
 * Contains utility methods for handling date times.
 */
public class DateTimeUtil {
    private static String dateTimeFormat = "dd-MM-yyyy HH:mm";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
    public static final String INVALID_DATETIME_FORMAT = "%s date time must be in the format of " + dateTimeFormat;

    /**
     * Returns true if a given string is a valid date time.
     *
     * @param dateTime The date time to be checked.
     * @return True if the date time is valid, false otherwise.
     */
    public static boolean isValidDateTime(String dateTime) {
        // Solution below adapted by https://medium.com/@barbieri.santiago/
        // -notes-javas-formatstyle-enum-58a6651015ec
        //@@author {Chandra Prakash}-reused
        //{A website that teaches common java methods}
        try {
            parseDateTime(dateTime);
            return true;
        } catch (ParseException e) {
            return false;
        }
        //@@author
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
