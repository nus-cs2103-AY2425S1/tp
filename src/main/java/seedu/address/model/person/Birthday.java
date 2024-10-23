package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {


    public static final String MESSAGE_CONSTRAINTS =
            "Birthdays should only contain numbers, and it should be in the format DD MM YYYY";
    public final String value;
    public final LocalDate date;

    /**
     * Constructs a {@code birthday}.
     *
     * @param birthday A valid birthday.
     */
    public Birthday(String birthday) {
        requireNonNull(birthday);
        checkArgument(isValidBirthday(birthday), MESSAGE_CONSTRAINTS);
        value = birthday;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM uuuu");
        date = LocalDate.parse(birthday, formatter);
    }

    /**
     * Returns true if a given string is a valid birthday.
     */
    public static boolean isValidBirthday(String test) {
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

        // instanceof handles nulls
        if (!(other instanceof Birthday)) {
            return false;
        }

        Birthday otherBirthday = (Birthday) other;
        return value.equals(otherBirthday.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
