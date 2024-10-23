package seedu.address.model.goodsreceipt;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Name;
import seedu.address.testutil.GoodsReceiptTestUtil;

public class GoodsReceiptUtilTest {
    // Use DummyGoodsList data as baseline
    private static final List<GoodsReceipt> GOODS_LIST = GoodsReceiptTestUtil.getDummyGoodsList();
    private static final double GOODS_PRICE_TOTAL = 142.72;

    @Test
    public void filterGoodsBySuppliers_success() {
        List<GoodsReceipt> filteredList =
                GoodsReceiptUtil.filterGoodsReceiptsBySuppliers(new Name("Bernice Yu"), GOODS_LIST);
        assertTrue(filteredList.size() == 2);
    }

    @Test
    public void filterGoodsBySuppliers_noValidSupplier() {
        List<GoodsReceipt> filteredList =
                GoodsReceiptUtil.filterGoodsReceiptsBySuppliers(new Name("No Such Person"), GOODS_LIST);
        assertTrue(filteredList.size() == 0);
    }

    @Test
    public void sumQuantityBySuppliers_success() {
        int total = GoodsReceiptUtil.sumQuantityBySuppliers(new Name("Alex Yeoh"), GOODS_LIST);
        assertTrue(total == 11);
    }

    @Test
    public void sumQuantityBySuppliers_noValidSupplier() {
        int total = GoodsReceiptUtil.sumQuantityBySuppliers(new Name("No Such Person"), GOODS_LIST);
        assertTrue(total == 0);
    }

    @Test
    public void sumTotals_success() {
        double total = GoodsReceiptUtil.sumTotals(GOODS_LIST);
        assertTrue(total == GOODS_PRICE_TOTAL);

        List<GoodsReceipt> filteredList =
                GoodsReceiptUtil.filterGoodsReceiptsBySuppliers(new Name("No Such Person"), GOODS_LIST);
        double total2 = GoodsReceiptUtil.sumTotals(filteredList);
        assertTrue(total2 == 0);
    }
}
