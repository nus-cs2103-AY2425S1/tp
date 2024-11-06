package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Person's LastPaidDate in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class LastPaidDate {

    public static final String MESSAGE_CONSTRAINTS =
            "LastPaidDate should only contain numbers, and it should be in the format DD MM YYYY";
    public final String value;
    public final LocalDate date;

    /**
     * Constructs a {@code lastPaidDate}.
     * @param lastPaidDate a valid date
     */
    public LastPaidDate(String lastPaidDate) {
        requireNonNull(lastPaidDate);
        checkArgument(isValidDate(lastPaidDate), MESSAGE_CONSTRAINTS);
        value = lastPaidDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM uuuu");
        date = LocalDate.parse(lastPaidDate, formatter);
    }

    /**
     * Overloaded constructor for LastPaidDate for when input is already a LocalDate object
     * @param lastPaidDate a LocalDate object
     */
    public LastPaidDate(LocalDate lastPaidDate) {
        requireNonNull(lastPaidDate);
        value = lastPaidDate.toString();
        date = lastPaidDate;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM uuuu");
        try {
            LocalDate.parse(test, formatter);
            return true; // The date is valid
        } catch (DateTimeParseException e) {
            return false; // The date is invalid
        }
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
        if (!(other instanceof LastPaidDate)) {
            return false;
        }
        LastPaidDate otherDate = (LastPaidDate) other;
        return otherDate.value.equals(value);
    }

    @Override
    public int hashCode() {
        if (value == null) {
            return 0;
        }
        return value.hashCode();
    }
}
