package seedu.address.model.product;

import java.util.function.Predicate;

/**
 * Tests that a {@code Product}'s {@code SupplierName} matches the supplier name given.
 */
public class ProductSupplierNamePredicate implements Predicate<Product> {
    private final String supplierName;

    public ProductSupplierNamePredicate(String supplierName) {
        this.supplierName = supplierName;
    }

    @Override
    public boolean test(Product product) {
        return product.isAssigned() && product.getSupplierName().fullName.equals(supplierName);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ProductSupplierNamePredicate
                && supplierName.equals(((ProductSupplierNamePredicate) other).supplierName));
    }
}
