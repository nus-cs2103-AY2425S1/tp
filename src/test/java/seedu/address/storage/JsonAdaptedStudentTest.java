package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;

public class JsonAdaptedStudentTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_STUDENT_ID = "-1"; // Assuming negative IDs are invalid
    private static final String INVALID_TUTORIAL_CLASS = ""; // Assuming empty string is invalid
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_DATE_STRING = "31/02/2023";
    private static final JsonAdaptedTutDate INVALID_TUT_DATE = new JsonAdaptedTutDate(INVALID_DATE_STRING,
            null);
    private static final List<JsonAdaptedTutDate> INVALID_TUT_DATES = new ArrayList<>();

    static {
        INVALID_TUT_DATES.add(INVALID_TUT_DATE);
    }
    private static final JsonAdaptedPresentDates INVALID_PRESENT_DATES =
            new JsonAdaptedPresentDates(INVALID_TUT_DATES);

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_STUDENT_ID = "1001";
    private static final String VALID_TUTORIAL_CLASS = "1001";
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
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
                invalidName, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_PRESENT_DATES, VALID_STUDENT_ID, VALID_TUTORIAL_CLASS, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_PRESENT_DATES, VALID_STUDENT_ID, VALID_TUTORIAL_CLASS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_PRESENT_DATES, VALID_STUDENT_ID, VALID_TUTORIAL_CLASS, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_PRESENT_DATES, VALID_STUDENT_ID, VALID_TUTORIAL_CLASS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                        VALID_PRESENT_DATES, VALID_STUDENT_ID, VALID_TUTORIAL_CLASS, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_PRESENT_DATES, VALID_STUDENT_ID, VALID_TUTORIAL_CLASS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                        VALID_PRESENT_DATES, VALID_STUDENT_ID, VALID_TUTORIAL_CLASS, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_PRESENT_DATES, VALID_STUDENT_ID, VALID_TUTORIAL_CLASS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_PRESENT_DATES, VALID_STUDENT_ID, VALID_TUTORIAL_CLASS, invalidTags);
        assertThrows(IllegalValueException.class, student::toModelType);
    }

    // Add new tests for invalid and null StudentId and TutorialClass

    @Test
    public void toModelType_invalidPresentDates_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        INVALID_PRESENT_DATES, VALID_STUDENT_ID, VALID_TUTORIAL_CLASS, VALID_TAGS);
        assertThrows(IllegalValueException.class, student::toModelType);
    }

    @Test
    public void toModelType_nullPresentDates_returnsStudentWithEmptyPresentDates() throws Exception {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        null, VALID_STUDENT_ID, VALID_TUTORIAL_CLASS, VALID_TAGS);
        Student modelStudent = student.toModelType();
        assertEquals(0, modelStudent.getPresentDates().getList().size());
    }
}
