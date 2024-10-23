package seedu.address.model.goodsreceipt;

import java.util.function.Predicate;

import seedu.address.model.person.Name;

/**
 * Tests that a {@code GoodsReceipt} belongs to a supplier by {@code Name}.
 */
public class SupplierNamePredicate implements Predicate<GoodsReceipt> {
    private final Name name;

    public SupplierNamePredicate(Name name) {
        this.name = name;
    }

    @Override
    public boolean test(GoodsReceipt goodsData) {
        return goodsData.isFromSupplier(this.name);
    }
}
