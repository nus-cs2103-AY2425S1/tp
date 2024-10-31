package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's age in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAge(int)}
 */
public class Age implements OptionalField {

    private static final int LOWER_LIMIT = 0;
    private static final int UPPER_LIMIT = 150;
    public static final String MESSAGE_CONSTRAINTS = "Age should be a whole number within "
            + LOWER_LIMIT + " to " + UPPER_LIMIT + " inclusive.";

    public final Integer value;

    /**
     * Constructs an {@code Age}.
     *
     * @param age A valid age.
     */
    public Age(Integer age) {
        requireNonNull(age);
        checkArgument(isValidAge(age), MESSAGE_CONSTRAINTS);
        value = age;
    }

    /**
     * Constructs an {@code Age}.
     *
     * @param age A valid age, in String format.
     */
    public Age(String age) {
        requireNonNull(age);
        checkArgument(isValidAge(age), MESSAGE_CONSTRAINTS);
        value = Integer.parseInt(age);
    }

    /**
     * Constructs an empty {@code Age}.
     * Package-private.
     */
    Age() {
        value = null;
    }

    /**
     * Returns an empty {@code Age}.
     */
    public static Age createEmpty() {
        return EmptyAge.get();
    }

    /**
     * Returns false
     */
    public boolean isEmpty() {
        return false;
    }

    /**
     * Returns if a given int is a valid age.
     */
    public static boolean isValidAge(int test) {
        return test >= LOWER_LIMIT && test <= UPPER_LIMIT;
    }

    /**
     * Returns if a given string is a valid age.
     */
    public static boolean isValidAge(String test) {
        int converted;

        // Verify if the String is an integer first
        try {
            converted = Integer.parseInt(test);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return converted >= LOWER_LIMIT && converted <= UPPER_LIMIT;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * Returns the String to be presented on the UI.
     */
    @Override
    public String getValueForUI() {
        return toString();
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
        return value;
    }

}
