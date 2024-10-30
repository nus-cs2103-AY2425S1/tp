package keycontacts.model.lesson;

import static java.util.Objects.requireNonNull;
import static keycontacts.commons.util.AppUtil.checkArgument;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

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

    /**
     * Constructs a {@code Date} object by directly wrapping around a {@code LocalDate} object.
     */
    public Date(LocalDate value) {
        requireNonNull(value);
        this.value = value;
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

    // util functions
    /**
     * Returns a {@code Day} object which the date falls on.
     */
    public Day convertToDay() {
        return new Day(value.getDayOfWeek().name());
    }

    /**
     * Returns a {@code Date} object representing the Monday of the week of this date.
     */
    public Date getFirstDayOfWeek() {
        return new Date(value.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)));
    }

    /**
     * Returns a {@code Date} object representing the Sunday of the week of this date.
     */
    public Date getLastDayOfWeek() {
        return new Date(value.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)));
    }
}
