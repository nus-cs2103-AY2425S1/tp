package tutorease.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_STUDENT_ID_CHAR;
import static tutorease.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import tutorease.address.logic.parser.exceptions.ParseException;

public class StudentIdTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudentId(null));
    }

    @Test
    public void constructor_invalidStudentId_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new StudentId(INVALID_STUDENT_ID_CHAR));
        assertThrows(IllegalArgumentException.class, () -> new StudentId(""));
    }
    @Test
    public void isValidStudentId() {
        // null student id
        assertThrows(NullPointerException.class, () -> StudentId.isValidStudentId(null));

        // invalid student id
        assertFalse(StudentId.isValidStudentId(""));
        assertFalse(StudentId.isValidStudentId(" "));
        assertFalse(StudentId.isValidStudentId("0"));
        assertFalse(StudentId.isValidStudentId("-1"));
        assertFalse(StudentId.isValidStudentId("1.1"));
    }

    @Test
    public void getValue() throws ParseException {
        StudentId studentId = new StudentId("1");
        assertNotEquals(2, studentId.getValue());
        assertNotEquals(1.0, studentId.getValue());
        assertEquals(0, studentId.getValue());
    }
    @Test
    public void equals() throws ParseException {
        StudentId studentId = new StudentId("1");

        assertTrue(studentId.equals(new StudentId("1")));
        assertTrue(studentId.equals(studentId));
        assertFalse(studentId.equals(null));
        assertFalse(studentId.equals(5.0f));
        assertFalse(studentId.equals(new StudentId("2")));
    }
    @Test
    public void toStringTest() throws ParseException {
        StudentId studentId = new StudentId("1");
        assertEquals("1", studentId.toString());
    }
}
