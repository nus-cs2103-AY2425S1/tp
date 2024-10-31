package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

/**
 * Represents a Person's last visited date by the social worker in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateOfLastVisit(String)}
 */
public class DateOfLastVisit implements Comparable<DateOfLastVisit> {

    public static final String MESSAGE_CONSTRAINTS = "Date of last visit should be in dd-MM-yyyy format.\n"
            + "Ensure that the month is 01-12 and the date is not later than today. ";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    public final String value;

    private final LocalDate localDate;

    /**
     * Constructs a {@code DateOfLastVisit}.
     *
     * @param date A valid date.
     */
    public DateOfLastVisit(String date) {
        requireNonNull(date);
        checkArgument(isValidDateOfLastVisit(date), MESSAGE_CONSTRAINTS);
        value = date;
        localDate = LocalDate.parse(date, DATE_TIME_FORMATTER);
    }

    /**
     * Returns true if a given string is a valid date of last visit.
     */
    public static boolean isValidDateOfLastVisit(String date) {
        try {
            DATE_TIME_FORMATTER.parse(date);
        } catch (DateTimeException e) {
            return false;
        }
        return true;
    }

    /**
     * @param other the date to be compared to.
     * @return an integer which specifies which date is earlier or if they are equal.
     */
    @Override
    public int compareTo(DateOfLastVisit other) {
        return localDate.compareTo(other.localDate);
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
        if (!(other instanceof DateOfLastVisit)) {
            return false;
        }

        DateOfLastVisit otherDateOfLastVisit = (DateOfLastVisit) other;
        return value.equals(otherDateOfLastVisit.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
