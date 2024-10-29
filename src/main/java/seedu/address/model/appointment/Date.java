package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents the date of an appointment in the address book.
 * The {@code Date} class encapsulates the appointment date as a string.
 * This value cannot be null.
 */
public class Date {

    public static final Date EMPTY_DATE = new Date(LocalDate.MIN);
    public static final String MESSAGE_CONSTRAINTS =
            "Dates should be in the format dd-MM-yy or ddMMyy, e.g., 25-12-24 or 251224.";
    private static final String VALIDATION_REGEX = "\\d{2}-\\d{2}-\\d{2}|\\d{6}";

    public final LocalDate value;

    /**
     * Constructs a {@code Date} object with the specified date value.
     * The value must not be {@code null}.
     *
     * @param value A string representing the date.
     */
    public Date(String value) {
        requireNonNull(value);
        checkArgument(isValidDate(value), MESSAGE_CONSTRAINTS);
        this.value = parseDate(value);
    }

    // Private constructor for EMPTY_DATE
    private Date(LocalDate date) {
        this.value = date;
    }

    /**
     * Checks if this date is today's date.
     *
     * @return true if the date is today; false otherwise.
     */
    public boolean isToday() {
        return LocalDate.now().equals(value);
    }

    /**
     * Checks if the date string is in the valid format.
     */
    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Parses the date string into a LocalDate.
     */
    private LocalDate parseDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
            if (date.matches("\\d{6}")) { // If in ddMMyy format, add dashes for parsing
                date = date.substring(0, 2) + "-" + date.substring(2, 4) + "-" + date.substring(4);
            }
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Date
                && value.equals(((Date) other).value));
    }

    @Override
    public String toString() {
        return value.format(DateTimeFormatter.ofPattern("dd-MM-yy"));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
