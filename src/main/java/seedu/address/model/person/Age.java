package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's age in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAge(int)}
 */
public class Age {

    public static final String MESSAGE_CONSTRAINTS =
            "Age should only contain non-negative integers, and it should not be blank";

    public static final String VALIDATION_REGEX = "0|[1-9]\\d*";

    public final int value;

    /**
     * Constructs an {@code Age}.
     *
     * @param age A valid age integer
     */
    public Age(int age) {
        checkArgument(isValidAge(age), MESSAGE_CONSTRAINTS);
        value = age;
    }

    public static boolean isValidAge(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public static boolean isValidAge(int test) {
        return test >= 0;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles null
        if (!(other instanceof Age)) {
            return false;
        }

        Age otherAge = (Age) other;

        // use `==` instead of `.equals` because int is primitive type
        return value == otherAge.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
