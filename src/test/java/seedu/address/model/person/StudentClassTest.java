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

        // invalid classes
        // EP: empty strings
        assertFalse(StudentClass.isValidStudentClass("")); // empty string
        assertFalse(StudentClass.isValidStudentClass(" ")); // spaces only

        // EP: wrong length of input
        assertFalse(StudentClass.isValidStudentClass("1")); // only digit
        assertFalse(StudentClass.isValidStudentClass("A")); // only alphabet
        assertFalse(StudentClass.isValidStudentClass("12A")); // multiple digits
        assertFalse(StudentClass.isValidStudentClass("1AA")); // multiple alphabets

        // EP: non-alphanumeric characters
        assertFalse(StudentClass.isValidStudentClass("^")); // only non-alphanumeric characters
        assertFalse(StudentClass.isValidStudentClass("1*")); // contains non-alphanumeric characters

        // EP: zero followed by a capital alphabet
        assertFalse(StudentClass.isValidStudentClass("0A")); // Boundary value

        // EP: non-zero digit followed by lowercase alphabet
        assertFalse(StudentClass.isValidStudentClass("5d"));

        // EP: capital alphabet followed by digit
        assertFalse(StudentClass.isValidStudentClass("A4"));

        // valid classes
        // EP: non-zero digit followed by a capital alphabet
        assertTrue(StudentClass.isValidStudentClass("1A")); // Boundary value
        assertTrue(StudentClass.isValidStudentClass("9Z")); // Boundary value
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
