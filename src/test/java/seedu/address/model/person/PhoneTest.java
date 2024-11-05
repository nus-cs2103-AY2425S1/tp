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
        assertFalse(Phone.isValidPhone("")); // EP: empty string
        assertFalse(Phone.isValidPhone(" ")); // EP: spaces only
        assertFalse(Phone.isValidPhone("phone")); // EP: non-numeric
        assertFalse(Phone.isValidPhone("9312 1534")); // EP: spaces within digits
        assertFalse(Phone.isValidPhone("924293842033123")); // EP: more than 8 numbers
        assertFalse(Phone.isValidPhone("91")); // EP: less than 8 numbers
        assertFalse(Phone.isValidPhone("51234567")); // EP: number not starting with 3, 6, 8, or 9
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits


        // valid phone numbers
        assertTrue(Phone.isValidPhone("93121534")); // EP: exactly 8 numbers, starting with 9
        assertTrue(Phone.isValidPhone("83121534")); // EP: exactly 8 numbers, starting with 8
        assertTrue(Phone.isValidPhone("63121534")); // EP: exactly 8 numbers, starting with 6
        assertTrue(Phone.isValidPhone("33121534")); // EP: exactly 8 numbers, starting with 3
    }

    @Test
    public void equals() {
        Phone phone = new Phone("99999999");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("99999999")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("99999995")));
    }
}
