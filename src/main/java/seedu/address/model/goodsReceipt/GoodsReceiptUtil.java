package seedu.address.model.goodsReceipt;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.person.Name;

/**
 * Contains a set of utility methods for goods.
 */
public class GoodsReceiptUtil {
    /**
     * Return a list of goods belonging to a specific supplier.
     *
     * @param supplier A valid supplier name.
     * @param goodsReceipt A list of goodsReceipts.
     */
    public static List<Name> filterGoodsBySuppliers(Name supplier, List<GoodsReceipt> goodsReceipt) {
        return goodsReceipt.stream()
                .filter(g -> g.isFromSupplier(supplier))
                .map(x-> new Name(x.toString()))
                .collect(Collectors.toSet())
                .stream().toList();
    }

    /**
     * Return the total quantity of goods belonging to a specific supplier.
     *
     * @param supplier A valid supplier name.
     * @param goodsReceipt A list of goodsReceipts.
     */
    public static int sumQuantityBySuppliers(Name supplier, List<GoodsReceipt> goodsReceipt) {
        return goodsReceipt.stream().filter(g -> g.isFromSupplier(supplier))
                                 .map(GoodsReceipt::getQuantity)
                                 .reduce(Integer::sum)
                                 .orElse(0);
    }

    /**
     * Return the sum of price totals from a list of goods.
     *
     * @param goodsReceipt A list of goodsReceipts.
     */
    public static double sumTotals(List<GoodsReceipt> goodsReceipt) {
        return goodsReceipt.stream().map(GoodsReceipt::getPriceTotal)
                                 .reduce(Double::sum)
                                 .orElse(0.0);
    }
}
