package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;

/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is always valid
 */
public class Birthday {

    public static final String MESSAGE_CONSTRAINTS =
            "Birthday should be of the format yyyy-mm-dd";
    public static final String VALIDATION_REGEX = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
    public static final Birthday EMPTY_BIRTHDAY = Birthday.of("");
    public final LocalDate value;

    /**
     * Constructs a {@code Birthday}.
     *
     * @param birthday A valid birthday.
     */
    public Birthday(String birthday) {
        requireNonNull(birthday);
        checkArgument(isValidBirthday(birthday), MESSAGE_CONSTRAINTS);
        if (birthday.isEmpty()) {
            value = LocalDate.MIN;
        } else {
            value = LocalDate.parse(birthday);
        }
    }

    /**
     * Returns true if a given string is a valid birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(VALIDATION_REGEX) || test.isEmpty();
    }

    public static Birthday of(String birthday) {
        return new Birthday(birthday);
    }

    public LocalDate getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.equals(LocalDate.MIN) ? "" : value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
