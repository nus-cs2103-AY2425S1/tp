package seedu.address.model.buyer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.buyer.BuyerBuilder;

public class BuyerFulfilsPredicateTest {

    @Test
    public void equals() {
        BuyerFulfilsPredicate firstPredicate = new BuyerFulfilsPredicate("buyer");
        BuyerFulfilsPredicate secondPredicate = new BuyerFulfilsPredicate("");
        BuyerFulfilsPredicate thirdPredicate = new BuyerFulfilsPredicate("filler");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        BuyerFulfilsPredicate firstPredicateCopy = new BuyerFulfilsPredicate("buyer");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different values -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
        assertFalse(firstPredicate.equals(thirdPredicate));
    }

    @Test
    public void test_buyerFulfilsPredicate_returnsTrue() {
        // valid buyer-type
        BuyerFulfilsPredicate predicate = new BuyerFulfilsPredicate("buyer");
        assertTrue(predicate.test(new BuyerBuilder().build()));

        // no buyer-type
        predicate = new BuyerFulfilsPredicate("");
        assertTrue(predicate.test(new BuyerBuilder().build()));
    }

    @Test
    public void test_buyerDoesNotFulfilPredicate_returnsFalse() {
        // non-matching buyer-type
        BuyerFulfilsPredicate predicate = new BuyerFulfilsPredicate("buyer");
        assertFalse(predicate.test(new BuyerBuilder().withBuyerType("seller").build()));
    }

    @Test
    public void toStringMethod() {
        BuyerFulfilsPredicate predicate = new BuyerFulfilsPredicate("buyer");

        String expected = BuyerFulfilsPredicate.class.getCanonicalName() + "{buyerType=buyer}";
        assertEquals(expected, predicate.toString());
    }
}
