package seedu.academyassist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.academyassist.testutil.Assert.assertThrows;

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
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("91")); // less than 8 numbers
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("9312 1534")); // spaces within digits
        assertFalse(Phone.isValidPhone("123")); // 3 digits
        assertFalse(Phone.isValidPhone("+6512345678")); // number with symbols
        assertFalse(Phone.isValidPhone("123456789123456789123")); // more than 20 numbers

        // valid phone numbers
        assertTrue(Phone.isValidPhone("93121534")); // 8 digits
        assertTrue(Phone.isValidPhone("1234")); // 4 digits
        assertTrue(Phone.isValidPhone("12345678912345678912")); // 20 digits
        assertTrue(Phone.isValidPhone("00000000")); // numbers all the same
    }

    @Test
    public void equals() {
        Phone phone = new Phone("12345678");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("12345678")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("87654321")));
    }
}
