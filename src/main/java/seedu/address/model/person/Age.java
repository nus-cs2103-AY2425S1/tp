package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's age in the address book. Guarantees: immutable; is valid as declared in
 * {@link #isValidAge(String)}
 */
public class Age {

    public static final String MESSAGE_CONSTRAINTS = "Age should only contain a non-negative integer, "
            + "between 0 and 150 (inclusive)!";

    /*
     * The age must be a non-negative integer.
     */
    public static final String VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Constructs a {@code Age}.
     *
     * @param age A valid age.
     */
    public Age(String age) {
        requireNonNull(age);
        checkArgument(isValidAge(age), MESSAGE_CONSTRAINTS);
        this.value = age;
    }

    /**
     * Returns true if a given string is a valid age. A valid age should be a non-negative integer, between 0 and 150
     * (inclusive).
     */
    public static boolean isValidAge(String test) {

        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        try {
            int age = Integer.parseInt(test);
            return age >= 0 && age <= 150;
        } catch (NumberFormatException e) {
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
