package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AskingPriceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AskingPrice(null));
    }

    @Test
    public void constructor_invalidAskingPrice_throwsIllegalArgumentException() {
        String invalidAskingPrice = "";
        assertThrows(IllegalArgumentException.class, () -> new AskingPrice(invalidAskingPrice));
    }

    @Test
    public void isValidAskingPrice() {
        // null phone number
        assertThrows(NullPointerException.class, () -> AskingPrice.isValidPrice(null));

        // invalid prices
        assertFalse(AskingPrice.isValidPrice("")); // empty string
        assertFalse(AskingPrice.isValidPrice(" ")); // spaces only
        assertFalse(AskingPrice.isValidPrice("91")); // less than 3 numbers
        assertFalse(AskingPrice.isValidPrice("phone")); // non-numeric
        assertFalse(AskingPrice.isValidPrice("9011p041")); // alphabets within digits
        assertFalse(AskingPrice.isValidPrice("9312 1534")); // spaces within digits

        // valid prices
        assertTrue(AskingPrice.isValidPrice("911")); // exactly 3 numbers
        assertTrue(AskingPrice.isValidPrice("93121534"));
        assertTrue(AskingPrice.isValidPrice("124293842033123")); // long phone numbers
    }

    @Test
    public void equals() {
        AskingPrice phone = new AskingPrice("999");

        // same values -> returns true
        assertTrue(phone.equals(new AskingPrice("999")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new AskingPrice("995")));
    }
}
