package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TelegramTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Telegram(null));
    }

    @Test
    public void constructor_invalidTelegram_throwsIllegalArgumentException() {
        String invalidTelegram = "test test";
        assertThrows(IllegalArgumentException.class, () -> new Telegram(invalidTelegram));
    }

    @Test
    public void isValidTelegram() {
        // null address
        assertThrows(NullPointerException.class, () -> Telegram.isValidTelegram(null));

        // invalid telegrams
        assertFalse(Telegram.isValidTelegram("")); // empty string
        assertFalse(Telegram.isValidTelegram(" ")); // spaces only
        assertFalse(Telegram.isValidTelegram("two words")); // whitespace
        assertFalse(Telegram.isValidTelegram("*")); // not alphanumeric or '_' character

        // valid telegrams
        assertTrue(Telegram.isValidTelegram("bruceWayne")); // alphabetical characters
        assertTrue(Telegram.isValidTelegram("a2b34C5")); // numeric characters
        assertTrue(Telegram.isValidTelegram("_test")); // '_' character
    }

    @Test
    public void equals() {
        Telegram telegram = new Telegram("ValidTelegram");

        // same values -> returns true
        assertTrue(telegram.equals(new Telegram("ValidTelegram")));

        // same object -> returns true
        assertTrue(telegram.equals(telegram));

        // null -> returns false
        assertFalse(telegram.equals(null));

        // different types -> returns false
        assertFalse(telegram.equals(5.0f));

        // different values -> returns false
        assertFalse(telegram.equals(new Telegram("OtherValidTelegram")));
    }
}
