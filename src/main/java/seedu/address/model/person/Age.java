package seedu.address.model.person;

import static java.lang.Integer.parseInt;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Age in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAge(String)}
 */
public class Age {
    public static final String MESSAGE_CONSTRAINTS =
           "Age should only contain numbers and should be between 0 to 999 inclusive";

    /**
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "\\d{1,3}";
    public static final String REMOVE_STARTING_ZEROES_REGEX = "^0+(?!$)";

    public final String value;

    /**
     * Constructs a {@code Age}.
     *
     * @param age A valid Age.
     */
    public Age(String age) {
        requireNonNull(age);

        String toAdd = age.replaceFirst(REMOVE_STARTING_ZEROES_REGEX, "");
        checkArgument(isValidAge(toAdd), MESSAGE_CONSTRAINTS);
        value = toAdd;
    }

    /**
     * Returns true if a given string is a valid age.
     */
    public static boolean isValidAge(String test) {
        String toTest = test.replaceFirst(REMOVE_STARTING_ZEROES_REGEX, "");
        return toTest.matches(VALIDATION_REGEX) && parseInt(toTest) >= 0 && parseInt(toTest) <= 999;
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

        Age otherName = (Age) other;
        return value.equals(otherName.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
