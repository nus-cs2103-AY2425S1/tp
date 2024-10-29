package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.storage.JsonAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;

public class JsonAdaptedStudentTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_COURSE = "#CS2103T";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final List<JsonAdaptedCourse> VALID_COURSES = BENSON.getCourses().stream()
            .map(JsonAdaptedCourse::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validStudentDetails_returnsStudent() throws Exception {
        JsonAdaptedStudent student = new JsonAdaptedStudent(BENSON);
        assertEquals(BENSON, student.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COURSES);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(null, VALID_PHONE, VALID_EMAIL,
                VALID_COURSES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_COURSES);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, null, VALID_EMAIL,
                VALID_COURSES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_COURSES);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, null,
                VALID_COURSES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidCourses_throwsIllegalValueException() {
        List<JsonAdaptedCourse> invalidCourses = new ArrayList<>(VALID_COURSES);
        invalidCourses.add(new JsonAdaptedCourse(INVALID_COURSE));
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, invalidCourses);
        assertThrows(IllegalValueException.class, student::toModelType);
    }

    @Test
    public void isSameStudent_sameObject_returnsTrue() {
        JsonAdaptedStudent student1 = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COURSES);
        assertTrue(() -> student1.isSameStudent(student1));
    }

    @Test
    public void isSameStudent_hasSameName_returnsTrue() {
        JsonAdaptedStudent student1 = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COURSES);
        JsonAdaptedStudent student2 = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, List.of());
        assertTrue(() -> student1.isSameStudent(student2));
    }

    @Test
    public void isSameStudent_hasDifferentName_returnsFalse() {
        JsonAdaptedStudent student1 = new JsonAdaptedStudent("Alice", VALID_PHONE, VALID_EMAIL, VALID_COURSES);
        JsonAdaptedStudent student2 = new JsonAdaptedStudent("Benson", VALID_PHONE, VALID_EMAIL, VALID_COURSES);
        assertFalse(() -> student1.isSameStudent(student2));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        JsonAdaptedStudent student1 = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COURSES);
        JsonAdaptedStudent student2 = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COURSES);
        assertTrue(() -> student1.equals(student1));
    }

    @Test
    public void equals_hasSameDetails_returnsTrue() {
        JsonAdaptedStudent student1 = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COURSES);
        JsonAdaptedStudent student2 = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COURSES);
        assertTrue(() -> student1.equals(student2));
    }

    @Test
    public void equals_hasDifferentDetails_returnsFalse() {
        JsonAdaptedStudent student1 = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COURSES);
        JsonAdaptedStudent student2 = new JsonAdaptedStudent("NIQUEIGFUQE", VALID_PHONE, VALID_EMAIL, List.of());
        JsonAdaptedStudent student3 = new JsonAdaptedStudent(VALID_NAME, "10475629", VALID_EMAIL, List.of());
        JsonAdaptedStudent student4 = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, List.of());
        assertFalse(() -> student1.equals(student2));
        assertFalse(() -> student1.equals(student3));
        assertFalse(() -> student1.equals(student4));
    }

}
