package seedu.hireme.logic.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StatusValidatorTest {
    @Test
    public void validate() {
        StatusValidator validator = StatusValidator.of();

        // null status
        assertThrows(NullPointerException.class, () -> validator.validate(null));

        // invalid status
        assertFalse(validator.validate("")); // empty string
        assertFalse(validator.validate(" ")); // spaces only
        assertFalse(validator.validate("^")); // only non-alphanumeric characters
        assertFalse(validator.validate("aa*??")); // contains non-alphanumeric characters
        assertFalse(validator.validate("test")); // contains alphanumeric characters

        // valid status
        assertTrue(validator.validate("PENDING")); // pending uppercase
        assertTrue(validator.validate("pending")); // pending lowercase
        assertTrue(validator.validate("peNdiNg")); // pending mixed case
        assertTrue(validator.validate("ACCEPTED")); // accepted uppercase
        assertTrue(validator.validate("accepted")); // accepted lowercase
        assertTrue(validator.validate("ACCepted")); // accepted mixed case
        assertTrue(validator.validate("REJECTED")); // rejected uppercase
        assertTrue(validator.validate("rejected")); // rejected lowercase
        assertTrue(validator.validate("reJEctEd")); // rejected mixed case
    }
}
