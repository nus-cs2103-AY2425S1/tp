package seedu.address.model.volunteer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EmailTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Email(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmail));
    }

    @Test
    public void isValidEmail() {
        // null email
        assertThrows(NullPointerException.class, () -> Email.isValidEmail(null));

        // blank email
        assertFalse(Email.isValidEmail("")); // empty string
        assertFalse(Email.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(Email.isValidEmail("@example.com")); // missing local part
        assertFalse(Email.isValidEmail("alicewongexample.com")); // missing '@' symbol
        assertFalse(Email.isValidEmail("alicewong@")); // missing domain name

        // invalid parts
        assertFalse(Email.isValidEmail("alicewong@-")); // invalid domain name
        assertFalse(Email.isValidEmail("alicewong@exam_ple.com")); // underscore in domain name
        assertFalse(Email.isValidEmail("alice wong@example.com")); // spaces in local part
        assertFalse(Email.isValidEmail("alicewong@exam ple.com")); // spaces in domain name
        assertFalse(Email.isValidEmail(" alicewong@example.com")); // leading space
        assertFalse(Email.isValidEmail("alicewong@example.com ")); // trailing space
        assertFalse(Email.isValidEmail("alicewong@@example.com")); // double '@' symbol
        assertFalse(Email.isValidEmail("alice@wong@example.com")); // '@' symbol in local part
        assertFalse(Email.isValidEmail("-alicewong@example.com")); // local part starts with a hyphen
        assertFalse(Email.isValidEmail("alicewong-@example.com")); // local part ends with a hyphen
        assertFalse(Email.isValidEmail("alice..wong@example.com")); // local part has two consecutive periods
        assertFalse(Email.isValidEmail("alicewong@example@com")); // '@' symbol in domain name
        assertFalse(Email.isValidEmail("alicewong@.example.com")); // domain name starts with a period
        assertFalse(Email.isValidEmail("alicewong@example.com.")); // domain name ends with a period
        assertFalse(Email.isValidEmail("alicewong@-example.com")); // domain name starts with a hyphen
        assertFalse(Email.isValidEmail("alicewong@example.com-")); // domain name ends with a hyphen
        assertFalse(Email.isValidEmail("alicewong@example.c")); // top level domain has less than two chars

        // valid email
        assertTrue(Email.isValidEmail("AliceWong_1190@example.com")); // underscore in local part
        assertTrue(Email.isValidEmail("AliceWong.1190@example.com")); // period in local part
        assertTrue(Email.isValidEmail("AliceWong+1190@example.com")); // '+' symbol in local part
        assertTrue(Email.isValidEmail("AliceWong-1190@example.com")); // hyphen in local part
        assertTrue(Email.isValidEmail("a@bc")); // minimal
        assertTrue(Email.isValidEmail("test@localhost")); // alphabets only
        assertTrue(Email.isValidEmail("123@145")); // numeric local part and domain name
        assertTrue(Email.isValidEmail("a1+be.d@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Email.isValidEmail("alice_wong@very-very-very-long-example.com")); // long domain name
        assertTrue(Email.isValidEmail("if.you.dream.it_you.can.do.it@example.com")); // long local part
        assertTrue(Email.isValidEmail("e1234567@u.nus.edu")); // more than one period in domain
    }

    @Test
    public void equals() {
        Email email = new Email("valid@email");

        // same values -> returns true
        assertTrue(email.equals(new Email("valid@email")));

        // same object -> returns true
        assertTrue(email.equals(email));

        // null -> returns false
        assertFalse(email.equals(null));

        // different types -> returns false
        assertFalse(email.equals(5.0f));

        // different values -> returns false
        assertFalse(email.equals(new Email("other.valid@email")));
    }

    @Test
    public void hashCode_sameEmail_sameHashCode() {
        Email email1 = new Email("alice@example.com");
        Email email2 = new Email("alice@example.com");

        assertTrue(email1.equals(email2));
        assertTrue(email1.hashCode() == email2.hashCode());
    }

    @Test
    public void hashCode_differentEmails_differentHashCode() {
        Email email1 = new Email("alice@example.com");
        Email email2 = new Email("bob@example.com");

        assertFalse(email1.equals(email2));
        assertFalse(email1.hashCode() == email2.hashCode());
    }
}
