package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudentId(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new StudentId(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> StudentId.isValidStudentId(null));

        // invalid phone numbers
        assertFalse(StudentId.isValidStudentId("")); // empty string
        assertFalse(StudentId.isValidStudentId(" ")); // spaces only
        assertFalse(StudentId.isValidStudentId("91")); // less than 3 numbers
        assertFalse(StudentId.isValidStudentId("phone")); // non-numeric
        assertFalse(StudentId.isValidStudentId("9011p041")); // alphabets within digits
        assertFalse(StudentId.isValidStudentId("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(StudentId.isValidStudentId("911")); // exactly 3 numbers
        assertTrue(StudentId.isValidStudentId("93121534"));
        assertTrue(StudentId.isValidStudentId("124293842033123")); // long phone numbers
    }

    @Test
    public void equals() {
        StudentId phone = new StudentId("999");

        // same values -> returns true
        assertTrue(phone.equals(new StudentId("999")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new StudentId("995")));
    }
}
