package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents the deliver-by date of a Person's Order in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {
    public static final String DATE_FORMATTER = "dd-MM-yyyy";
    public static final String MESSAGE_CONSTRAINTS = "Dates should be of the format dd-mm-yyyy "
            + "and adhere to the following constraints:\n"
            + "1. The day \"dd\" should be a 2-digit number representing a valid day the specified calender month\n"
            + "2. The month \"mm\" should be a 2-digit number representing a valid month in the calender year\n"
            + "3. The year \"yyyy\" should be a 4-digit number representing a valid year within the 21st century\n"
            + "4. The day, month and year are separated by a hyphen '-'.";
    private static final String REGEX_DAY = "(0[1-9]|[12][0-9]|3[01])";
    private static final String REGEX_MONTH = "(0[1-9]|1[0-2])";
    private static final String REGEX_YEAR = "(20[0-9]{2})";
    public static final String REGEX_DATE = "^" + REGEX_DAY + "-" + REGEX_MONTH + "-" + REGEX_YEAR + "$";
    public final String value;

    /**
     * Constructs an {@code Date}.
     *
     * @param date A valid date address.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        value = date;
    }

    /**
     * Returns if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        if (!test.matches(REGEX_DATE)) {
            return false;
        }
        try {
            ParserUtil.parseLocalDate(test);
        } catch (ParseException e) {
            return false;
        }
        return true;
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
