package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Patient's date of birth in the contacts book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DateOfBirth {

    public static final String MESSAGE_CONSTRAINTS = "Dates must be in the format of DD-MM-YYYY";

    /*
    * Matches an input string to the DD-MM-YYYY format.
    */
    public static final String VALIDATION_REGEX = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public final LocalDate date;

    /**
     * Constructs a {@code DateOfBirth}.
     *
     * @param input A valid date.
     */
    public DateOfBirth(String input) {
        requireNonNull(input);
        checkArgument(isValidDate(input), MESSAGE_CONSTRAINTS);

        date = LocalDate.parse(input, FORMATTER);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return date.format(FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DateOfBirth)) {
            return false;
        }

        DateOfBirth otherDateOfBirth = (DateOfBirth) other;
        return date.equals(otherDateOfBirth.date);
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
