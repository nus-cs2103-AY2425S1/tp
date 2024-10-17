package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Grade;

/**
 * Jackson-friendly version of {@link Grade}.
 */
class JsonAdaptedGrade {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Grade's %s field is missing!";
    public static final String MESSAGE_GRADE_CONSTRAINTS =
            "Grades should be numeric values between 0 and 100 (in percentage), including decimals.";
    public static final String MESSAGE_TEST_NAME_CONSTRAINTS =
            "Test names should be at least 3 characters long and "
                    + "contain only alphanumeric characters, spaces, hyphens (-), or underscores (_).";
    public static final String MESSAGE_WEIGHTAGE_CONSTRAINTS =
            "Weightage should be a numeric value between 0 (exclusive) and 100 (inclusive).";

    private final String testName;
    private final float score;
    private final float weightage;

    /**
     * Constructs a {@code JsonAdaptedGrade} with the given grade details.
     */
    @JsonCreator
    public JsonAdaptedGrade(@JsonProperty("testName") String testName,
                            @JsonProperty("score") float score,
                            @JsonProperty("weightage") float weightage) {
        this.testName = testName;
        this.score = score;
        this.weightage = weightage;
    }

    /**
     * Converts a given {@code Grade} into this class for Jackson use.
     */
    public JsonAdaptedGrade(Grade source) {
        testName = source.getTestName();
        score = source.getScore();
        weightage = source.getWeightage();
    }

    /**
     * Converts this Jackson-friendly adapted grade object into the model's {@code Grade} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted grade.
     */
    public Grade toModelType() throws IllegalValueException {
        if (testName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Test Name"));
        }
        if (!Grade.isValidTestName(testName)) {
            throw new IllegalValueException(MESSAGE_TEST_NAME_CONSTRAINTS);
        }
        if (!Grade.isValidGrade(score)) {
            throw new IllegalValueException(MESSAGE_GRADE_CONSTRAINTS);
        }
        if (!Grade.isValidWeightage(weightage)) {
            throw new IllegalValueException(MESSAGE_WEIGHTAGE_CONSTRAINTS);
        }
        return new Grade(testName, score, weightage);
    }
}
