package seedu.address.model.goodsreceipt;

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
        // Make it caps insensitive
        String actualGoodsName = goodsData.getGoods().getReadableGoodsName().toLowerCase();
        return actualGoodsName.contains(name.toLowerCase());
    }
}
