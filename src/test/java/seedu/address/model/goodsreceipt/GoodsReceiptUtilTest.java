package seedu.address.model.goodsreceipt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.GoodsReceiptTestUtil;

public class GoodsReceiptUtilTest {
    // Use DummyGoodsList data as baseline
    private static final List<GoodsReceipt> GOODS_LIST = GoodsReceiptTestUtil.getDummyGoodsList();
    private static final int QUANTITY_TOTAL = 17;
    private static final double GOODS_PRICE_TOTAL = 142.72;

    @Test
    public void sumQuantity_success() {
        int total = GoodsReceiptUtil.sumQuantity(GOODS_LIST);
        assertEquals(QUANTITY_TOTAL, total);
    }

    @Test
    public void sumQuantity_emptyList_success() {
        int total = GoodsReceiptUtil.sumQuantity(new ArrayList<GoodsReceipt>());
        assertEquals(0, total);
    }

    @Test
    public void sumTotals_success() {
        double total = GoodsReceiptUtil.sumTotals(GOODS_LIST);
        assertEquals(GOODS_PRICE_TOTAL, total);
    }

    @Test
    public void sumTotals_emptyList_success() {
        double total = GoodsReceiptUtil.sumTotals(new ArrayList<GoodsReceipt>());
        assertEquals(0.0, total);
    }
}
