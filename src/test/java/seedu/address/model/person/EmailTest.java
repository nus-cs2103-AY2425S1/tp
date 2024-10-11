package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EmailOld(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new EmailOld(invalidEmail));
    }

    @Test
    public void isValidEmail() {
        // null email
        assertThrows(NullPointerException.class, () -> EmailOld.isValidEmail(null));

        // blank email
        assertFalse(EmailOld.isValidEmail("")); // empty string
        assertFalse(EmailOld.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(EmailOld.isValidEmail("@example.com")); // missing local part
        assertFalse(EmailOld.isValidEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(EmailOld.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(EmailOld.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(EmailOld.isValidEmail("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(EmailOld.isValidEmail("peter jack@example.com")); // spaces in local part
        assertFalse(EmailOld.isValidEmail("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(EmailOld.isValidEmail(" peterjack@example.com")); // leading space
        assertFalse(EmailOld.isValidEmail("peterjack@example.com ")); // trailing space
        assertFalse(EmailOld.isValidEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(EmailOld.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(EmailOld.isValidEmail("-peterjack@example.com")); // local part starts with a hyphen
        assertFalse(EmailOld.isValidEmail("peterjack-@example.com")); // local part ends with a hyphen
        assertFalse(EmailOld.isValidEmail("peter..jack@example.com")); // local part has two consecutive periods
        assertFalse(EmailOld.isValidEmail("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(EmailOld.isValidEmail("peterjack@.example.com")); // domain name starts with a period
        assertFalse(EmailOld.isValidEmail("peterjack@example.com.")); // domain name ends with a period
        assertFalse(EmailOld.isValidEmail("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(EmailOld.isValidEmail("peterjack@example.com-")); // domain name ends with a hyphen
        assertFalse(EmailOld.isValidEmail("peterjack@example.c")); // top level domain has less than two chars

        // valid email
        assertTrue(EmailOld.isValidEmail("PeterJack_1190@example.com")); // underscore in local part
        assertTrue(EmailOld.isValidEmail("PeterJack.1190@example.com")); // period in local part
        assertTrue(EmailOld.isValidEmail("PeterJack+1190@example.com")); // '+' symbol in local part
        assertTrue(EmailOld.isValidEmail("PeterJack-1190@example.com")); // hyphen in local part
        assertTrue(EmailOld.isValidEmail("a@bc")); // minimal
        assertTrue(EmailOld.isValidEmail("test@localhost")); // alphabets only
        assertTrue(EmailOld.isValidEmail("123@145")); // numeric local part and domain name
        assertTrue(EmailOld.isValidEmail("a1+be.d@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(EmailOld.isValidEmail("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(EmailOld.isValidEmail("if.you.dream.it_you.can.do.it@example.com")); // long local part
        assertTrue(EmailOld.isValidEmail("e1234567@u.nus.edu")); // more than one period in domain
    }

    @Test
    public void equals() {
        EmailOld email = new EmailOld("valid@email");

        // same values -> returns true
        assertTrue(email.equals(new EmailOld("valid@email")));

        // same object -> returns true
        assertTrue(email.equals(email));

        // null -> returns false
        assertFalse(email.equals(null));

        // different types -> returns false
        assertFalse(email.equals(5.0f));

        // different values -> returns false
        assertFalse(email.equals(new EmailOld("other.valid@email")));
    }
}
