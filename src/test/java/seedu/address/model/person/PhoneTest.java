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
        assertFalse(Phone.isValidPhone("91")); // less than 3 numbers
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("-934")); // Hyphen at the start
        assertFalse(Phone.isValidPhone("934-")); // Hyphen at the end
        assertFalse(Phone.isValidPhone("934--999")); // Double hyphen between digits
        assertFalse(Phone.isValidPhone("934  999")); // Double space between digits
        assertFalse(Name.isValidName("999- 934")); // Hyphen and space between 2 characters
        assertFalse(Phone.isValidPhone("++9344 8293")); // Two pluses
        assertFalse(Phone.isValidPhone("+9344+8293")); // Plus in the middle of the phone
        assertFalse(Phone.isValidPhone("344+-8293"));
        assertFalse(Phone.isValidPhone("344 +8293"));
        assertFalse(Phone.isValidPhone("+")); // Just a plus


        // valid phone numbers
        assertTrue(Phone.isValidPhone("911")); // exactly 3 numbers
        assertTrue(Phone.isValidPhone("93121534"));
        assertTrue(Phone.isValidPhone("124293842033123")); // long phone numbers
        assertTrue(Phone.isValidPhone("999 999")); // Space allowed between numbers
        assertTrue(Phone.isValidPhone("999-999")); // Hyphen allowed between numbers
        assertTrue(Phone.isValidPhone("92-9")); // Phone number with at least 3 digits in total
        assertTrue(Phone.isValidPhone("987 983-834")); // Number with space and hyphen
        assertTrue(Phone.isValidPhone("987 983-834  ")); // Number with trailing whitespaces
        assertTrue(Phone.isValidPhone("+65 9344 8293")); // Number with country code
        assertTrue(Phone.isValidPhone("+6593448293")); // Country code but no spaces
        assertTrue(Phone.isValidPhone("+65 9344-8293")); // Mix of hyphens, spaces and country code
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
