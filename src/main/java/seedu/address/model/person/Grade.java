package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Student's Grade.
 * Guarantees: immutable; is valid as declared in {@link #isValidGrade(float)} and {@link #isValidWeightage(float)}.
 */
public class Grade {

    public static final String MESSAGE_GRADE_CONSTRAINTS =
            "Grades should be numeric values between 0 and 100 (in percentage), including decimals.";
    public static final String MESSAGE_TEST_NAME_CONSTRAINTS =
            "Test names should be at least 3 characters long and "
                    + "contain only alphanumeric characters, spaces, hyphens (-), or underscores (_).";

    public static final String MESSAGE_SCORE_CONSTRAINTS =
            "Score should be in the form of a number and is between 0 "
                    + "and 100 inclusive";
    public static final String MESSAGE_WEIGHTAGE_CONSTRAINTS =
            "Weightage should be between 0 and 100 (inclusive).";


    // Validation regex for test name (allows alphanumeric characters and spaces)
    private static final String TEST_NAME_VALIDATION_REGEX = "^[A-Za-z0-9 _-]{3,}$";

    private final String testName;
    private final float score;
    private final float weightage;

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
        return Float.compare(weightage, 0.0f) >= 0 && Float.compare(weightage, 100.0f) <= 0;
    }

    /**
     * Returns the test name.
     *
     * @return the name of the test.
     */
    public String getTestName() {
        return testName;
    }

    /**
     * Returns the score.
     *
     * @return the score achieved in the test.
     */
    public float getScore() {
        return score;
    }

    /**
     * Returns the weightage.
     *
     * @return the weightage of the test.
     */
    public float getWeightage() {
        return weightage;
    }

    @Override
    public String toString() {
        return testName + ": " + score + "% (Weightage: " + weightage + "%)";
    }

    @Override
    public boolean equals(Object other) {
        requireNonNull(other);
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
        return Objects.hash(testName, score, weightage);
    }
}
