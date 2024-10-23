package seedu.address.model.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a Task's date (either start or end date).
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}.
 */
public class Date {
    public static final String MESSAGE_CONSTRAINTS = "Date should be in the format yyyy-MM-dd.";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final LocalDate date;

    /**
     * Constructs a {@code TaskDate}.
     *
     * @param date A valid date string in the format "yyyy-MM-dd".
     */
    public Date(String date) {
        Objects.requireNonNull(date);
        this.date = LocalDate.parse(date, FORMATTER);
    }

    /**
     * Checks if the given string is a valid date in the format "yyyy-MM-dd".
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test, FORMATTER);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns the formatted date.
     */
    public String format(DateTimeFormatter formatter) {
        return date.format(formatter);
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return date.format(FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;
        return date.equals(otherDate.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
