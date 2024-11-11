package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PADDED_LARGE_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PADDED_OVERFLOW_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_UNPADDED_LARGE_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_UNPADDED_OVERFLOW_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASK_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BID_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LARGEST_PADDED_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LARGEST_UNPADDED_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MATCHINGPRICE_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MATCHINGPRICE_BEDOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PADDED_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SMALLEST_PADDED_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SMALLEST_UNPADDED_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNPADDED_PRICE;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MatchingPriceTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MatchingPrice(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidCode = "";
        assertThrows(IllegalArgumentException.class, () -> new MatchingPrice(invalidCode));
    }

    @Test
    public void getMatchingPrice() {
        assertEquals(Integer.valueOf(VALID_MATCHINGPRICE_ADMIRALTY), MatchingPrice.getMatchingPrice(
                new Ask(VALID_ASK_ADMIRALTY), new Bid(VALID_BID_ADMIRALTY)
        ));
    }

    @Test
    public void toInteger() {
        assertEquals(Integer.valueOf(VALID_MATCHINGPRICE_ADMIRALTY),
                new MatchingPrice(VALID_MATCHINGPRICE_ADMIRALTY).toInteger());
    }

    @Test
    public void isValidMatchingPrice() {
        // null name
        assertThrows(NullPointerException.class, () -> MatchingPrice.isValidMatchingPrice(null));

        // invalid name
        assertFalse(MatchingPrice.isValidMatchingPrice("")); // empty string
        assertFalse(MatchingPrice.isValidMatchingPrice(" ")); // spaces only
        assertFalse(MatchingPrice.isValidMatchingPrice("^")); // only non-alphanumeric characters
        assertFalse(MatchingPrice.isValidMatchingPrice("-00000")); // contains non-alphanumeric characters
        assertFalse(MatchingPrice.isValidMatchingPrice("00&000")); // contains non-alphanumeric characters
        assertFalse(MatchingPrice.isValidMatchingPrice("00.000")); // contains decimal characters
        assertFalse(MatchingPrice.isValidMatchingPrice("00000 ")); // contains space
        assertFalse(MatchingPrice.isValidMatchingPrice("000 0000")); // contains space delimiter
        assertFalse(MatchingPrice.isValidMatchingPrice("$1000000")); // contains dollar character
        assertFalse(MatchingPrice.isValidMatchingPrice(INVALID_UNPADDED_OVERFLOW_PRICE)); // Integer Overflow
        assertFalse(MatchingPrice.isValidMatchingPrice(INVALID_PADDED_OVERFLOW_PRICE)); // Integer Overflow
        assertFalse(MatchingPrice.isValidMatchingPrice(INVALID_UNPADDED_LARGE_PRICE)); // Large price edge
        assertFalse(MatchingPrice.isValidMatchingPrice(INVALID_PADDED_LARGE_PRICE)); // Large price padded edge

        // valid name
        assertTrue(MatchingPrice.isValidMatchingPrice("000000")); // alphabets only
        assertTrue(MatchingPrice.isValidMatchingPrice("123456"));
        assertTrue(MatchingPrice.isValidMatchingPrice("999999"));
    }

    @Test
    public void equals() {
        MatchingPrice matchingPrice = new MatchingPrice(VALID_MATCHINGPRICE_BEDOK);

        // same values -> returns true
        assertTrue(matchingPrice.equals(new MatchingPrice(VALID_MATCHINGPRICE_BEDOK)));

        // same object -> returns true
        assertTrue(matchingPrice.equals(matchingPrice));

        // null -> returns false
        assertFalse(matchingPrice.equals(null));

        // different types -> returns false
        assertFalse(matchingPrice.equals(5.0f));

        // different values -> returns false
        assertFalse(matchingPrice.equals(new MatchingPrice(VALID_MATCHINGPRICE_ADMIRALTY)));

        // padded and non-padded values are the same
        assertTrue(new MatchingPrice(VALID_PADDED_PRICE).equals(new MatchingPrice(VALID_UNPADDED_PRICE)));

        // padded and non-padded values are the same at largest number boundry
        assertTrue(new MatchingPrice(VALID_LARGEST_PADDED_PRICE)
                .equals(new MatchingPrice(VALID_LARGEST_UNPADDED_PRICE)));

        // padded and non-padded values are the same at smallest number boundry
        assertTrue(new MatchingPrice(VALID_SMALLEST_PADDED_PRICE)
                .equals(new MatchingPrice(VALID_SMALLEST_UNPADDED_PRICE)));
    }
}
