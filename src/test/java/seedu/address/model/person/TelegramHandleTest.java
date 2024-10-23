package seedu.address.model.person;

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
        // null telegram handle
        assertThrows(NullPointerException.class, () -> TelegramHandle.isValidTelegramHandle(null));

        // blank telegram handle
        assertFalse(TelegramHandle.isValidTelegramHandle("")); // empty string
        assertFalse(TelegramHandle.isValidTelegramHandle(" ")); // spaces only

        // missing parts
        assertFalse(TelegramHandle.isValidTelegramHandle("peterjack")); // missing '@' symbol

        // invalid parts
        assertFalse(TelegramHandle.isValidTelegramHandle("@peterjack!")); // contains invalid character
        assertFalse(TelegramHandle.isValidTelegramHandle("@peter jack")); // spaces in telegram handle
        assertFalse(TelegramHandle.isValidTelegramHandle(" @peterjack")); // leading space
        assertFalse(TelegramHandle.isValidTelegramHandle("@peterjack ")); // trailing space
        assertFalse(TelegramHandle.isValidTelegramHandle("@@peterjack")); // double '@' symbol
        assertFalse(TelegramHandle.isValidTelegramHandle("@peter@jack")); // '@' symbol in telegram handle
        assertFalse(TelegramHandle.isValidTelegramHandle("@pete")); // telegram handle has less than five chars
        assertFalse(TelegramHandle.isValidTelegramHandle(
                "@peterjackpeterjackpeterjack123456789")); // telegram handle has more than 32 chars

        // valid telegram handle
        assertTrue(TelegramHandle.isValidTelegramHandle("@peter_jack")); // underscore in telegram handle
        assertTrue(TelegramHandle.isValidTelegramHandle("@peterjack123")); // numbers in telegram handle
        assertTrue(TelegramHandle.isValidTelegramHandle("@peterJack")); // uppercase characters in telegram handle
        assertTrue(TelegramHandle.isValidTelegramHandle("@peterjack")); // alphabets only
        assertTrue(TelegramHandle.isValidTelegramHandle("@12345")); // numbers only
    }

    @Test
    public void equals() {
        TelegramHandle telegramHandle = new TelegramHandle("@validTeleHandle");

        // same values -> returns true
        assertTrue(telegramHandle.equals(new TelegramHandle("@validTeleHandle")));

        // same object -> returns true
        assertTrue(telegramHandle.equals(telegramHandle));

        // null -> returns false
        assertFalse(telegramHandle.equals(null));

        // different types -> returns false
        assertFalse(telegramHandle.equals(5.0f));

        // different values -> returns false
        assertFalse(telegramHandle.equals(new TelegramHandle("@otherValidTeleHandle")));
    }
}
