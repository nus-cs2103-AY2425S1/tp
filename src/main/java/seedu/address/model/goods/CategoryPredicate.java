package seedu.address.model.goods;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

public class CategoryPredicate implements Predicate<Person> {
    private final GoodsCategories category;

    public CategoryPredicate(GoodsCategories category) {
        this.category = category;
    }

    @Override
    public boolean test(Person person) {
        // TODO: Uncomment when goods storage is implemented
        // return person.getGoods().stream(good -> category.equals(good.getCategory()));
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
