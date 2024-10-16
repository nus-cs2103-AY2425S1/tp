package keycontacts.storage;

import static keycontacts.storage.JsonAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT;
import static keycontacts.testutil.Assert.assertThrows;
import static keycontacts.testutil.TypicalStudents.BENSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import keycontacts.commons.exceptions.IllegalValueException;
import keycontacts.model.student.Address;
import keycontacts.model.student.GradeLevel;
import keycontacts.model.student.Name;
import keycontacts.model.student.Phone;

public class JsonAdaptedStudentTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_GRADE_LEVEL = "R0";
    private static final String INVALID_PIANO_PIECE = " ";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_GRADE_LEVEL = BENSON.getGradeLevel().toString();
    private static final List<JsonAdaptedPianoPiece> VALID_PIANO_PIECES = BENSON.getPianoPieces().stream()
            .map(JsonAdaptedPianoPiece::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedCancelledLesson> VALID_CANCELLED_LESSONS = BENSON.getCancelledLessons()
            .stream()
            .map(JsonAdaptedCancelledLesson::new)
            .toList();

    private static final JsonAdaptedRegularLesson EMPTY_REGULAR_LESSON = null;

    @Test
    public void toModelType_validStudentDetails_returnsStudent() throws Exception {
        JsonAdaptedStudent student = new JsonAdaptedStudent(BENSON);
        assertEquals(BENSON, student.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(INVALID_NAME, VALID_PHONE, VALID_ADDRESS, VALID_GRADE_LEVEL, VALID_PIANO_PIECES,
                        EMPTY_REGULAR_LESSON, VALID_CANCELLED_LESSONS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(null, VALID_PHONE, VALID_ADDRESS, VALID_GRADE_LEVEL,
                VALID_PIANO_PIECES, EMPTY_REGULAR_LESSON, VALID_CANCELLED_LESSONS);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, INVALID_PHONE, VALID_ADDRESS, VALID_GRADE_LEVEL, VALID_PIANO_PIECES,
                        EMPTY_REGULAR_LESSON, VALID_CANCELLED_LESSONS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, null, VALID_ADDRESS, VALID_GRADE_LEVEL,
                VALID_PIANO_PIECES, EMPTY_REGULAR_LESSON, VALID_CANCELLED_LESSONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, INVALID_ADDRESS, VALID_GRADE_LEVEL, VALID_PIANO_PIECES,
                        EMPTY_REGULAR_LESSON, VALID_CANCELLED_LESSONS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidGradeLevel_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_ADDRESS, INVALID_GRADE_LEVEL, VALID_PIANO_PIECES,
                        EMPTY_REGULAR_LESSON);
        String expectedMessage = GradeLevel.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullGradeLevel_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_ADDRESS, null,
                VALID_PIANO_PIECES, EMPTY_REGULAR_LESSON);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, GradeLevel.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, null, VALID_GRADE_LEVEL,
                VALID_PIANO_PIECES, EMPTY_REGULAR_LESSON, VALID_CANCELLED_LESSONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }


    @Test
    public void toModelType_invalidPianoPieces_throwsIllegalValueException() {
        List<JsonAdaptedPianoPiece> invalidPianoPieces = new ArrayList<>(VALID_PIANO_PIECES);
        invalidPianoPieces.add(new JsonAdaptedPianoPiece(INVALID_PIANO_PIECE));
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_ADDRESS, VALID_GRADE_LEVEL,
                invalidPianoPieces, EMPTY_REGULAR_LESSON, VALID_CANCELLED_LESSONS);
        assertThrows(IllegalValueException.class, student::toModelType);
    }
}
