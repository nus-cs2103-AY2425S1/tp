package seedu.address.model.supplier.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.supplier.Supplier;

/**
 * Abstract class for predicates that operate on a {@code Supplier}.
 */
public abstract class SupplierPredicate implements Predicate<Supplier> {
    protected final String keyword;

    public SupplierPredicate(String keywords) {
        this.keyword = keywords;
    }

    @Override
    public abstract boolean test(Supplier supplier);

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SupplierPredicate)) {
            return false;
        }

        SupplierPredicate otherPredicate = (SupplierPredicate) other;
        return keyword.equals(otherPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywords", keyword)
                .toString();
    }
}
