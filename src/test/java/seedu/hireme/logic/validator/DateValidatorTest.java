package seedu.hireme.logic.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DateValidatorTest {
    @Test
    public void validate() {
        DateValidator validator = DateValidator.of();

        // invalid date
        assertFalse(validator.validate("")); // empty string
        assertFalse(validator.validate(" ")); // spaces only
        assertFalse(validator.validate("///")); // backslashes only
        assertFalse(validator.validate("120524")); // numbers only
        assertFalse(validator.validate("12052024")); // numbers only with 4 digit year
        assertFalse(validator.validate("12/05/2024")); // invalid year format
        assertFalse(validator.validate("12\05\2024")); // forward slashes
        assertFalse(validator.validate("12/50/24")); // invalid month
        assertFalse(validator.validate("-1/05/24")); // invalid day
        assertFalse(validator.validate("12?05?24")); // invalid special characters
        assertFalse(validator.validate("12/05/30")); // future date

        // valid date
        assertTrue(validator.validate("12/05/24")); // valid date
        assertTrue(validator.validate("29/11/19")); // numbers only

    }
}
