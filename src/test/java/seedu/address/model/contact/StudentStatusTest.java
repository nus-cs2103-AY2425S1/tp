package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StudentStatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudentStatus(null));
    }

    @Test
    public void constructor_invalidStudentStatus_throwsIllegalArgumentException() {
        String invalidStudentStatus = "";
        assertThrows(IllegalArgumentException.class, () -> new StudentStatus(invalidStudentStatus));
    }

    @Test
    public void isValidStudentStatus() {
        // null student status
        assertThrows(NullPointerException.class, () -> StudentStatus.isValidStudentStatus(null));

        // invalid student statuses
        assertFalse(StudentStatus.isValidStudentStatus("phd 2")); // phd with year
        assertFalse(StudentStatus.isValidStudentStatus("undergraduate 10")); // year > 6

        // valid student statuses
        assertTrue(StudentStatus.isValidStudentStatus("undergraduate 3"));
        assertTrue(StudentStatus.isValidStudentStatus("phd"));
        assertTrue(StudentStatus.isValidStudentStatus("masters"));
    }

    @Test
    public void equals() {
        StudentStatus studentStatus = new StudentStatus("undergraduate 1");

        // same values -> returns true
        assertTrue(studentStatus.equals(new StudentStatus("undergraduate 1")));

        // same object -> returns true
        assertTrue(studentStatus.equals(studentStatus));

        // null -> returns false
        assertFalse(studentStatus.equals(null));

        // different types -> returns false
        assertFalse(studentStatus.equals(5.0f));

        // different values -> returns false
        assertFalse(studentStatus.equals(new StudentStatus("phd")));
    }
}
