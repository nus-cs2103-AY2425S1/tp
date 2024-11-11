package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PADDED_LARGE_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PADDED_OVERFLOW_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_UNPADDED_LARGE_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_UNPADDED_OVERFLOW_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BID_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BID_BEDOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LARGEST_PADDED_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LARGEST_UNPADDED_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PADDED_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SMALLEST_PADDED_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SMALLEST_UNPADDED_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNPADDED_PRICE;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BidTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Bid(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidCode = "";
        assertThrows(IllegalArgumentException.class, () -> new Bid(invalidCode));
    }

    @Test
    public void isValidBid() {
        // null name
        assertThrows(NullPointerException.class, () -> Bid.isValidBid(null));

        // invalid name
        assertFalse(Bid.isValidBid("")); // empty string
        assertFalse(Bid.isValidBid(" ")); // spaces only
        assertFalse(Bid.isValidBid("^")); // only non-alphanumeric characters
        assertFalse(Bid.isValidBid("-00000")); // contains non-alphanumeric characters
        assertFalse(Bid.isValidBid("00&000")); // contains non-alphanumeric characters
        assertFalse(Bid.isValidBid("00.000")); // contains decimal characters
        assertFalse(Bid.isValidBid("00000 ")); // contains space
        assertFalse(Bid.isValidBid("000 0000")); // contains space delimiter
        assertFalse(Bid.isValidBid("$1000000")); // contains dollar character
        assertFalse(Bid.isValidBid(INVALID_UNPADDED_OVERFLOW_PRICE)); // Integer Overflow
        assertFalse(Bid.isValidBid(INVALID_PADDED_OVERFLOW_PRICE)); // Integer Overflow
        assertFalse(Bid.isValidBid(INVALID_UNPADDED_LARGE_PRICE)); // Large price edge
        assertFalse(Bid.isValidBid(INVALID_PADDED_LARGE_PRICE)); // Large price padded edge

        // valid name
        assertTrue(Bid.isValidBid("000000")); // alphabets only
        assertTrue(Bid.isValidBid("123456"));
        assertTrue(Bid.isValidBid("999999"));
    }

    @Test
    public void equals() {
        Bid bid = new Bid(VALID_BID_BEDOK);

        // same values -> returns true
        assertTrue(bid.equals(new Bid(VALID_BID_BEDOK)));

        // same object -> returns true
        assertTrue(bid.equals(bid));

        // null -> returns false
        assertFalse(bid.equals(null));

        // different types -> returns false
        assertFalse(bid.equals(5.0f));

        // different values -> returns false
        assertFalse(bid.equals(new Bid(VALID_BID_ADMIRALTY)));

        // padded and non-padded values are the same
        assertTrue(new Bid(VALID_PADDED_PRICE).equals(new Bid(VALID_UNPADDED_PRICE)));

        // padded and non-padded values are the same at largest number boundry
        assertTrue(new Bid(VALID_LARGEST_PADDED_PRICE).equals(new Bid(VALID_LARGEST_UNPADDED_PRICE)));

        // padded and non-padded values are the same at smallest number boundry
        assertTrue(new Bid(VALID_SMALLEST_PADDED_PRICE).equals(new Bid(VALID_SMALLEST_UNPADDED_PRICE)));
    }
}
