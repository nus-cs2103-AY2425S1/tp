package seedu.sellsavvy.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.sellsavvy.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.ZoneId;

import seedu.sellsavvy.logic.parser.ParserUtil;
import seedu.sellsavvy.logic.parser.exceptions.ParseException;

/**
 * Represents the deliver-by date of a Person's Order in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateRegex(String)}
 * and {@link #isValidCalendarDate(String)}
 */
public class Date {
    public static final String DATE_FORMATTER = "dd-MM-uuuu";
    public static final String MESSAGE_CONSTRAINTS = "Dates should be of the format dd-mm-yyyy "
            + "and adhere to the following constraints:\n"
            + "1. The day \"dd\" should be a 2-digit number representing a valid day the specified calender month\n"
            + "2. The month \"mm\" should be a 2-digit number representing a valid month in the calender year\n"
            + "3. The year \"yyyy\" should be a 4-digit number representing a valid year within the 21st century\n"
            + "4. The day, month and year are separated by a hyphen '-'."
            + "e.g. 31-03-2024";
    public static final String MESSAGE_INVALID_DATE = "Date provided is not a valid calendar date";
    public static final String MESSAGE_OUTDATED_WARNING = "Note: "
            + "The date provided has already passed, "
            + "verify if this is a mistake\n";
    private static final String REGEX_DAY = "(0[1-9]|[12][0-9]|3[01])";
    private static final String REGEX_MONTH = "(0[1-9]|1[0-2])";
    private static final String REGEX_YEAR = "(20(0[1-9]|[1-9][0-9])|2100)";
    public static final String REGEX_DATE = "^" + REGEX_DAY + "-" + REGEX_MONTH + "-" + REGEX_YEAR + "$";
    public final String value;

    /**
     * Constructs an {@code Date}.
     *
     * @param date A valid date address.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDateRegex(date), MESSAGE_CONSTRAINTS);
        checkArgument(isValidCalendarDate(date), MESSAGE_INVALID_DATE);
        value = date;
    }

    /**
     * Returns if a given string is of a valid date regex.
     */
    public static boolean isValidDateRegex(String test) {
        if (!test.matches(REGEX_DATE)) {
            return false;
        }
        return true;
    }

    /**
     * Returns if a given string is a valid calendar date.
     */
    public static boolean isValidCalendarDate(String test) {
        try {
            LocalDate localDate = ParserUtil.parseLocalDate(test);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns if the {@code Date} has passed.
     */
    public boolean hasDateElapsed() {
        LocalDate localDate;
        try {
            localDate = ParserUtil.parseLocalDate(value);
        } catch (ParseException e) {
            // since hasDateElapsed is only called after validation, ParseException should never be thrown.
            return false;
        }

        LocalDate localDateNow = LocalDate.now(ZoneId.of("Asia/Singapore"));
        return localDate.isBefore(localDateNow);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;
        return value.equals(otherDate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
