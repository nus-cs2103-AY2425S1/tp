package seedu.address.model.goodsreceipt;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;

import org.junit.jupiter.api.Test;

import seedu.address.model.goods.Goods;
import seedu.address.model.goods.GoodsCategories;
import seedu.address.model.goods.GoodsName;
import seedu.address.model.person.Name;

public class GoodsReceiptTest {

    private static final String DATETIME_VALID = "2024-10-10 12:00";
    private static final String DATETIME_INVALID = "2024-12-12 12:00";
    private static final Goods VALID_GOODS =
            new Goods(new GoodsName("Milk Bread"), GoodsCategories.CONSUMABLES);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GoodsReceipt(
                VALID_GOODS,
                new Name("Alex Yeoh"), null,
                new Date(DATETIME_VALID), false, 1, 5.22));
    }

    @Test
    public void constructor_invalidProcurementDate_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new GoodsReceipt(
                VALID_GOODS,
                new Name("Alex Yeoh"), new Date(DATETIME_INVALID),
                new Date(DATETIME_VALID), false, 1, 5.22));
    }

    @Test
    public void constructor_validParameters_success() {
        assertDoesNotThrow(() -> new GoodsReceipt(
                VALID_GOODS,
                new Name("Alex Yeoh"), new Date(DATETIME_VALID),
                new Date(DATETIME_VALID), false, 1, 5.22));
    }

    @Test
    public void getGoods_success() {
        GoodsReceipt goodsReceipt = new GoodsReceipt(
                VALID_GOODS,
                new Name("Alex Yeoh"), new Date(DATETIME_VALID),
                new Date(DATETIME_VALID), false, 1, 5.22);

        assertTrue(goodsReceipt.getGoods().equals(VALID_GOODS));
    }

    @Test
    public void unmarkedGoodsReceipt_markAsDelivered_returnsMarkedReceipt() {
        GoodsReceipt goodsReceipt = new GoodsReceipt(
                VALID_GOODS,
                new Name("Alex Yeoh"), new Date(DATETIME_VALID),
                new Date(DATETIME_VALID), false, 1, 5.22);

        GoodsReceipt goodsReceiptMarked = goodsReceipt.markAsDelivered();
        assertTrue(goodsReceiptMarked.isDelivered());
    }

    @Test
    public void goodsReceiptToString_success() {
        GoodsReceipt goodsReceipt = new GoodsReceipt(
                VALID_GOODS,
                new Name("Alex Yeoh"), new Date(DATETIME_VALID),
                new Date(DATETIME_VALID), false, 1, 5.22);
        assertTrue(Objects.equals(goodsReceipt.toString(), "[Quantity 1] Milk Bread (Pending)"));
    }
}
