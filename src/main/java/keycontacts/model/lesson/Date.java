package keycontacts.model.lesson;

import static java.util.Objects.requireNonNull;
import static keycontacts.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a student's lesson date in the student directory.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date implements Comparable<Date> {

    public static final String MESSAGE_CONSTRAINTS = "Date should be in DD-MM-YYYY format";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter DATE_TIME_FORMATTER_DISPLAY = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    public static final String VALIDATION_REGEX = "^(0[1-9]|[12]\\d|3[01])-(0[1-9]|1[0-2])-\\d{4}$";

    public final LocalDate value;

    /**
     * Constructs a {@code Date} object.
     *
     * @param date A valid date
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.value = LocalDate.parse(date, DATE_TIME_FORMATTER);
    }

    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value.format(DATE_TIME_FORMATTER);
    }

    /**
     * Returns the date in a user-friendly display format.
     */
    public String toDisplay() {
        return value.format(DATE_TIME_FORMATTER_DISPLAY);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
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
    public int compareTo(Date other) {
        return this.value.compareTo(other.value);
    }

    /**
     * Returns a {@code Day} object which the date falls on.
     */
    public Day convertToDay() {
        return new Day(value.getDayOfWeek().name());
    }
}
