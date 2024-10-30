package seedu.hireme.logic.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameValidatorTest {
    @Test
    public void validate() {
        NameValidator validator = NameValidator.of();

        // null name
        assertThrows(NullPointerException.class, () -> validator.validate(null));

        // invalid name
        assertFalse(validator.validate("")); // empty string
        assertFalse(validator.validate(" ")); // spaces only
        assertFalse(validator.validate("^")); // only non-alphanumeric characters
        assertFalse(validator.validate("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(validator.validate("peter jack")); // alphabets only
        assertTrue(validator.validate("12345")); // numbers only
        assertTrue(validator.validate("peter the 2nd")); // alphanumeric characters
        assertTrue(validator.validate("Capital Tan")); // with capital letters
        assertTrue(validator.validate("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
