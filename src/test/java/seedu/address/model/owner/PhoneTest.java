package seedu.address.model.owner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Phone;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new seedu.address.model.person.Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new seedu.address.model.person.Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> seedu.address.model.person.Phone.isValidPhone(null));

        // invalid phone numbers
        assertFalse(seedu.address.model.person.Phone.isValidPhone("")); // empty string
        assertFalse(seedu.address.model.person.Phone.isValidPhone(" ")); // spaces only
        assertFalse(seedu.address.model.person.Phone.isValidPhone("91")); // less than 3 numbers
        assertFalse(seedu.address.model.person.Phone.isValidPhone("phone")); // non-numeric
        assertFalse(seedu.address.model.person.Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(seedu.address.model.person.Phone.isValidPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(seedu.address.model.person.Phone.isValidPhone("911")); // exactly 3 numbers
        assertTrue(seedu.address.model.person.Phone.isValidPhone("93121534"));
        assertTrue(seedu.address.model.person.Phone.isValidPhone("124293842033123")); // long phone numbers
    }

    @Test
    public void equals() {
        seedu.address.model.person.Phone phone = new seedu.address.model.person.Phone("999");

        // same values -> returns true
        assertTrue(phone.equals(new seedu.address.model.person.Phone("999")));

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
