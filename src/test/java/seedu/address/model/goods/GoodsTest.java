package seedu.address.model.goods;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.goodsReceipt.Date;
import seedu.address.model.person.Name;

public class GoodsTest {
    private static final String DATETIME_VALID = "2024-10-10 12:00";
    private static final String DATETIME_INVALID = "2024-12-12 12:00";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new Goods(new GoodsName("Test"), GoodsCategories.CONSUMABLES));
    }

    @Test
    public void constructor_invalidProcurementDate_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Goods(new GoodsName("Milk Bread^*"), GoodsCategories.CONSUMABLES));
    }

    @Test
    public void constructor_validParameters_success() {
        assertDoesNotThrow(() -> new Goods(new GoodsName("Milk Bread"), GoodsCategories.CONSUMABLES));
    }
}
