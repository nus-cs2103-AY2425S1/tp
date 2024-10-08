package hallpointer.address.model.member;

import static hallpointer.address.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        // invalid telegram handles
        assertFalse(TelegramHandle.isValidTelegramHandle("")); // empty string
        assertFalse(TelegramHandle.isValidTelegramHandle(" ")); // spaces only
        assertFalse(TelegramHandle.isValidTelegramHandle("91")); // less than 3 numbers
        assertFalse(TelegramHandle.isValidTelegramHandle("telegram handle")); // non-numeric
        assertFalse(TelegramHandle.isValidTelegramHandle("9011p041")); // alphabets within digits
        assertFalse(TelegramHandle.isValidTelegramHandle("9312 1534")); // spaces within digits

        // valid telegram handles
        assertTrue(TelegramHandle.isValidTelegramHandle("911")); // exactly 3 numbers
        assertTrue(TelegramHandle.isValidTelegramHandle("93121534"));
        assertTrue(TelegramHandle.isValidTelegramHandle("124293842033123")); // long telegram handles
    }

    @Test
    public void equals() {
        TelegramHandle telegramHandle = new TelegramHandle("999");

        // same values -> returns true
        assertTrue(telegramHandle.equals(new TelegramHandle("999")));

        // same object -> returns true
        assertTrue(telegramHandle.equals(telegramHandle));

        // null -> returns false
        assertFalse(telegramHandle.equals(null));

        // different types -> returns false
        assertFalse(telegramHandle.equals(5.0f));

        // different values -> returns false
        assertFalse(telegramHandle.equals(new TelegramHandle("995")));
    }
}
