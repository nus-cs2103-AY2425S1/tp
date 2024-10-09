package seedu.address.commons.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import seedu.address.logic.parser.exceptions.ParseException;

public class DateTimeUtil {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    public static final String INVALID_DATETIME_FORMAT = "%s date time must be in the format of " + formatter;

    public static boolean isValidDateTime(String dateTime) {
        // Solution below adapted by https://medium.com/@barbieri.santiago/student-notes-javas-formatstyle-enum-58a6651015ec


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

    public static LocalDateTime parseDateTime(String dateTime) throws ParseException {
        try {
            return LocalDateTime.parse(dateTime, formatter);
        } catch (Exception e) {
            throw new ParseException(INVALID_DATETIME_FORMAT);
        }
    }

    public static String dateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }

    public static String dateTimeNowString() {
        return LocalDateTime.now().format(formatter);
    }
}
