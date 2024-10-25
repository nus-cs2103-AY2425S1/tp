package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.BENSON;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.Name;
import seedu.address.model.student.StudentId;
import seedu.address.model.student.TutorialId;

public class JsonAdaptedStudentTest {
    private static final String INVALID_STUDENT_ID = "-1"; // Assuming negative IDs are invalid
    private static final String INVALID_TUTORIAL_ID = ""; // Assuming empty string is invalid
    private static final String INVALID_DATE_STRING = "2023/02/31";
    private static final List<String> VALID_STUDENT_IDS = List.of("A1001000U", "A1002000U");
    public static final JsonAdaptedTutDate INVALID_TUT_DATE =
            new JsonAdaptedTutDate(INVALID_DATE_STRING, VALID_STUDENT_IDS);
    private static final List<JsonAdaptedTutDate> INVALID_TUT_DATES = new ArrayList<>();

    static {
        INVALID_TUT_DATES.add(INVALID_TUT_DATE);
    }
    private static final JsonAdaptedPresentDates INVALID_PRESENT_DATES =
            new JsonAdaptedPresentDates(INVALID_TUT_DATES);

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_STUDENT_ID = "A1001000U";
    private static final String VALID_TUTORIAL_ID = "T1001";
    private static final JsonAdaptedPresentDates VALID_PRESENT_DATES =
            new JsonAdaptedPresentDates(BENSON.getPresentDates());

    @Test
    public void toModelType_validStudentDetails_returnsStudent() throws Exception {
        JsonAdaptedStudent student = new JsonAdaptedStudent(BENSON);
        assertEquals(BENSON, student.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        // Assuming that the Name class does not allow names with special characters
        final String invalidName = "Rachel@123"; // This should be invalid if special characters are not allowed
        JsonAdaptedStudent student = new JsonAdaptedStudent(
                invalidName,
                VALID_PRESENT_DATES, VALID_STUDENT_ID, VALID_TUTORIAL_ID);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(null,
                VALID_PRESENT_DATES, VALID_STUDENT_ID, VALID_TUTORIAL_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidStudentId_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME,
                VALID_PRESENT_DATES, INVALID_STUDENT_ID, VALID_TUTORIAL_ID);
        String expectedMessage = StudentId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullStudentId_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME,
                VALID_PRESENT_DATES, null, VALID_TUTORIAL_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StudentId.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidTutorialId_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME,
                VALID_PRESENT_DATES, VALID_STUDENT_ID, INVALID_TUTORIAL_ID);
        String expectedMessage = TutorialId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullTutorialId_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME,
                VALID_PRESENT_DATES, VALID_STUDENT_ID, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TutorialId.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

}
