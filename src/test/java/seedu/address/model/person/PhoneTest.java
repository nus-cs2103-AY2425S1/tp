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

        // invalid phone numbers (completely incorrect format)
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("phone")); // non-numeric

        // invalid phone numbers (incorrect number of digits)
        assertFalse(Phone.isValidPhone("91")); // less than 8 digits
        assertFalse(Phone.isValidPhone("124293842033123")); // more than 8 digits

        // invalid phone numbers (additional characters)
        assertFalse(Phone.isValidPhone(" 93123456 ")); // leading and trailing spaces
        assertFalse(Phone.isValidPhone("\t93123456\n")); // tabs and newlines
        assertFalse(Phone.isValidPhone("+6593121234")); // contains international prefix
        assertFalse(Phone.isValidPhone("９３１２１５３４")); // Unicode digits (full-width characters)
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("9312-1534")); // special character within digits
        assertFalse(Phone.isValidPhone("9312@1534")); // special character within digits
        assertFalse(Phone.isValidPhone("9312 1534")); // spaces within digits

        // invalid phone numbers (does not start with 6, 8 or 9)
        for (int i = 1; i <= 9; i++) {
            if (i != 6 && i != 8 && i != 9) {
                String invalidPhoneNumber = i + "2345678"; // Append i to create an (invalid) 8-digit number
                assertFalse(Phone.isValidPhone(invalidPhoneNumber),
                        "Phone number starting with " + i + " should be invalid");
            }
        }

        // valid phone numbers
        assertTrue(Phone.isValidPhone("69021534")); // exactly 8 digits, starts with 6
        assertTrue(Phone.isValidPhone("89012232")); // exactly 8 digits, starts with 8
        assertTrue(Phone.isValidPhone("93121534")); // exactly 8 digits, starts with 9
    }

    @Test
    public void equals() {
        Phone phone = new Phone("91234567");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("91234567")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("81112222")));
    }
}
