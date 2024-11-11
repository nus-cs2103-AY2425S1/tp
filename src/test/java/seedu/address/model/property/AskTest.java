package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PADDED_LARGE_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PADDED_OVERFLOW_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_UNPADDED_LARGE_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_UNPADDED_OVERFLOW_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASK_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASK_BEDOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LARGEST_PADDED_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LARGEST_UNPADDED_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PADDED_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SMALLEST_PADDED_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SMALLEST_UNPADDED_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNPADDED_PRICE;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AskTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Ask(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidCode = "";
        assertThrows(IllegalArgumentException.class, () -> new Ask(invalidCode));
    }

    @Test
    public void isValidAsk() {
        // null name
        assertThrows(NullPointerException.class, () -> Ask.isValidAsk(null));

        // invalid name
        assertFalse(Ask.isValidAsk("")); // empty string
        assertFalse(Ask.isValidAsk(" ")); // spaces only
        assertFalse(Ask.isValidAsk("^")); // only non-alphanumeric characters
        assertFalse(Ask.isValidAsk("-00000")); // contains non-alphanumeric characters
        assertFalse(Ask.isValidAsk("00&000")); // contains non-alphanumeric characters
        assertFalse(Ask.isValidAsk("00.000")); // contains decimal characters
        assertFalse(Ask.isValidAsk("00000 ")); // contains space
        assertFalse(Ask.isValidAsk("000 0000")); // contains space delimiter
        assertFalse(Ask.isValidAsk("$1000000")); // contains dollar character
        assertFalse(Ask.isValidAsk(INVALID_UNPADDED_OVERFLOW_PRICE)); // Integer Overflow
        assertFalse(Ask.isValidAsk(INVALID_PADDED_OVERFLOW_PRICE)); // Integer Overflow
        assertFalse(Ask.isValidAsk(INVALID_UNPADDED_LARGE_PRICE)); // Large price edge
        assertFalse(Ask.isValidAsk(INVALID_PADDED_LARGE_PRICE)); // Large price padded edge

        // valid name
        assertTrue(Ask.isValidAsk("000000")); // alphabets only
        assertTrue(Ask.isValidAsk("123456"));
        assertTrue(Ask.isValidAsk("999999"));
    }

    @Test
    public void equals() {
        Ask ask = new Ask(VALID_ASK_BEDOK);

        // same values -> returns true
        assertTrue(ask.equals(new Ask(VALID_ASK_BEDOK)));

        // same object -> returns true
        assertTrue(ask.equals(ask));

        // null -> returns false
        assertFalse(ask.equals(null));

        // different types -> returns false
        assertFalse(ask.equals(5.0f));

        // different values -> returns false
        assertFalse(ask.equals(new Ask(VALID_ASK_ADMIRALTY)));

        // padded and non-padded values are the same
        assertTrue(new Ask(VALID_PADDED_PRICE).equals(new Ask(VALID_UNPADDED_PRICE)));

        // padded and non-padded values are the same at largest number boundry
        assertTrue(new Ask(VALID_LARGEST_PADDED_PRICE).equals(new Ask(VALID_LARGEST_UNPADDED_PRICE)));

        // padded and non-padded values are the same at smallest number boundry
        assertTrue(new Ask(VALID_SMALLEST_PADDED_PRICE).equals(new Ask(VALID_SMALLEST_UNPADDED_PRICE)));
    }
}
