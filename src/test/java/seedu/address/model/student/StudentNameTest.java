package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StudentNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudentName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new StudentName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> StudentName.isValidName(null));

        // invalid name
        assertFalse(StudentName.isValidName("")); // empty string
        assertFalse(StudentName.isValidName(" ")); // spaces only
        assertFalse(StudentName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(StudentName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(StudentName.isValidName("peter jack")); // alphabets only
        assertTrue(StudentName.isValidName("12345")); // numbers only
        assertTrue(StudentName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(StudentName.isValidName("Capital Tan")); // with capital letters
        assertTrue(StudentName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void equals() {
        StudentName name = new StudentName("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new StudentName("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new StudentName("Other Valid Name")));
    }
}
