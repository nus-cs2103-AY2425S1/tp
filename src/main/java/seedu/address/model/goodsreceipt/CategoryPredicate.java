package seedu.address.model.goodsreceipt;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.model.goods.GoodsCategories;

/**
 * Tests that a {@code Person}'s {@code Goods}'s {@code Category} matches the category given.
 */
public class CategoryPredicate implements Predicate<GoodsReceipt> {
    private final GoodsCategories category;

    /**
     * Constructor for CategoryPredicate.
     */
    public CategoryPredicate(GoodsCategories category) {
        requireNonNull(category);
        this.category = category;
    }

    @Override
    public boolean test(GoodsReceipt goodsData) {
        return goodsData.getGoods().getCategory().equals(this.category);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CategoryPredicate otherCategoryPredicate)) {
            return false;
        }

        return category.equals(otherCategoryPredicate.category);
    }

    @Override
    public String toString() {
        return this.category.toString();
    }
}
