package bizbook.model.person;

import static bizbook.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        // less than 8 numbers even though it starts with 9
        assertFalse(Phone.isValidPhone("91"));
        assertFalse(Phone.isValidPhone("9123456"));

        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("9312 1534")); // spaces within digits
        assertFalse(Phone.isValidPhone("12345678")); // exactly 8 numbers but does not start with 6, 8 or 9

        // valid phone numbers
        assertTrue(Phone.isValidPhone("91415691"));
        assertTrue(Phone.isValidPhone("93121534"));
    }

    @Test
    public void equals() {
        Phone phone = new Phone("91415691");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("91415691")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(8.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("91415693")));
    }
}
