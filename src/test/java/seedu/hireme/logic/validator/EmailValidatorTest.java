package seedu.hireme.logic.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EmailValidatorTest {
    @Test
    public void validate() {
        EmailValidator validator = EmailValidator.of();

        // null email
        assertThrows(NullPointerException.class, () -> validator.validate(null));

        // blank email
        assertFalse(validator.validate("")); // empty string
        assertFalse(validator.validate(" ")); // spaces only

        // missing parts
        assertFalse(validator.validate("@example.com")); // missing local part
        assertFalse(validator.validate("peterjackexample.com")); // missing '@' symbol
        assertFalse(validator.validate("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(validator.validate("peterjack@-")); // invalid domain name
        assertFalse(validator.validate("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(validator.validate("peter jack@example.com")); // spaces in local part
        assertFalse(validator.validate("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(validator.validate(" peterjack@example.com")); // leading space
        assertFalse(validator.validate("peterjack@example.com ")); // trailing space
        assertFalse(validator.validate("peterjack@@example.com")); // double '@' symbol
        assertFalse(validator.validate("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(validator.validate("-peterjack@example.com")); // local part starts with a hyphen
        assertFalse(validator.validate("peterjack-@example.com")); // local part ends with a hyphen
        assertFalse(validator.validate("peter..jack@example.com")); // local part has two consecutive periods
        assertFalse(validator.validate("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(validator.validate("peterjack@.example.com")); // domain name starts with a period
        assertFalse(validator.validate("peterjack@example.com.")); // domain name ends with a period
        assertFalse(validator.validate("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(validator.validate("peterjack@example.com-")); // domain name ends with a hyphen
        assertFalse(validator.validate("peterjack@example.c")); // top level domain has less than two chars

        // valid email
        assertTrue(validator.validate("PeterJack_1190@example.com")); // underscore in local part
        assertTrue(validator.validate("PeterJack.1190@example.com")); // period in local part
        assertTrue(validator.validate("PeterJack+1190@example.com")); // '+' symbol in local part
        assertTrue(validator.validate("PeterJack-1190@example.com")); // hyphen in local part
        assertTrue(validator.validate("a@g.bc")); // minimal
        assertTrue(validator.validate("123@145.67")); // numeric local part and domain name
        assertTrue(validator.validate("a1+be.d@example1.com")); // mix of alphanumeric and special char
        assertTrue(validator.validate("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(validator.validate("if.you.dream.it_you.can.do.it@example.com")); // long local part
        assertTrue(validator.validate("e1234567@u.nus.edu")); // more than one period in domain
    }
}
