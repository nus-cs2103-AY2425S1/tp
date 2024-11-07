package hallpointer.address.model.session;

import static hallpointer.address.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a date in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class SessionDate {

    public static final String MESSAGE_CONSTRAINTS =
            "Dates should be in the format dd MMM yyyy.\n"
                    + "Example: 24 Sep 2024";

    // Desired date format
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");

    public final LocalDate fullDate;

    /**
     * Constructs a {@code SessionDate}.
     *
     * @param date A valid date string.
     */
    public SessionDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        fullDate = LocalDate.parse(date, DATE_FORMATTER);
    }

    /**
     * Returns true if a given string is a valid date and has the expected format.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate parsedDate = LocalDate.parse(test, DATE_FORMATTER);
            return parsedDate.format(DATE_FORMATTER).equals(test);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return fullDate.format(DATE_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SessionDate)) {
            return false;
        }

        SessionDate otherDate = (SessionDate) other;
        return fullDate.equals(otherDate.fullDate);
    }

    @Override
    public int hashCode() {
        return fullDate.hashCode();
    }
}
