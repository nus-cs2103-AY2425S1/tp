package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class StudentIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudentId(null));
    }

    @Test
    public void constructor_invalidStudentId_throwsIllegalArgumentException() {
        String invalidStudentId = "";
        assertThrows(IllegalArgumentException.class, () -> new StudentId(invalidStudentId));
    }

    @Test
    public void isValidStudentId() {
        // null
        assertThrows(NullPointerException.class, () -> StudentId.isValidStudentId(null));

        // invalid studentId
        assertFalse(StudentId.isValidStudentId("")); // empty string
        assertFalse(StudentId.isValidStudentId("   ")); // spaces only
        assertFalse(StudentId.isValidStudentId("91B51")); // less than 9 characters
        assertFalse(StudentId.isValidStudentId("123456789")); // all numbers
        assertFalse(StudentId.isValidStudentId("abcdefghi")); // all alphabets
        assertFalse(StudentId.isValidStudentId("A312 1534L")); // spaces inside

        // valid studentIds
        assertTrue(StudentId.isValidStudentId("A1234567L")); // correct format
        assertTrue(StudentId.isValidStudentId("a5555555e"));
    }

    @Test
    public void equals() {
        StudentId studentId = new StudentId("A1234567E");

        // same values -> returns true
        assertTrue(studentId.equals(new StudentId("A1234567E")));

        // same object -> returns true
        assertTrue(studentId.equals(studentId));

        // null -> returns false
        assertFalse(studentId.equals(null));

        // different types -> returns false
        assertFalse(studentId.equals(5.0f));

        // different values -> returns false
        assertFalse(studentId.equals(new StudentId("A1000000E")));
    }
}
