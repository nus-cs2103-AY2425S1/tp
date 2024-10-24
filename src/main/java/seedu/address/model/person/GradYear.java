package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Resident's graduation year in the dormManager book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGradYear(String)}
 * Note: is optional.
 */
public class GradYear {

    public static final String MESSAGE_CONSTRAINTS = "A string of length 4 in the form of “YYYY” "
            + "Y are integers representing the year of graduation. "
            + "The first digit is 2. Eg \"2027\"";


    /*
     * 4 integers representing the year
     * first integer is 2.
     */
    public static final String VALIDATION_REGEX = "2\\d{3}";

    public final String value;

    /**
     * Constructs an {@code GradYear}.
     *
     * @param gradDate A valid year.
     */
    public GradYear(String gradDate) {
        requireNonNull(gradDate);
        checkArgument(isValidGradYear(gradDate), MESSAGE_CONSTRAINTS);
        value = gradDate;
    }

    /**
     * Returns true if a given string is a valid gradYear.
     */
    public static boolean isValidGradYear(String test) {
        return test.matches(VALIDATION_REGEX);
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
        if (!(other instanceof GradYear)) {
            return false;
        }

        GradYear otherGradYear = (GradYear) other;
        return value.equals(otherGradYear.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
