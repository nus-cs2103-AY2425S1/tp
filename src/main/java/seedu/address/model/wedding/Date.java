package seedu.address.model.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents the Date of a Wedding.
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS =
            "Date should be in the following format, "
                    + "YYYY-MM-DD.";

    // Use built-in formatter to parse and validate the date
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private LocalDate fullDate;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date in the format of "yyyy-MM-dd".
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.fullDate = LocalDate.parse(date, FORMATTER);
    }

    /**
     * Returns true if a given string is a valid date according to the format "yyyy-MM-dd".
     *
     * @param test string to be tested
     * @return whether the string is a valid date.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test, FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return fullDate.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;
        return fullDate.equals(otherDate.fullDate);
    }

    @Override
    public int hashCode() {
        return fullDate.hashCode();
    }
}
