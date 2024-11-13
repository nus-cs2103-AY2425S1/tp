package seedu.address.model.goodsreceipt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.goods.Goods;
import seedu.address.model.goods.GoodsCategories;
import seedu.address.model.goods.GoodsName;
import seedu.address.model.person.Name;

public class GoodsNamePredicateTest {
    private static final String DATETIME_PROCUREMENT_VALID = "2024-10-10 12:00";
    private static final String DATETIME_ARRIVAL_VALID = "2024-12-12 12:00";
    private final Goods testGoods = new Goods(new GoodsName("Gardenia Bread"), GoodsCategories.CONSUMABLES);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GoodsNamePredicate(null));
    }

    @Test
    public void gooodsNamePredicateTest_valid_predicateSuccess() {
        GoodsReceipt testReceipt = new GoodsReceipt(testGoods, new Name("Alex Yeoh"),
                new Date(DATETIME_PROCUREMENT_VALID), new Date(DATETIME_ARRIVAL_VALID), false, 1, 1.0);

        GoodsNamePredicate testPredicate = new GoodsNamePredicate("Bread");
        boolean posResult = testPredicate.test(testReceipt);
        assertTrue(posResult);
    }

    @Test
    public void goodsNamePredicateTest_invalid_predicateFailure() {
        GoodsReceipt testReceipt = new GoodsReceipt(testGoods, new Name("Alex Yeoh"),
                new Date(DATETIME_PROCUREMENT_VALID), new Date(DATETIME_ARRIVAL_VALID), false, 1, 1.0);

        GoodsNamePredicate testPredicate = new GoodsNamePredicate("Cheese");
        boolean negResult = testPredicate.test(testReceipt);
        assertFalse(negResult);
    }

    @Test
    public void goodsNamePredicateTest_checkCapitalization_success() {
        GoodsReceipt testReceipt = new GoodsReceipt(testGoods, new Name("Alex Yeoh"),
                new Date(DATETIME_PROCUREMENT_VALID), new Date(DATETIME_ARRIVAL_VALID), false, 1, 1.0);

        GoodsNamePredicate testLowerPredicate = new GoodsNamePredicate("bread");
        GoodsNamePredicate testUpperPredicate = new GoodsNamePredicate("BREAD");
        boolean lowerResult = testLowerPredicate.test(testReceipt);
        boolean upperResult = testUpperPredicate.test(testReceipt);
        assertTrue(lowerResult && upperResult);
    }

    @Test
    public void equals() {
        GoodsNamePredicate predicate = new GoodsNamePredicate("Bread");

        // check that it is equal to itself
        assertEquals(predicate, predicate);

        // check that it is equal regardless of capitalization
        assertEquals(predicate, new GoodsNamePredicate("BREAD"));

        // check that it is not equal if keyword is different
        assertNotEquals(predicate, new GoodsNamePredicate("Ice Cream"));

        // check that it is not equal to other types
        assertNotEquals(1.00, predicate);
    }
}
