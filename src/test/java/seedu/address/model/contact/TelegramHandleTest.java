package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TelegramHandleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TelegramHandle(null));
    }

    @Test
    public void constructor_invalidTelegramHandle_throwsIllegalArgumentException() {
        String invalidTelegramHandle = "";
        assertThrows(IllegalArgumentException.class, () -> new TelegramHandle(invalidTelegramHandle));
    }

    @Test
    public void isValidTelegramHandle() {
        // null telegram Handle
        assertThrows(NullPointerException.class, () -> TelegramHandle.isValidTelegramHandle(null));

        // invalid telegram Handles
        assertFalse(TelegramHandle.isValidTelegramHandle("")); // empty string
        assertFalse(TelegramHandle.isValidTelegramHandle(" ")); // spaces only
        assertFalse(TelegramHandle.isValidTelegramHandle("91")); // less than 5 characters
        assertFalse(TelegramHandle.isValidTelegramHandle("9312 1534")); // spaces within digits
        assertFalse(TelegramHandle.isValidTelegramHandle("@93121534")); // contains @

        // valid telegrams
        assertTrue(TelegramHandle.isValidTelegramHandle("911_P")); // exactly 5 characters
        // exactly 32 characters
        assertTrue(TelegramHandle.isValidTelegramHandle("somethingIsNotRightHelp_12345678"));
        assertTrue(TelegramHandle.isValidTelegramHandle("124293842033123")); // long Telegram handle
        assertTrue(TelegramHandle.isValidTelegramHandle("phone")); // non-numeric
        assertTrue(TelegramHandle.isValidTelegramHandle("9011p041")); // alphabets within digits
    }

    @Test
    public void equals() {
        TelegramHandle telegramHandle = new TelegramHandle("Tele9");

        // same values -> returns true
        assertTrue(telegramHandle.equals(new TelegramHandle("Tele9")));

        // same object -> returns true
        assertTrue(telegramHandle.equals(telegramHandle));

        // null -> returns false
        assertFalse(telegramHandle.equals(null));

        // different types -> returns false
        assertFalse(telegramHandle.equals(5.0f));

        // different values -> returns false
        assertFalse(telegramHandle.equals(new TelegramHandle("995k1")));
    }
}
