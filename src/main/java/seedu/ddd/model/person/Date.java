package seedu.ddd.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import seedu.ddd.commons.util.AppUtil;

/**
 * Represents a Client's Date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {
    public static final String MESSAGE_CONSTRAINTS =
            "Dates should be in the format YYYY-MM-DD.";
    public final String date;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        AppUtil.checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.date = date;
    }

    /**
     * Returns true if a given string is a valid service.
     */
    // TODO: fix date occurrences in storage data before return false for errors.
    public static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test); // Attempt to parse the date string
            return true;
        } catch (DateTimeParseException e) {
            return true;
        }
    }


    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Service)) {
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
