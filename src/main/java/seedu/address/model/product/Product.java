package seedu.address.model.product;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Product in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Product {
    private final seedu.address.model.product.ProductName name;

    /**
     * Every field must be present and not null.
     */
    public Product(seedu.address.model.product.ProductName name) {
        requireAllNonNull(name);
        this.name = name;
    }

    public ProductName getName() {
        return name;
    }


    /**
     * Returns true if both suppliers have the same name.
     * This defines a weaker notion of equality between two suppliers.
     */
    public boolean isSameProduct(Product otherSupplier) {
        if (otherSupplier == this) {
            return true;
        }

        return otherSupplier != null
                && otherSupplier.getName().equals(getName());
    }

    /**
     * Returns true if both suppliers have the same identity and data fields.
     * This defines a stronger notion of equality between two suppliers.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Product)) {
            return false;
        }

        Product otherProduct = (Product) other;
        return name.equals(otherProduct.name);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .toString();
    }
}
