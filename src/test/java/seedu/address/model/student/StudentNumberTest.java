package seedu.address.model.student;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class StudentNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudentNumber(null));
    }

    @Test
    public void constructor_invalidStudentNumber_throwsIllegalArgumentException() {
        String invalidStudentNumber = "";
        assertThrows(IllegalArgumentException.class, () -> new StudentNumber(invalidStudentNumber));
    }

    @Test
    public void isValidStudentNumber() {
        // null student number
        assertThrows(NullPointerException.class, () -> StudentNumber.isValidStudentNumber(null));

        // invalid student number
        assertFalse(StudentNumber.isValidStudentNumber("")); // empty string
        assertFalse(StudentNumber.isValidStudentNumber(" ")); // spaces only
        assertFalse(StudentNumber.isValidStudentNumber("91")); // less than 3 numbers
        assertFalse(StudentNumber.isValidStudentNumber("student")); // non-numeric
        assertFalse(StudentNumber.isValidStudentNumber("9011p041")); // alphabets within digits

        // valid student number
        assertTrue(StudentNumber.isValidStudentNumber("a1111111j")); // lowercase alphabets
        assertTrue(StudentNumber.isValidStudentNumber("A1111111J")); // uppercase alphabets
        assertTrue(StudentNumber.isValidStudentNumber("a1111111J")); // alphabets of both cases
    }

    @Test
    public void equals() {
        StudentNumber studentNumber = new StudentNumber("a1111111j");

        // same values -> returns true
        assertTrue(studentNumber.equals(new StudentNumber("a1111111j")));

        // same object -> returns true
        assertTrue(studentNumber.equals(studentNumber));

        // null -> returns false
        assertFalse(studentNumber.equals(null));

        // different types -> returns false
        assertFalse(studentNumber.equals(5.0f));

        // different values -> returns false
        assertFalse(studentNumber.equals(new StudentNumber("a1111112j")));

        // different case for alphabets -> returns true
        assertTrue(studentNumber.equals(new StudentNumber("A1111111J")));
        assertTrue(studentNumber.equals(new StudentNumber("A1111111j")));
        assertTrue(studentNumber.equals(new StudentNumber("a1111111J")));
    }
}
