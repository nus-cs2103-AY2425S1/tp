package hallpointer.address.model.member;

import static hallpointer.address.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        // invalid telegrams: length & character set
        assertFalse(Telegram.isValidTelegram("")); // empty string
        assertFalse(Telegram.isValidTelegram("eeee")); // less than 5 chars
        assertFalse(Telegram.isValidTelegram("r456e&")); // non-underscore symbols
        assertFalse(Telegram.isValidTelegram("ben ten")); // spaces
        assertFalse(Telegram.isValidTelegram("cccccdddddeeeeefffffggggghhhhhiii")); // more than 33 characters
        assertFalse(Telegram.isValidTelegram("a_a_a_a_a_a_a_a_a_a_a_a_a_a_a_a_a_")); // certain regexes fail this

        // invalid telegrams: starting and ending requirements
        assertFalse(Telegram.isValidTelegram("_aaaaaa")); // start cannot be underscores
        assertFalse(Telegram.isValidTelegram("1aaaaaa")); // start cannot be numberic
        assertTrue(Telegram.isValidTelegram("tota1")); // end cannot be numeric


        // valid telegrams
        assertTrue(Telegram.isValidTelegram("AAAAA")); // exactly 5 chars, just caps
        assertTrue(Telegram.isValidTelegram("bbbbbbbb")); // just lowercase
        assertTrue(Telegram.isValidTelegram("A22W_f_wfe3")); // numbers and underscores ok
        assertTrue(Telegram.isValidTelegram("A_____A")); // consecutive underscores ok
        assertTrue(Telegram.isValidTelegram("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ")); // exactly 32 chars
        assertTrue(Telegram.isValidTelegram("Ra_a_a_a_a_a_a_a_a_a_a_a_a_a_a_a")); // certain regexes may fail this too
    }

    @Test
    public void equals() {
        Telegram telegram = new Telegram("vegetable");

        // same values -> returns true
        assertTrue(telegram.equals(new Telegram("vegetable")));

        // same object -> returns true
        assertTrue(telegram.equals(telegram));

        // null -> returns false
        assertFalse(telegram.equals(null));

        // different types -> returns false
        assertFalse(telegram.equals(5.0f));

        // different values -> returns false
        assertFalse(telegram.equals(new Telegram("banana")));
    }
}
