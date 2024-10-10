package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StudentClassTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudentClass(null));
    }

    @Test
    public void constructor_invalidStudentClass_throwsIllegalArgumentException() {
        String invalidStudentClass = "";
        assertThrows(IllegalArgumentException.class, () -> new StudentClass(invalidStudentClass));
    }

    @Test
    public void isValidStudentClass() {
        // null class
        assertThrows(NullPointerException.class, () -> StudentClass.isValidStudentClass(null));

        // invalid class
        assertFalse(StudentClass.isValidStudentClass("")); // empty string
        assertFalse(StudentClass.isValidStudentClass(" ")); // spaces only
        assertFalse(StudentClass.isValidStudentClass("^")); // only non-alphanumeric characters
        assertFalse(StudentClass.isValidStudentClass("1A*")); // contains non-alphanumeric characters
        assertFalse(StudentClass.isValidStudentClass("0A")); // digit is 0
        assertFalse(StudentClass.isValidStudentClass("1")); // only digit
        assertFalse(StudentClass.isValidStudentClass("A")); // only alphabet
        assertFalse(StudentClass.isValidStudentClass("A4")); // wrong order
        assertFalse(StudentClass.isValidStudentClass("12A")); // multiple digits
        assertFalse(StudentClass.isValidStudentClass("1AA")); // multiple alphabets

        // valid class
        assertTrue(StudentClass.isValidStudentClass("2C")); // digit followed by alphabet
        assertTrue(StudentClass.isValidStudentClass("5d")); // with lowercase
    }

    @Test
    public void equals() {
        StudentClass studentClass = new StudentClass("1A");

        // same values -> returns true
        assertTrue(studentClass.equals(new StudentClass("1A")));

        // same object -> returns true
        assertTrue(studentClass.equals(studentClass));

        // null -> returns false
        assertFalse(studentClass.equals(null));

        // different types -> returns false
        assertFalse(studentClass.equals(5.0f));

        // different values -> returns false
        assertFalse(studentClass.equals(new StudentClass("2A")));
    }
}
