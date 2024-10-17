package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Grade;

public class JsonAdaptedGradeTest {
    private static final Grade VALID_GRADE = new Grade("Midterm", 76F, 30F);
    private static final String INVALID_NAME = "Midterm@1";
    private static final float INVALID_SCORE = -1F;
    private static final float INVALID_WEIGHTAGE = 101F;

    private static final String VALID_NAME = "Midterm";
    private static final float VALID_SCORE = 76F;
    private static final float VALID_WEIGHTAGE = 30F;


    @Test
    public void toModelType_validGradeDetails_returnGrade() throws IllegalValueException {
        JsonAdaptedGrade grade = new JsonAdaptedGrade(VALID_GRADE);
        assertEquals(VALID_GRADE, grade.toModelType());
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedGrade grade = new JsonAdaptedGrade(null, VALID_SCORE, VALID_WEIGHTAGE);
        String expectedMessage = String.format(JsonAdaptedGrade.MISSING_FIELD_MESSAGE_FORMAT, "Test Name");
        assertThrows(IllegalValueException.class, expectedMessage, grade::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedGrade grade = new JsonAdaptedGrade(INVALID_NAME, VALID_SCORE, VALID_WEIGHTAGE);
        String expectedMessage = JsonAdaptedGrade.MESSAGE_TEST_NAME_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, grade::toModelType);
    }

    @Test
    public void toModelType_invalidScore_throwsIllegalValueException() {
        JsonAdaptedGrade grade = new JsonAdaptedGrade(VALID_NAME, INVALID_SCORE, VALID_WEIGHTAGE);
        String expectedMessage = JsonAdaptedGrade.MESSAGE_GRADE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, grade::toModelType);
    }

    @Test
    public void toModelType_invalidWeightage_throwsIllegalValueException() {
        JsonAdaptedGrade grade = new JsonAdaptedGrade(VALID_NAME, VALID_SCORE, INVALID_WEIGHTAGE);
        String expectedMessage = JsonAdaptedGrade.MESSAGE_WEIGHTAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, grade::toModelType);
    }

}
