package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;

/**
 * A class to represent a person's date of birth in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(LocalDate)}
 */
public class DateOfBirth {

    public static final String MESSAGE_CONSTRAINTS = "Date of birth must not be a future date.";

    private final LocalDate value;

    /**
     * Constructs a {@code DateOfBirth}.
     *
     * @param date A valid {@link LocalDate}
     */
    public DateOfBirth(LocalDate date) {
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        value = date;
    }

    public LocalDate getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
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

        // also checks for null
        if (!(other instanceof DateOfBirth otherDateOfBirth)) {
            return false;
        }

        return value.equals(otherDateOfBirth.value);
    }

    /**
     * Returns true if a given date is a valid date of birth.
     *
     * @param test a {@link LocalDate} to be tested
     * @return {@code true} if the date is valid, {@code false} otherwise
     */
    public static boolean isValidDate(LocalDate test) {
        requireNonNull(test);
        return !test.isAfter(LocalDate.now());
    }
}
