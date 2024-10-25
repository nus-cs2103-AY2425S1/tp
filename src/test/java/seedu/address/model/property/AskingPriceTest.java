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
        // null asking price
        assertThrows(NullPointerException.class, () -> AskingPrice.isValidPrice(null));

        // invalid asking price
        assertFalse(AskingPrice.isValidPrice("-100,000")); // negative number
        assertFalse(AskingPrice.isValidPrice("abc")); // not a number
        assertFalse(AskingPrice.isValidPrice("ab100")); // contain number and alphabets
        assertFalse(AskingPrice.isValidPrice("100.00")); // not an integer
        assertFalse(AskingPrice.isValidPrice("1000,000")); // haphazard placement of commas


        // valid asking price
        assertTrue(AskingPrice.isValidPrice("1000000")); // no commas
        assertTrue(AskingPrice.isValidPrice("1,000,000")); // commas
    }

    @Test
    public void equals() {
        AskingPrice askingPrice = new AskingPrice("999");

        // same values -> returns true
        assertTrue(askingPrice.equals(new AskingPrice("999")));

        // same object -> returns true
        assertTrue(askingPrice.equals(askingPrice));

        // null -> returns false
        assertFalse(askingPrice.equals(null));

        // different types -> returns false
        assertFalse(askingPrice.equals(5.0f));

        // different values -> returns false
        assertFalse(askingPrice.equals(new AskingPrice("995")));
    }
}
