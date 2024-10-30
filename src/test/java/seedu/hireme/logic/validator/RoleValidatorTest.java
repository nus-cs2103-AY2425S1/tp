package seedu.hireme.logic.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleValidatorTest {
    @Test
    public void validate() {
        RoleValidator validator = RoleValidator.of();

        // null role
        assertThrows(NullPointerException.class, () -> validator.validate(null));

        // invalid role
        assertFalse(validator.validate("")); // empty string
        assertFalse(validator.validate(" ")); // spaces only
        assertFalse(validator.validate("^")); // only non-alphanumeric characters
        assertFalse(validator.validate("swe*")); // contains non-alphanumeric characters

        // valid role
        assertTrue(validator.validate("backend intern")); // alphabets only
        assertTrue(validator.validate("12345")); // numbers only
        assertTrue(validator.validate("2nd backend intern")); // alphanumeric characters
        assertTrue(validator.validate("Backend Intern")); // with capital letters
        assertTrue(validator.validate("Front office desk operations intern")); // long roles
    }
}
