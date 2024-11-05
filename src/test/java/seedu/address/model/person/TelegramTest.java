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
        assertFalse(Telegram.isValidTelegram("")); // EP: empty string
        assertFalse(Telegram.isValidTelegram(" ")); // EP: space only
        assertFalse(Telegram.isValidTelegram("*")); // EP: non-alphanumeric or '_' character
        assertFalse(Telegram.isValidTelegram("two words")); // whitespace

        // valid telegrams
        assertTrue(Telegram.isValidTelegram("bruceWayne")); // EP: alphabetical characters
        assertTrue(Telegram.isValidTelegram("12345")); // EP: only numeric characters
        assertTrue(Telegram.isValidTelegram("a2b34C5")); // alphabetical + numeric characters
        assertTrue(Telegram.isValidTelegram("_test")); // '_' character
    }

    @Test
    public void isValidTelegramLength() {

        // invalid telegram lengths
        assertFalse(Telegram.isValidTelegramLength("")); // 0 length string
        assertFalse(Telegram.isValidTelegramLength("abcd")); // EP: < 5 length
        assertFalse(Telegram.isValidTelegramLength("abcabcabcabcabcabcabcabcabcabcabc")); // EP: > 32 length


        // valid telegram lengths
        assertTrue(Telegram.isValidTelegramLength("bruceWayne")); // EP: 5 <= length <= 32
        assertTrue(Telegram.isValidTelegramLength("abcde")); // Boundary: 5 length
        assertTrue(Telegram.isValidTelegramLength("abcabcabcabcabcabcabcabcabcabcab")); // Boundary: 32 length
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
