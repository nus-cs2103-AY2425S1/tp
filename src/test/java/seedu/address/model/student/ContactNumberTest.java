package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ContactNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ContactNumber(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new ContactNumber(invalidPhone));
    }

    @Test
    public void isValidContact() {
        // null phone number
        assertThrows(NullPointerException.class, () -> ContactNumber.isValidContact(null));

        // invalid phone numbers
        assertFalse(ContactNumber.isValidContact("")); // empty string
        assertFalse(ContactNumber.isValidContact(" ")); // spaces only
        assertFalse(ContactNumber.isValidContact("91")); // less than 3 numbers
        assertFalse(ContactNumber.isValidContact("phone")); // non-numeric
        assertFalse(ContactNumber.isValidContact("9011p041")); // alphabets within digits
        // assertFalse(ContactNumber.isValidContact("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(ContactNumber.isValidContact("911")); // exactly 3 numbers
        assertTrue(ContactNumber.isValidContact("93121534"));
        assertTrue(ContactNumber.isValidContact("124293842033123")); // long phone numbers
    }

    @Test
    public void equals() {
        ContactNumber contact = new ContactNumber("999");

        // same values -> returns true
        assertTrue(contact.equals(new ContactNumber("999")));

        // same object -> returns true
        assertTrue(contact.equals(contact));

        // null -> returns false
        assertFalse(contact.equals(null));

        // different types -> returns false
        assertFalse(contact.equals(5.0f));

        // different values -> returns false
        assertFalse(contact.equals(new ContactNumber("995")));
    }
}
