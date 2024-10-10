package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's Grade.
 */
public class Grade {

    public static final String MESSAGE_GRADE_CONSTRAINTS =
            "Grades should be numeric values between 0 and 100 (in percentage), including decimals.";
    public static final String MESSAGE_TEST_NAME_CONSTRAINTS =
            "Test names should be at least 3 characters long and " +
                    "contain only alphanumeric characters, spaces, hyphens (-), or underscores (_).";

    // Validation regex for test name (allows alphabetic characters and spaces)
    public static final String TEST_NAME_VALIDATION_REGEX = "^[A-Za-z ]+$";

    public final String testName;
    public final double gradeValue;

    /**
     * Constructs a {@code Grade}.
     *
     * @param testName A valid test name.
     * @param gradeValue A valid grade value.
     */
    public Grade(String testName, double gradeValue) {
        requireNonNull(testName);
        checkArgument(isValidTestName(testName), MESSAGE_TEST_NAME_CONSTRAINTS);
        checkArgument(isValidGrade(gradeValue), MESSAGE_GRADE_CONSTRAINTS);
        this.testName = testName;
        this.gradeValue = gradeValue;
    }

    /**
     * Returns true if the given grade value is valid.
     * The grade must be a numeric value between 0 and 100.
     *
     * @param grade The grade value to check.
     * @return true if the grade is between 0 and 100, false otherwise.
     */
    public static boolean isValidGrade(double grade) {
        return grade >= 0 && grade <= 100;
    }

    /**
     * Returns true if the given test name is valid.
     * A valid test name must:
     * - Be at least 3 characters long.
     * - Contain only alphanumeric characters, spaces, hyphens (-), or underscores (_).
     *
     * @param testName The test name to check.
     * @return true if the test name is valid, false otherwise.
     */
    public static boolean isValidTestName(String testName) {
        return testName.matches(TEST_NAME_VALIDATION_REGEX);
    }

    /**
     * Returns a string representation of the grade, including the test name and grade value.
     *
     * @return A string in the format "TestName: GradeValue".
     */
    @Override
    public String toString() {
        return testName + ": " + gradeValue + '%';
    }

    /**
     * Compares this grade object to another grade object for equality.
     * Two grade objects are considered equal if both their test names and grade values are the same.
     *
     * @param other The object to compare with.
     * @return true if the other object is a Grade with the same test name and grade value, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Grade)) {
            return false;
        }

        Grade otherGrade = (Grade) other;
        return Double.compare(gradeValue, otherGrade.gradeValue) == 0
                && testName.equalsIgnoreCase(otherGrade.testName);
    }

    /**
     * Returns the hash code for this Grade object.
     * The hash code is based on the grade value and the lowercased test name.
     *
     * @return The hash code for this Grade.
     */
    @Override
    public int hashCode() {
        return Double.hashCode(gradeValue) + testName.toLowerCase().hashCode();
    }

}
