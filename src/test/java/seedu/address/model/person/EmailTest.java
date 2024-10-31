package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Email.makeEmail(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "Tom@Jerry.com";
        assertThrows(IllegalArgumentException.class, () -> Email.makeEmail(invalidEmail));
    }

    @Test
    public void isValidEmail() {
        // null email
        assertThrows(NullPointerException.class, () -> Email.isValidEmail(null));

        // blank email
        assertFalse(Email.isValidEmail("")); // empty string
        assertFalse(Email.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(Email.isValidEmail("@u.nus.edu")); // missing local part
        assertFalse(Email.isValidEmail("e1234567u.nus.edu")); // missing '@' symbol
        assertFalse(Email.isValidEmail("e1234567@")); // missing domain name

        // invalid parts
        assertFalse(Email.isValidEmail("e1234567@gmail.com")); // invalid domain name
        assertFalse(Email.isValidEmail("e123 4567@u.nus.edu")); // spaces in local part

        // valid email
        assertTrue(Email.isValidEmail("e1234567@u.nus.edu")); // lowercase in net Id
        assertTrue(Email.isValidEmail("E1234567@u.nus.edu")); // uppercase in net Id
    }

    @Test
    public void isValidNetId() {
        // null
        assertThrows(NullPointerException.class, () -> Email.isValidNetId(null));

        // blank
        assertFalse(Email.isValidNetId("")); // empty string
        assertFalse(Email.isValidNetId(" ")); // spaces only

        // missing parts
        assertFalse(Email.isValidNetId("e12345")); // less than 8 characters

        // invalid parts
        assertFalse(Email.isValidNetId("12345678")); // all numbers
        assertFalse(Email.isValidNetId("abcdefgh")); // all alphabets
        assertFalse(Email.isValidNetId("e123  4567")); // spaces within

        // valid Net Id
        assertTrue(Email.isValidNetId("e1234567")); // lowercase
        assertTrue(Email.isValidNetId("E1234567")); // uppercase
    }

    @Test
    public void equals() {
        Email email = Email.makeEmail("e1234567@u.nus.edu");

        // same values -> returns true
        assertTrue(email.equals(Email.makeEmail("e1234567@u.nus.edu")));

        // same object -> returns true
        assertTrue(email.equals(email));

        // null -> returns false
        assertFalse(email.equals(null));

        // different types -> returns false
        assertFalse(email.equals(5.0f));

        // different values -> returns false
        assertFalse(email.equals(Email.makeEmail("e9999999@u.nus.edu")));
    }
}
