package seedu.academyassist.model.person;

import static java.util.Objects.requireNonNull;

import seedu.academyassist.commons.util.AppUtil;

/**
 * Represents a Person's year group in the management system.
 * Guarantees: immutable; is valid as declared in {@link #isValidYearGroup(String)}
 */
public class YearGroup {


    public static final String MESSAGE_CONSTRAINTS =
            "Year group should only contain numbers, and it should be between 1 and 13";
    public static final String VALIDATION_REGEX = "^(1[0-3]|[1-9])$"; // Adapted from the help of ChatGPT
    public final String value;

    /**
     * Constructs a {@code YearGroup}.
     *
     * @param yearGroup A valid year group.
     */
    public YearGroup(String yearGroup) {
        requireNonNull(yearGroup);
        AppUtil.checkArgument(isValidYearGroup(yearGroup), MESSAGE_CONSTRAINTS);
        value = yearGroup;
    }

    /**
     * Returns true if a given string is a valid year group.
     */
    public static boolean isValidYearGroup(String test) {
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
        if (!(other instanceof YearGroup)) {
            return false;
        }

        YearGroup otherYearGroup = (YearGroup) other;
        return value.equals(otherYearGroup.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
