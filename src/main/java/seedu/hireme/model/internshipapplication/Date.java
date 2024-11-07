package seedu.hireme.model.internshipapplication;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.hireme.commons.util.AppUtil;
import seedu.hireme.logic.validator.DateValidator;

/**
 * Represents a Date.
 * Guarantees: immutable; the date is valid as declared in the constructor.
 */
public class Date implements Comparable<Date> {

    /**
     * Message to indicate the constraints on Date format and validity.
     * A valid Date must be in the format "dd/MM/yy", must represent a real calendar date,
     * and must not be a future date.
     */
    public static final String MESSAGE_CONSTRAINTS = "Dates must not be in the future, should be in the format "
            + c"'DD/MM/YY', and must represent a valid calendar date.";


    /**
     * Message to indicate that only the date should be provided without additional text.
     */
    public static final String MESSAGE_TOO_MANY_ARGUMENTS = "Date should only be DD/MM/YY with no other words";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yy");

    private final LocalDate date;

    /**
     * Constructs a {@code Date} with a {@code LocalDate}.
     *
     * @param date A valid {@code LocalDate} object.
     * @throws NullPointerException if the {@code date} is null.
     */
    public Date(LocalDate date) {
        requireNonNull(date);
        this.date = date;
    }

    /**
     * Constructs a {@code Date} from a string in the format 'dd/MM/yy'.
     *
     * @param dateString A string representing a date in the format 'dd/MM/yy'.
     * @throws NullPointerException if the {@code dateString} is null.
     * @throws IllegalArgumentException if the {@code dateString} does not follow the expected format or is invalid.
     */
    public Date(String dateString) {
        requireNonNull(dateString);
        boolean isValidDate = DateValidator.of().validate(dateString);
        AppUtil.checkArgument(isValidDate, MESSAGE_CONSTRAINTS);

        this.date = LocalDate.parse(dateString, FORMATTER);
    }

    /**
     * Returns a string representation of the date using the 'dd/MM/yy' format.
     *
     * @return The formatted date as a string.
     */
    @Override
    public String toString() {
        return date.format(FORMATTER);
    }

    /**
     * Compares this {@code Date} to another object for equality.
     *
     * @param other The object to compare with.
     * @return True if the object is an instance of {@code Date} and has the same date value, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;
        return date.equals(otherDate.date);
    }

    /**
     * Compares this {@code Date} with another for ordering.
     *
     * @param other The date to compare with.
     * @return 0 if the dates are the same, a negative number if this date is before, and a positive number if after.
     */
    @Override
    public int compareTo(Date other) {
        return date.compareTo(other.date);
    }

    /**
     * Returns the hash code of the date.
     *
     * @return The hash code of the {@code LocalDate} value.
     */
    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
