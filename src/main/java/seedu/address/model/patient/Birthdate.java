package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Patient's BirthDate in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthdate(String)}
 */
public class Birthdate {

    public static final String MESSAGE_CONSTRAINTS =
            "Birth Date should follow the format YYYY-MM-DD and should not be after today's date";

    public final String value;

    /**
     * Constructs an {@code BirthDate}.
     *
     * @param birthdate A valid birthdate.
     */
    public Birthdate(String birthdate) {
        requireNonNull(birthdate);
        checkArgument(isValidBirthdate(birthdate), MESSAGE_CONSTRAINTS);
        value = birthdate;
    }

    /**
     * Returns if a given string is a valid nric.
     */
    public static boolean isValidBirthdate(String test) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate date = LocalDate.parse(test, formatter);
            return !date.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
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
        if (!(other instanceof Birthdate)) {
            return false;
        }

        Birthdate otherBirthdate = (Birthdate) other;
        return value.equals(otherBirthdate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
