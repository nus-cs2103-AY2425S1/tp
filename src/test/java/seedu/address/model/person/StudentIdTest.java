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
        String invalidStudentId = "A1234567";
        assertThrows(IllegalArgumentException.class, () -> new StudentId(invalidStudentId));
    }

    @Test
    public void isValidStudentId() {
        // null student ID
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid student ID
        assertFalse(StudentId.isValidStudentId(""));
        assertFalse(StudentId.isValidStudentId(" "));
        assertFalse(StudentId.isValidStudentId("E000")); // Too short of student ID
        assertFalse(StudentId.isValidStudentId("E12345678")); // Too long of student ID
        assertFalse(StudentId.isValidStudentId("E123456a")); // Correct length, contains letters
        assertFalse(StudentId.isValidStudentId("A1234567")); // Correct lenght, incorrect starting letter

        // valid student ID
        assertTrue(StudentId.isValidStudentId("E1234567"));
        assertTrue(StudentId.isValidStudentId("E0982341"));
    }
}
