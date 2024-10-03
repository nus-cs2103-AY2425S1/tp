package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A class for representing dates.
 */
public class DateUtil {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String MESSAGE_CONSTRAINTS = "Date should be in the format of " + DATE_FORMAT;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    /**
     * Constructs a {@code DateUtil} Object.
     */
    public DateUtil(String date) {
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
    }

    /**
     * Returns if a given string is a valid date.
     *
     * @param date The date to be checked.
     */
    public static boolean isValidDate(String date) {
        requireNonNull(date);
        try {
            LocalDate parsedDate = LocalDate.parse(date, DATE_FORMATTER);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    /**
     * Returns if a given string is a date that is after today.
     *
     * @param date The date to be checked.
     * @return true if the date is after today, false otherwise.
     */
    public static boolean isAfterToday(String date) {
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        LocalDate parsedDate = LocalDate.parse(date, DATE_FORMATTER);
        return parsedDate.isAfter(LocalDate.now());
    }
}
