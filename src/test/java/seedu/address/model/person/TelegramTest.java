package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        String invalidTelegram = "";
        assertThrows(IllegalArgumentException.class, () -> new Telegram(invalidTelegram));
    }

    @Test
    public void isValidTelegram() {
        // null telegram
        assertThrows(NullPointerException.class, () -> Telegram.isValidTelegram(null));

        // invalid telegram IDs
        assertFalse(Telegram.isValidTelegram("")); // empty string
        assertFalse(Telegram.isValidTelegram(" ")); // spaces only
        assertFalse(Telegram.isValidTelegram("-")); // one character
        assertFalse(Telegram.isValidTelegram("Test")); // the ID does not start with @
        assertFalse(Telegram.isValidTelegram("@Test!!!")); // contains invalid char '!'
        assertFalse(Telegram.isValidTelegram("@")); // only contains '@' which is not allowed


        // valid telegram IDs
        assertTrue(Telegram.isValidTelegram("@test"));
        assertTrue(Telegram.isValidTelegram("@1234"));
        assertTrue(Telegram.isValidTelegram("@test123"));
    }

    @Test
    public void equals() {
        Telegram telegram = new Telegram("@ValidTelegram");

        // same values -> returns true
        assertTrue(telegram.equals(new Telegram("@ValidTelegram")));

        // same object -> returns true
        assertTrue(telegram.equals(telegram));

        // null -> returns false
        assertFalse(telegram.equals(null));

        // different types -> returns false
        assertFalse(telegram.equals(5.0f));

        // different values -> returns false
        assertFalse(telegram.equals(new Telegram("@OtherValidTelegram")));
    }

    @Test
    public void compareTo() {
        Telegram telegram = new Telegram("@Alice");
        Telegram otherTelegram = new Telegram("@Bob");

        // null input
        assertThrows(NullPointerException.class, () -> telegram.compareTo(null));

        // valid input
        assertTrue(telegram.compareTo(otherTelegram) < 0);
        assertTrue(otherTelegram.compareTo(telegram) > 0);

        // same input
        assertEquals(0, telegram.compareTo(telegram));
    }
}
