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
            "Test names should be at least 3 characters long and "
                    + "contain only alphanumeric characters, spaces, hyphens (-), or underscores (_).";
    public static final String MESSAGE_WEIGHTAGE_CONSTRAINTS =
            "Weightage should be a numeric value between 0 (exclusive) and 100 (inclusive).";


    // Validation regex for test name (allows alphabetic characters and spaces)
    public static final String TEST_NAME_VALIDATION_REGEX = "^[A-Za-z ]+$";

    public final String testName;
    public final float score;
    public final float weightage;

    /**
     * Constructs a {@code Grade}.
     *
     * @param testName A valid test name.
     * @param score A valid grade value.
     */
    public Grade(String testName, float score, float weightage) {
        requireNonNull(testName);
        checkArgument(isValidTestName(testName), MESSAGE_TEST_NAME_CONSTRAINTS);
        checkArgument(isValidGrade(score), MESSAGE_GRADE_CONSTRAINTS);
        checkArgument(isValidWeightage(weightage), MESSAGE_WEIGHTAGE_CONSTRAINTS);
        this.testName = testName;
        this.score = score;
        this.weightage = weightage;
    }

    /**
     * Returns true if the given grade value is valid.
     * The grade must be a numeric value between 0 and 100.
     *
     * @param score The grade value to check.
     * @return true if the grade is between 0 and 100, false otherwise.
     */
    public static boolean isValidGrade(float score) {
        return Float.compare(score, 0.0f) >= 0 && Float.compare(score, 100.0f) <= 0;
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
     * Returns true if the given weightage is valid.
     * The weightage must be greater than 0 and less than or equal to 1.
     *
     * @param weightage The weightage value to check.
     * @return true if the weightage is between 0 (exclusive) and 1 (inclusive), false otherwise.
     */
    public static boolean isValidWeightage(float weightage) {
        return Float.compare(weightage, 0.0f) > 0 && Float.compare(weightage, 100.0f) <= 0;
    }

    @Override
    public String toString() {
        return testName + ": " + score + '%';
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Grade)) {
            return false;
        }

        Grade otherGrade = (Grade) other;
        return Double.compare(score, otherGrade.score) == 0
                && Double.compare(weightage, otherGrade.weightage) == 0
                && testName.equalsIgnoreCase(otherGrade.testName);
    }

    @Override
    public int hashCode() {
        return Double.hashCode(score) * Double.hashCode(weightage) * testName.toLowerCase().hashCode();
    }
}
