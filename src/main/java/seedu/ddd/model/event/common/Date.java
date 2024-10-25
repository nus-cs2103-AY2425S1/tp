package seedu.ddd.model.event.common;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.ddd.commons.util.AppUtil;

/**
 * Represents a Client's Date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {
    public static final String MESSAGE_CONSTRAINTS =
            "Dates should be in the format of either of the following:\n"
                    + "1. yyyy-MM-dd\n"
                    + "2. MM/dd/yyyy\n"
                    + "3. d MMM yyyy";
    public static final DateTimeFormatter VALID_DATE_FORMAT1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter VALID_DATE_FORMAT2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    public static final DateTimeFormatter VALID_DATE_FORMAT3 = DateTimeFormatter.ofPattern("d MMM yyyy");
    private static final DateTimeFormatter[] formatters = new DateTimeFormatter[] {
        VALID_DATE_FORMAT1,
        VALID_DATE_FORMAT2,
        VALID_DATE_FORMAT3
    };

    public final LocalDate date;
    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        AppUtil.checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.date = parseDate(date);
    }

    /**
     * Returns true if a given string is a valid service.
     */
    // TODO: fix date occurrences in storage data before return false for errors.
    public static boolean isValidDate(String test) {
        for (DateTimeFormatter formatter : formatters) {
            try {
                LocalDate.parse(test, formatter);
                return true;
            } catch (DateTimeParseException e) {
                //Ignore and try the next one
            }
        }
        return false;
    }

    /**
     * Parses the date string using available formatters.
     *
     * @param date The date string to parse.
     * @return The parsed LocalDate.
     */
    public static LocalDate parseDate(String date) {
        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(date, formatter);
            } catch (DateTimeParseException e) {
                // Ignore and try next formatter
            }
        }
        throw new DateTimeParseException("Date could not be parsed", date, 0);
    }


    @Override
    public String toString() {
        return date.toString();
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
        return date.equals(otherDate.date);
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
