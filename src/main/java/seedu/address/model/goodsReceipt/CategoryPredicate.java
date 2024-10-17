package seedu.address.model.goodsReceipt;

import java.util.function.Predicate;

import seedu.address.model.goods.GoodsCategories;

/**
 * Tests that a {@code Person}'s {@code Goods}' {@code Category} matches the category given.
 */
public class CategoryPredicate implements Predicate<GoodsReceipt> {
    private final GoodsCategories category;

    public CategoryPredicate(GoodsCategories category) {
        this.category = category;
    }

    @Override
    public boolean test(GoodsReceipt goodsData) {
        // TODO: Uncomment when goods storage is implemented
        return false;
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
    public String toString() { return this.category.toString(); }
}
