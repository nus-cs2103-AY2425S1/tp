package seedu.address.model.tag;

import static java.lang.Integer.parseInt;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.tag.exceptions.InvalidGradeIndexException;

/**
 * Represents a Grade in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidGradeIndex(String)}
 */
public class Grade {
    public static final String MESSAGE_CONSTRAINTS = "Grade index should be numeric and between 0 to 5";
    public static final String VALIDATION_REGEX = "\\d+";
    public final String gradeIndex;

    /**
     * Constructs a {@code Grade}.
     *
     * @param gradeIndex A valid Grade Index.
     */
    public Grade(String gradeIndex) {
        requireNonNull(gradeIndex);
        checkArgument(isValidGradeIndex(gradeIndex), MESSAGE_CONSTRAINTS);
        this.gradeIndex = gradeIndex;
    }

    /**
     * Returns true if a given string is a valid grade index.
     */
    public static boolean isValidGradeIndex(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        int testInt = parseInt(test);
        return testInt >= 0 && testInt <= 4;
    }

    /**
     * Converts grade index into name.
     *
     * @return A name representing the grade index.
     */
    public String gradeIndexToName() {
        switch (parseInt(gradeIndex)) {
        case 0:
            return "Unknown (0)";
        case 1:
            return "Failing (1)";
        case 2:
            return "Satisfactory (2)";
        case 3:
            return "Good (3)";
        case 4:
            return "Excellent (4)";
        default:
            throw new InvalidGradeIndexException();
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Grade otherGrade)) {
            return false;
        }

        return gradeIndex.equals(otherGrade.gradeIndex);
    }

    @Override
    public int hashCode() {
        return gradeIndex.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return gradeIndex;
    }

}
