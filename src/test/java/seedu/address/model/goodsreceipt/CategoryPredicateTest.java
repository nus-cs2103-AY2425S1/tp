package seedu.address.model.goodsreceipt;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.goods.Goods;
import seedu.address.model.goods.GoodsCategories;
import seedu.address.model.goods.GoodsName;
import seedu.address.model.person.Name;

public class CategoryPredicateTest {
    private static final String DATETIME_PROCUREMENT_VALID = "2024-10-10 12:00";
    private static final String DATETIME_ARRIVAL_VALID = "2024-12-12 12:00";
    private final Goods testGoods = new Goods(new GoodsName("Gardenia Bread"), GoodsCategories.CONSUMABLES);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CategoryPredicate(null));
    }

    @Test
    public void categoryPredicateTest_valid_predicateSuccess() {
        GoodsReceipt testReceipt = new GoodsReceipt(testGoods, new Name("Alex Yeoh"),
                new Date(DATETIME_PROCUREMENT_VALID), new Date(DATETIME_ARRIVAL_VALID), false, 1, 1.0);

        CategoryPredicate testPredicate = new CategoryPredicate(GoodsCategories.CONSUMABLES);
        boolean posResult = testPredicate.test(testReceipt);
        assertTrue(posResult);
    }

    @Test
    public void categoryPredicateTest_invalid_predicateFailure() {
        GoodsReceipt testReceipt = new GoodsReceipt(testGoods, new Name("Alex Yeoh"),
                new Date(DATETIME_PROCUREMENT_VALID), new Date(DATETIME_ARRIVAL_VALID), false, 1, 1.0);

        CategoryPredicate testPredicate = new CategoryPredicate(GoodsCategories.SPECIALTY);
        boolean negResult = testPredicate.test(testReceipt);
        assertFalse(negResult);
    }
}
