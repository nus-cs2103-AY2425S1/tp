package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {

    public static final String MESSAGE_CONSTRAINTS =
            "Birthday should be in the format DD-MM-YYYY and should be a valid date. "
                    + "For example: 29-02-2000 is valid but 29-02-2001 is not as 2001 is not a leap year.";

    public static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter[] ACCEPTED_INPUT_FORMATS = {
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("d-M-yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("d/M/yyyy")
    };

    public final LocalDate value;

    /**
     * Constructs a {@code Birthday}.
     *
     * @param birthday A valid birthday string.
     */
    public Birthday(String birthday) {
        requireNonNull(birthday);
        checkArgument(isValidBirthday(birthday), MESSAGE_CONSTRAINTS);
        value = parseBirthday(birthday);
    }

    /**
     * Parses a birthday string into a LocalDate.
     * Attempts to parse using multiple common date formats.
     *
     * @param birthday The birthday string to parse
     * @return The parsed LocalDate
     * @throws IllegalArgumentException if the date cannot be parsed in any accepted format
     */
    private static LocalDate parseBirthday(String birthday) {
        for (DateTimeFormatter formatter : ACCEPTED_INPUT_FORMATS) {
            try {
                return LocalDate.parse(birthday.trim(), formatter);
            } catch (DateTimeParseException e) {
                continue;
            }
        }
        throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
    }

    /**
     * Returns true if a given string is a valid birthday.
     */
    public static boolean isValidBirthday(String test) {
        if (test == null || test.trim().isEmpty()) {
            return false;
        }

        try {
            parseBirthday(test);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value.format(DISPLAY_FORMAT);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

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
