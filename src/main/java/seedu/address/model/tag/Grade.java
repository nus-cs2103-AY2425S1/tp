package seedu.address.model.tag;

import static java.lang.Integer.parseInt;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.tag.exceptions.InvalidGradeIndexException;

/**
 * Represents a Grade in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidGradeName(String)}
 */
public class Grade {
    public static final String MESSAGE_CONSTRAINTS = "Grade index should be numeric";
    public static final String VALIDATION_REGEX = "\\d+";
    public final String gradeName;

    /**
     * Constructs a {@code Grade}.
     *
     * @param gradeIndex A valid Grade index.
     */
    public Grade(String gradeIndex) {
        requireNonNull(gradeIndex);
        checkArgument(isValidGradeName(gradeIndex), MESSAGE_CONSTRAINTS);
        gradeName = convertIndexToName(gradeIndex);
    }

    /**
     * Returns true if a given string is a valid grade index.
     */
    public static boolean isValidGradeName(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        int testInt = parseInt(test);
        return testInt >= 1 && testInt <= 4;
    }

    private String convertIndexToName(String index) {
        String name = "";
        switch (parseInt(index)) {
        case 1:
            name = "Excellent";
            break;
        case 2:
            name = "Good";
            break;
        case 3:
            name = "Satisfactory";
            break;
        case 4:
            name = "Failing";
            break;
        default:
            throw new InvalidGradeIndexException();
        }
        return name;
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

        return gradeName.equals(otherGrade.gradeName);
    }

    @Override
    public int hashCode() {
        return gradeName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + gradeName + ']';
    }

}
