package tutorease.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static tutorease.address.commons.util.DateTimeUtil.checkValidDateTime;
import static tutorease.address.commons.util.DateTimeUtil.dateTimeToString;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import tutorease.address.commons.core.LogsCenter;
import tutorease.address.logic.commands.AddLessonCommand;
import tutorease.address.logic.parser.exceptions.ParseException;

/**
 * Represents a DateTime in the address book.
 */
public class DateTime implements Comparable<DateTime> {
    private static Logger logger = LogsCenter.getLogger(AddLessonCommand.class);
    private final LocalDateTime dateTime;

    /**
     * Constructs a {@code DateTime}.
     *
     * @param dateTime A valid date and time.
     */
    public DateTime(LocalDateTime dateTime) throws ParseException {
        logger.log(Level.INFO, "Creating DateTime object with date time: " + dateTime);
        requireNonNull(dateTime);
        checkValidDateTime(dateTimeToString(dateTime));

        this.dateTime = dateTime;
        logger.log(Level.INFO, "Created DateTime object with date time: " + dateTime);
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
        try {
            checkValidDateTime(dateTime);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
