package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone("   \t\n  ")); // whitespaces only
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("9234")); // less than 8 digits
        assertFalse(Phone.isValidPhone("623456789")); // more than 8 digits
        assertFalse(Phone.isValidPhone("82345 678")); // whitespace not separating phone number into halves
        assertFalse(Phone.isValidPhone("00000000")); // does not start with 6, 8, or 9
        assertFalse(Phone.isValidPhone("7918 2933")); // does not start with 6, 8, or 9

        // valid phone numbers
        assertTrue(Phone.isValidPhone("93121534")); // normal-looking mobile number
        assertTrue(Phone.isValidPhone("62429384")); // normal-looking landline number
        assertTrue(Phone.isValidPhone("9312 1534")); // one space between digits
        assertTrue(Phone.isValidPhone("9001     2003")); // multiple spaces between digits
        assertTrue(Phone.isValidPhone("9123\t4567")); // tab between digits
    }

    @Test
    public void equals() {
        Phone phone = new Phone("99990000");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("99990000")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("99501234")));
    }
}
