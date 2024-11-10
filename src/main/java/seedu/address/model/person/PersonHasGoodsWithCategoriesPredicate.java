package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.goods.GoodsCategories;

/**
 * Tests that a {@code Person} has goods which have at
 * least one of the specified goods categories
 */
public class PersonHasGoodsWithCategoriesPredicate implements Predicate<Person> {

    private final Model model;

    private final Set<GoodsCategories> categoriesSet;

    /**
     * Creates a predicate that checks if a person has goods
     * which have at least one of the specified goods categories
     */
    public PersonHasGoodsWithCategoriesPredicate(Model model, Set<GoodsCategories> categoriesSet) {
        requireNonNull(model);
        requireNonNull(categoriesSet);
        this.model = model;
        this.categoriesSet = categoriesSet;
    }

    @Override
    public boolean test(Person person) {
        return model
                .getGoods()
                .getReceiptList()
                .stream()
                .filter(goodsReceipt -> goodsReceipt.isFromSupplier(person.getName()))
                .anyMatch(goodsReceipt -> categoriesSet.contains(goodsReceipt.getGoods().category()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonHasGoodsWithCategoriesPredicate otherPersonHasGoodsWithCategoriesPredicate)) {
            return false;
        }

        return model.equals(otherPersonHasGoodsWithCategoriesPredicate.model)
                && categoriesSet.equals(otherPersonHasGoodsWithCategoriesPredicate.categoriesSet);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("model", model)
                .add("categoriesSet", categoriesSet)
                .toString();
    }
}
