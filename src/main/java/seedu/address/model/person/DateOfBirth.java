package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;

/**
 * A class to represent a person's date of birth in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DateOfBirth extends Date {

    public static final String MESSAGE_CONSTRAINTS =
            String.format("%s. Date of birth must not be a future date.", Date.MESSAGE_CONSTRAINTS);

    /**
     * Constructs a {@code DateOfBirth}.
     *
     * @param date A valid date string
     */
    public DateOfBirth(String date) {
        super(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
    }

    /**
     * Returns true if a given string is a valid date.
     *
     * @param test A string to be tested
     * @return {@code true} if the string is valid, {@code false} otherwise
     */
    public static boolean isValidDate(String test) {
        requireNonNull(test);

        if (!Date.isValidDate(test)) {
            return false;
        }

        LocalDate localDate = Date.parseLocalDate(test);
        LocalDate now = LocalDate.now();
        return localDate.isBefore(now) || localDate.isEqual(now);
    }
}
