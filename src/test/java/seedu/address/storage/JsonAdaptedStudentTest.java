package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.HUGH;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.student.TutorialGroup;

public class JsonAdaptedStudentTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";

    private static final String INVALID_TUTORIAL_GROUP = "1234G";
    private static final String INVALID_STUDENT_NUMBER = "A0123456789GG";

    private static final String VALID_NAME = HUGH.getName().toString();
    private static final String VALID_PHONE = HUGH.getPhone().toString();
    private static final String VALID_EMAIL = HUGH.getEmail().toString();
    private static final String VALID_ADDRESS = HUGH.getAddress().toString();
    private static final String VALID_TUTORIAL_GROUP = HUGH.getTutorialGroup().toString();
    private static final String VALID_STUDENT_NUMBER = HUGH.getStudentNumber().toString();

    private static final List<JsonAdaptedAssignment> VALID_ASSIGNMENTS = HUGH.getAssignments().stream()
            .map(JsonAdaptedAssignment::new)
            .collect(Collectors.toList());


    @Test
    public void toModelType_validStudentDetails_returnsStudent() throws Exception {
        JsonAdaptedStudent student = new JsonAdaptedStudent(HUGH);
        assertEquals(HUGH, student.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_TUTORIAL_GROUP, VALID_STUDENT_NUMBER, VALID_ASSIGNMENTS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_TUTORIAL_GROUP, VALID_STUDENT_NUMBER, VALID_ASSIGNMENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_TUTORIAL_GROUP, VALID_STUDENT_NUMBER, VALID_ASSIGNMENTS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                        VALID_TUTORIAL_GROUP, VALID_STUDENT_NUMBER, VALID_ASSIGNMENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                        VALID_TUTORIAL_GROUP, VALID_STUDENT_NUMBER, VALID_ASSIGNMENTS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                        VALID_TUTORIAL_GROUP, VALID_STUDENT_NUMBER, VALID_ASSIGNMENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                        VALID_TUTORIAL_GROUP, VALID_STUDENT_NUMBER, VALID_ASSIGNMENTS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                        VALID_TUTORIAL_GROUP, VALID_STUDENT_NUMBER, VALID_ASSIGNMENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidTutorialGroup_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        INVALID_TUTORIAL_GROUP, VALID_STUDENT_NUMBER, VALID_ASSIGNMENTS);
        String expectedMessage = TutorialGroup.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullTutorialGroup_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        null, VALID_STUDENT_NUMBER, VALID_ASSIGNMENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TutorialGroup.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }



    @Test
    public void toModelType_invalidStudentNumber_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_TUTORIAL_GROUP, INVALID_STUDENT_NUMBER, VALID_ASSIGNMENTS);
        String expectedMessage = StudentNumber.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullStudentNumber_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_TUTORIAL_GROUP, null, VALID_ASSIGNMENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StudentNumber.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }


}
