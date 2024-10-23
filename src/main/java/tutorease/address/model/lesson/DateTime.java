package tutorease.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static tutorease.address.commons.util.AppUtil.checkArgument;
import static tutorease.address.commons.util.DateTimeUtil.dateTimeToString;
import static tutorease.address.commons.util.DateTimeUtil.getDateTimeFormat;

import java.time.LocalDateTime;

import tutorease.address.commons.util.DateTimeUtil;

/**
 * Represents a DateTime in the address book.
 */
public class DateTime implements Comparable<DateTime> {
    private static final String MESSAGE_CONSTRAINTS = "DateTime must be in the format of " + getDateTimeFormat();
    private final LocalDateTime dateTime;
    /**
     * Constructs a {@code DateTime}.
     *
     * @param dateTime A valid date and time.
     */
    public DateTime(LocalDateTime dateTime) {
        requireNonNull(dateTime);
        checkArgument(DateTimeUtil.isValidDateTime(dateTimeToString(dateTime)), MESSAGE_CONSTRAINTS);
        this.dateTime = dateTime;
    }

    /**
     * Returns the date and time.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Returns true if a given date time is before the other.
     *
     * @param other The other date time to compare with.
     * @return True if this date time is before the other.
     */
    public boolean isBefore(DateTime other) {
        return this.dateTime.isBefore(other.getDateTime());
    }

    /**
     * Returns true if a given date time is after the other.
     *
     * @param other The other date time to compare with.
     * @return True if this date time is after the other.
     */
    public boolean isAfter(DateTime other) {
        return this.dateTime.isAfter(other.getDateTime());
    }

    @Override
    public String toString() {
        return dateTimeToString(dateTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DateTime)) {
            return false;
        }

        DateTime otherDateTime = (DateTime) other;
        return dateTime.equals(otherDateTime.dateTime);
    }
    /**
     * Compares this date time with another date time.
     *
     * @param dateTime The date time to compare with.
     * @return A negative integer, zero, or a positive integer as this date time is before, equal to, or after the
     *     specified date time.
     */
    @Override
    public int compareTo(DateTime dateTime) {
        return this.dateTime.compareTo(dateTime.dateTime);
    }

    /**
     * Returns true if a given string is a valid date and time.
     *
     * @param dateTime The date and time to be checked.
     * @return True if the date and time is valid, false otherwise.
     */
    public static boolean isValidDateTime(String dateTime) {
        return DateTimeUtil.isValidDateTime(dateTime);
    }
}
