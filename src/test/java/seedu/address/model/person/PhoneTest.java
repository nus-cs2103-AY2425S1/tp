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
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("(+9999) 93121534")); // with country code too long
        assertFalse(Phone.isValidPhone("(+) 93121534")); // with blank country code
        assertFalse(Phone.isValidPhone("93  121534")); // with more than 1 space
        assertFalse(Phone.isValidPhone("93121534 [Office And Others]")); // with note > 10 chars
         // spaces within digits

        // valid phone numbers
        assertTrue(Phone.isValidPhone("91")); // short phone number
        assertTrue(Phone.isValidPhone("9312 1534")); // spaces allowed
        assertTrue(Phone.isValidPhone("911")); // exactly 3 numbers
        assertTrue(Phone.isValidPhone("(+999) 93121534")); // with country code length 3
        assertTrue(Phone.isValidPhone("93121534 [Office]")); // with note
        assertTrue(Phone.isValidPhone("(+999) 93121534 [Office]")); // with both country code and note
        assertTrue(Phone.isValidPhone("93121534 [Not Office]")); // with note = 10 chars
        assertTrue(Phone.isValidPhone("124293842033123")); // long phone numbers
    }

    @Test
    public void equals() {
        Phone phone = new Phone("999");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("999")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("995")));
    }
}
