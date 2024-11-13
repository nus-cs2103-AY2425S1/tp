package seedu.address.model.goodsreceipt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.goods.Goods;
import seedu.address.model.goods.GoodsCategories;
import seedu.address.model.goods.GoodsName;
import seedu.address.model.person.Name;

public class DeliveredPredicateTest {
    private static final String DATETIME_PROCUREMENT_VALID = "2024-10-10 12:00";
    private static final String DATETIME_ARRIVAL_VALID_PENDING = "2024-12-12 12:00";
    private static final String DATETIME_ARRIVAL_VALID_DELIVERED = "2024-12-12 12:00";
    private final Goods testGoods = new Goods(new GoodsName("Gardenia Bread"), GoodsCategories.CONSUMABLES);

    @Test
    public void deliveredPredicateTest_valid_predicateSuccess() {
        GoodsReceipt testReceipt = new GoodsReceipt(testGoods, new Name("Alex Yeoh"),
                new Date(DATETIME_PROCUREMENT_VALID), new Date(DATETIME_ARRIVAL_VALID_DELIVERED), true, 1, 1.0);

        DeliveredPredicate testPredicate = new DeliveredPredicate(true);
        boolean posResult = testPredicate.test(testReceipt);
        assertTrue(posResult);
    }

    @Test
    public void deliveredPredicateTest_invalid_predicateFailure() {
        GoodsReceipt testReceipt = new GoodsReceipt(testGoods, new Name("Alex Yeoh"),
                new Date(DATETIME_PROCUREMENT_VALID), new Date(DATETIME_ARRIVAL_VALID_PENDING), false, 1, 1.0);

        DeliveredPredicate testPredicate = new DeliveredPredicate(true);
        boolean posResult = testPredicate.test(testReceipt);
        assertFalse(posResult);
    }

    @Test
    public void equals() {
        DeliveredPredicate predicate = new DeliveredPredicate(true);

        // check that it is equal to itself
        assertEquals(predicate, predicate);

        // check that it is equal when isDelivered is equal
        assertEquals(predicate, new DeliveredPredicate(true));

        // check that it is not equal with different isDelivered
        assertNotEquals(predicate, new DeliveredPredicate(false));

        // check that it is not equal with null
        assertNotEquals(null, predicate);

        // check that it is not equal with different object type
        assertNotEquals(predicate, new Object());
    }
}
