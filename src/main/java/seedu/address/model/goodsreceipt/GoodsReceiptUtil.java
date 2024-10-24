package seedu.address.model.goodsreceipt;

import java.util.List;

/**
 * Contains a set of utility methods for goods.
 */
public class GoodsReceiptUtil {
    /**
     * Return the total quantity of goods from a list of goodsReceipts.
     *
     * @param goodsReceipt A list of goodsReceipts.
     */
    public static int sumQuantity(List<GoodsReceipt> goodsReceipt) {
        return goodsReceipt.stream().map(GoodsReceipt::getQuantity)
                                 .reduce(Integer::sum)
                                 .orElse(0);
    }

    /**
     * Return the sum of price totals from a list of goodsReceipts.
     *
     * @param goodsReceipt A list of goodsReceipts.
     */
    public static double sumTotals(List<GoodsReceipt> goodsReceipt) {
        return goodsReceipt.stream().map(GoodsReceipt::getPriceTotal)
                                 .reduce(Double::sum)
                                 .orElse(0.0);
    }
}
