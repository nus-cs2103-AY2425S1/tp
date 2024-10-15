package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTALCODE_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTALCODE_BEDOK;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PostalCodeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PostalCode(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidCode = "";
        assertThrows(IllegalArgumentException.class, () -> new PostalCode(invalidCode));
    }

    @Test
    public void isValidPostalCode() {
        // null name
        assertThrows(NullPointerException.class, () -> PostalCode.isValidPostalCode(null));

        // invalid name
        assertFalse(PostalCode.isValidPostalCode("")); // empty string
        assertFalse(PostalCode.isValidPostalCode(" ")); // spaces only
        assertFalse(PostalCode.isValidPostalCode("^")); // only non-alphanumeric characters
        assertFalse(PostalCode.isValidPostalCode("-00000")); // contains non-alphanumeric characters
        assertFalse(PostalCode.isValidPostalCode("00&000")); // contains non-alphanumeric characters
        assertFalse(PostalCode.isValidPostalCode("00.000")); // contains decimal characters
        assertFalse(PostalCode.isValidPostalCode("00000")); // contains less than 6 character
        assertFalse(PostalCode.isValidPostalCode("0000000")); // contains more than 6 character
        assertFalse(PostalCode.isValidPostalCode("-000001")); // negative number

        // valid name
        assertTrue(PostalCode.isValidPostalCode("000000")); // alphabets only
        assertTrue(PostalCode.isValidPostalCode("123456"));
        assertTrue(PostalCode.isValidPostalCode("999999"));
    }

    @Test
    public void equals() {
        PostalCode postalCode = new PostalCode(VALID_POSTALCODE_BEDOK);

        // same values -> returns true
        assertTrue(postalCode.equals(new PostalCode(VALID_POSTALCODE_BEDOK)));

        // same object -> returns true
        assertTrue(postalCode.equals(postalCode));

        // null -> returns false
        assertFalse(postalCode.equals(null));

        // different types -> returns false
        assertFalse(postalCode.equals(5.0f));

        // different values -> returns false
        assertFalse(postalCode.equals(new PostalCode(VALID_POSTALCODE_ADMIRALTY)));
    }
}
