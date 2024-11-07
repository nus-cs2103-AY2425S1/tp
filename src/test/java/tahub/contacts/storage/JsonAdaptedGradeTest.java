package tahub.contacts.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import tahub.contacts.commons.exceptions.IllegalValueException;
import tahub.contacts.model.grade.Grade;

/**
 * Unit tests for {@link JsonAdaptedGrade}.
 */
public class JsonAdaptedGradeTest {

    private static final String VALID_ASSESSMENT_NAME = "Midterm";
    private static final double VALID_SCORE_PERCENTAGE = 85.0;
    private static final double VALID_WEIGHT = 0.4;

    private static final String INVALID_ASSESSMENT_NAME = "";
    private static final double INVALID_SCORE_PERCENTAGE = 101.0;
    private static final double INVALID_WEIGHT = 1.1;

    @Test
    public void toModelType_validGradeDetails_returnsGrade() throws Exception {
        JsonAdaptedGrade grade = new JsonAdaptedGrade(VALID_ASSESSMENT_NAME, VALID_SCORE_PERCENTAGE, VALID_WEIGHT);
        Grade expectedGrade = new Grade(VALID_ASSESSMENT_NAME, VALID_SCORE_PERCENTAGE, VALID_WEIGHT);
        assertEquals(expectedGrade, grade.toModelType());
    }

    @Test
    public void toModelType_invalidAssessmentName_throwsIllegalValueException() {
        JsonAdaptedGrade grade = new JsonAdaptedGrade(INVALID_ASSESSMENT_NAME, VALID_SCORE_PERCENTAGE, VALID_WEIGHT);
        assertThrows(IllegalValueException.class, grade::toModelType);
    }

    @Test
    public void toModelType_invalidScorePercentage_throwsIllegalValueException() {
        JsonAdaptedGrade grade = new JsonAdaptedGrade(VALID_ASSESSMENT_NAME, INVALID_SCORE_PERCENTAGE, VALID_WEIGHT);
        assertThrows(IllegalValueException.class, grade::toModelType);
    }

    @Test
    public void toModelType_invalidWeight_throwsIllegalValueException() {
        JsonAdaptedGrade grade = new JsonAdaptedGrade(VALID_ASSESSMENT_NAME, VALID_SCORE_PERCENTAGE, INVALID_WEIGHT);
        assertThrows(IllegalValueException.class, grade::toModelType);
    }
}
