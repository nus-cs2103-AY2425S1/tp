package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents the date and time of a delivery in the application.
 */
public class DateTime implements Comparable<DateTime> {

    public static final String MESSAGE_CONSTRAINTS =
            "DELIVERY_DATE_TIME should be in the format dd-MM-yyyy HH:mm and must not be blank.";

    public static final DateTimeFormatter FORMATTER_TIME =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private final LocalDateTime dateTime;

    /**
     * Constructs a {@code Time}.
     *
     * @param dateTime A valid time following the format of dd-MM-yyyy HH:mm.
     */
    public DateTime(String dateTime) {
        requireNonNull(dateTime);
        checkArgument(isValidTime(dateTime), MESSAGE_CONSTRAINTS);
        this.dateTime = parseTime(dateTime.trim());
    }

    /**
     * Compares this dateTime with the specified dateTime for order.
     * Returns a negative integer, zero, or a positive integer as this dateTime is less than, equal to,
     * or greater than the specified dateTime.
     *
     * @param otherDateTime The dateTime to be compared.
     * @return a negative integer, zero, or a positive integer as this dateTime is less than, equal to,
     *      or greater than the other dateTime.
     */
    public int compareTo(DateTime otherDateTime) {
        return time.compareTo(otherDateTime.time);
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        return canParse(test, FORMATTER_TIME);
    }

    private static boolean canParse(String dateTime, DateTimeFormatter formatter) {
        try {
            LocalDateTime.parse(dateTime, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Parses a given string into a LocalDateTime object.
     *
     * @param dateTime String to be parsed.
     * @return LocalDateTime object parsed from the string.
     */
    private static LocalDateTime parseTime(String dateTime) {
        if (canParse(dateTime, FORMATTER_TIME)) {
            return LocalDateTime.parse(dateTime, FORMATTER_TIME);
        } else {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Checks if instance of DateTime has LocalDateTime value that is earlier than input deliveryDateTime.
     *
     * @param deliveryDateTime Input deliveryDateTime to compare against.
     * @return True if instance of DateTime is earlier thant input.
     */
    public boolean isEarlierThan(DateTime deliveryDateTime) {
        return this.dateTime.isBefore(deliveryDateTime.dateTime);
    }

    /**
     * Checks if instance of DateTime has LocalDateTime value that is later than input deliveryDateTime.
     *
     * @param deliveryDateTime Input deliveryDateTime to compare against.
     * @return True if instance of DateTime is later than input.
     */
    public boolean isLaterThan(DateTime deliveryDateTime) {
        return this.dateTime.isAfter(deliveryDateTime.dateTime);
    }

    @Override
    public String toString() {
        return dateTime.format(FORMATTER_TIME);
    }


    public String displayFormater() {
        return dateTime.format(DateTimeFormatter.ofPattern("d MMMM yyyy h:mm a"));
    }

    public LocalDateTime getDateTime() {

        assert dateTime != null;
        return this.dateTime;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DateTime)) {
            return false;
        }

        DateTime otherDateTime = (DateTime) other;
        return dateTime.equals(otherDateTime.dateTime);
    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }
}
