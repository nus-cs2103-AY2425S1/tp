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
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidStudentId));
    }

    @Test
    public void isValidStudentId() {
        // null Student ID
        assertThrows(NullPointerException.class, () -> StudentId.isValidStudentId(null));

        // invalid Student IDs
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("51")); // less than 8 digits
        assertFalse(Phone.isValidPhone("123456789")); // more than 8 digits
        assertFalse(Phone.isValidPhone("studentid")); // non-numeric
        assertFalse(Phone.isValidPhone("1e34t678")); // alphabets within digits
        assertFalse(Phone.isValidPhone("1234 5678")); // spaces within digits

        // valid phone numbers
        assertTrue(Phone.isValidPhone("3219692")); // exactly 8 numbers
    }

    @Test
    public void equals() {
        StudentId studentId = new StudentId("12345678");

        // same values -> returns true
        assertTrue(studentId.equals(new Phone("12345678")));

        // same object -> returns true
        assertTrue(studentId.equals(studentId));

        // null -> returns false
        assertFalse(studentId.equals(null));

        // different types -> returns false
        assertFalse(studentId.equals(5.0f));

        // different values -> returns false
        assertFalse(studentId.equals(new StudentId("87654321")));
    }
}
