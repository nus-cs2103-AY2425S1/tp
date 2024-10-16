package seedu.address.model.internshipapplication;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.validator.EmailValidator;

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
    public void validate() {
        // null email
        assertThrows(NullPointerException.class, () -> EmailValidator.of().validate(null));

        // blank email
        assertFalse(EmailValidator.of().validate("")); // empty string
        assertFalse(EmailValidator.of().validate(" ")); // spaces only

        // missing parts
        assertFalse(EmailValidator.of().validate("@example.com")); // missing local part
        assertFalse(EmailValidator.of().validate("peterjackexample.com")); // missing '@' symbol
        assertFalse(EmailValidator.of().validate("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(EmailValidator.of().validate("peterjack@-")); // invalid domain name
        assertFalse(EmailValidator.of().validate("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(EmailValidator.of().validate("peter jack@example.com")); // spaces in local part
        assertFalse(EmailValidator.of().validate("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(EmailValidator.of().validate(" peterjack@example.com")); // leading space
        assertFalse(EmailValidator.of().validate("peterjack@example.com ")); // trailing space
        assertFalse(EmailValidator.of().validate("peterjack@@example.com")); // double '@' symbol
        assertFalse(EmailValidator.of().validate("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(EmailValidator.of().validate("-peterjack@example.com")); // local part starts with a hyphen
        assertFalse(EmailValidator.of().validate("peterjack-@example.com")); // local part ends with a hyphen
        assertFalse(EmailValidator.of().validate("peter..jack@example.com")); // local part has two consecutive periods
        assertFalse(EmailValidator.of().validate("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(EmailValidator.of().validate("peterjack@.example.com")); // domain name starts with a period
        assertFalse(EmailValidator.of().validate("peterjack@example.com.")); // domain name ends with a period
        assertFalse(EmailValidator.of().validate("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(EmailValidator.of().validate("peterjack@example.com-")); // domain name ends with a hyphen
        assertFalse(EmailValidator.of().validate("peterjack@example.c")); // top level domain has less than two chars

        // valid email
        assertTrue(EmailValidator.of().validate("PeterJack_1190@example.com")); // underscore in local part
        assertTrue(EmailValidator.of().validate("PeterJack.1190@example.com")); // period in local part
        assertTrue(EmailValidator.of().validate("PeterJack+1190@example.com")); // '+' symbol in local part
        assertTrue(EmailValidator.of().validate("PeterJack-1190@example.com")); // hyphen in local part
        assertTrue(EmailValidator.of().validate("a@g.bc")); // minimal
        assertTrue(EmailValidator.of().validate("123@145.67")); // numeric local part and domain name
        assertTrue(EmailValidator.of().validate("a1+be.d@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(EmailValidator.of().validate("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(EmailValidator.of().validate("if.you.dream.it_you.can.do.it@example.com")); // long local part
        assertTrue(EmailValidator.of().validate("e1234567@u.nus.edu")); // more than one period in domain
    }

    @Test
    public void equals() {
        Email email = new Email("valid@email.com");

        // same values -> returns true
        assertTrue(email.equals(new Email("valid@email.com")));

        // same object -> returns true
        assertTrue(email.equals(email));

        // null -> returns false
        assertFalse(email.equals(null));

        // different types -> returns false
        assertFalse(email.equals(5.0f));

        // different values -> returns false
        assertFalse(email.equals(new Email("other.valid@email.com")));
    }
}
