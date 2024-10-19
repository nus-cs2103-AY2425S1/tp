package seedu.address.model.goodsReceipt;

import java.util.function.Predicate;

/**
 * Tests that a {@code GoodsReceipt}'s {@code Goods} partially or fully matches the given {@code GoodsName}.
 */
public class GoodsNamePredicate implements Predicate<GoodsReceipt> {
    private final String name;

    public GoodsNamePredicate(String name) {
        this.name = name;
    }

    @Override
    public boolean test(GoodsReceipt goodsData) {
        return goodsData.getGoods().getReadableGoodsName().contains(name);
    }
}
