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
    public void constructor_validPhone_withSpaces() {
        Phone longPhoneWithSpaces = new Phone(" 93121534  ");
        assertTrue(longPhoneWithSpaces.equals(new Phone("93121534"))); // Should equal the trimmed version

        Phone mixedSpaces2 = new Phone("   98 76 54 32 ");
        assertTrue(mixedSpaces2.equals(new Phone("98765432")));
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
        assertFalse(Phone.isValidPhone("124293842033123")); // more than 8 digits

        // valid phone numbers
        assertTrue(Phone.isValidPhone("93121534")); //phone number starts with 9
        assertTrue(Phone.isValidPhone("83121534")); //phone number starts with 8
        assertTrue(Phone.isValidPhone("63121534")); //phone number starts with 6
        assertTrue(Phone.isValidPhone("33121534")); //phone number starts with 3
    }

    @Test
    public void equals() {
        Phone phone = new Phone("98765432");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("98765432")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("97654321")));
    }
}
