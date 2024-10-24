package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.Period;

/**
 * Represents a Person's age in the address book.
 * Guarantees: immutable; is valid;
 */
public class Age {

    public final String value;
    public final int age;

    /**
     * Constructs a {@code age}.
     *
     * @param birthday A valid birthday.
     */
    public Age(Birthday birthday) {
        requireNonNull(birthday);
        age = calculateAge(birthday);
        value = String.valueOf(age);
    }

    /**
     * Calculates age of person based on given birthday.
     * @param birthday A valid birthday.
     * @return Age of person.
     */
    public int calculateAge(Birthday birthday) {
        LocalDate today = LocalDate.now();
        LocalDate birthDate = birthday.date;
        return Period.between(birthDate, today).getYears();
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
        if (!(other instanceof Age)) {
            return false;
        }

        Age otherAge = (Age) other;
        return value.equals(otherAge.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

