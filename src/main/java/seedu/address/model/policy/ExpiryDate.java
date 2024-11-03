package seedu.address.model.policy;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.dateformatter.DateFormatter.MM_DD_YYYY_FORMATTER;
import static seedu.address.commons.core.dateformatter.DateFormatter.MM_DD_YYYY_PATTERN;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents a Policy's expiry date.
 * Guarantees: immutable; is valid as declared in {@link #isValidExpiryDate(String)}
 */
public class ExpiryDate {
    public static final String MESSAGE_CONSTRAINTS = "Expiry date should be of the format " + MM_DD_YYYY_PATTERN + ".";

    public final LocalDate value;

    /**
     * Constructs an {@code ExpiryDate} using a {@code String} object.
     *
     * @param expiryDate A valid expiry date.
     */
    public ExpiryDate(String expiryDate) {
        requireNonNull(expiryDate);
        checkArgument(isValidExpiryDate(expiryDate), MESSAGE_CONSTRAINTS);
        value = LocalDate.parse(expiryDate, MM_DD_YYYY_FORMATTER);
    }

    /**
     * Constructs an {@code ExpiryDate} using a {@code LocalDate} object.
     *
     * @param expiryDate An expiry date.
     */
    public ExpiryDate(LocalDate expiryDate) {
        requireNonNull(expiryDate);
        value = expiryDate;
    }

    /**
     * Returns if a given string is a valid expiry date.
     */
    public static boolean isValidExpiryDate(String test) {
        try {
            LocalDate.parse(test, MM_DD_YYYY_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns whether this expiry date is before the date.
     *
     * @param date to be compared with this expiry date.
     */
    public boolean isBefore(LocalDate date) {
        return value.isBefore(date);
    }

    /**
     * Returns whether this expiry date is after the date.
     *
     * @param date to be compared with this expiry date.
     */
    public boolean isAfter(LocalDate date) {
        return value.isAfter(date);
    }

    /**
     * Returns whether this expiry date is equal to the date.
     *
     * @param date to be compared with this expiry date.
     */
    public boolean isEqual(LocalDate date) {
        return value.isEqual(date);
    }

    @Override
    public String toString() {
        return value.format(MM_DD_YYYY_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExpiryDate)) {
            return false;
        }

        ExpiryDate otherExpiryDate = (ExpiryDate) other;
        return value.equals(otherExpiryDate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
