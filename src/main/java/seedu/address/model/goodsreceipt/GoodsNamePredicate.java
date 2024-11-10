package seedu.address.model.goodsreceipt;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

/**
 * Tests that a {@code GoodsReceipt}'s {@code Goods} partially or fully matches the given {@code GoodsName}.
 */
public class GoodsNamePredicate implements Predicate<GoodsReceipt> {
    private final String name;

    /**
     * Constructs a predicate that checks if a goods receipt has a good name.
     */
    public GoodsNamePredicate(String name) {
        requireNonNull(name);
        this.name = name.toLowerCase();
    }

    @Override
    public boolean test(GoodsReceipt goodsData) {
        // Make it caps insensitive
        String actualGoodsName = goodsData.getGoods().toString().toLowerCase();
        return actualGoodsName.contains(name);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GoodsNamePredicate otherGoodsNamePredicate)) {
            return false;
        }

        return name.equals(otherGoodsNamePredicate.name);
    }
}
