package seedu.address.model.product;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.supplier.Name;
import seedu.address.model.tag.Tag;

/**
 * Represents a Product in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Product {
    private final seedu.address.model.product.ProductName name;

    private Name supplierName;

    private int stockLevel;
    private int minStockLevel;
    private int maxStockLevel;

    private final Set<Tag> tags = new HashSet<>(); // TODO: Implement storage of tags

    /**
     * Every field must be present and not null.
     */
    public Product(seedu.address.model.product.ProductName name) {
        requireAllNonNull(name);
        this.name = name;
        this.tags.addAll(tags);
        this.stockLevel = 0;
        this.minStockLevel = Integer.MAX_VALUE;
        this.maxStockLevel = 0;
    }

    /**
     * Every field must be present and not null.
     */
    public Product(seedu.address.model.product.ProductName name, Set<Tag> tags) {
        requireAllNonNull(name, tags);
        this.name = name;
        this.tags.addAll(tags);
        this.stockLevel = 0;
        this.minStockLevel = Integer.MAX_VALUE;
        this.maxStockLevel = 0;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Removes assigned supplier and assignment status.
     */
    public void unsetSupplier() {
        this.supplierName = null;
    }

    /**
     * Sets a supplier and updates assignment status.
     */
    public void setSupplierName(Name supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * Returns true if the product is assigned to a supplier.
     */
    public boolean isAssigned() {
        return supplierName != null;
    }

    /**
     * Returns the supplier assigned to the product.
     */
    public Name getSupplierName() {
        return this.supplierName;
    }

    /**
     * Returns the stock level of the product.
     */
    public int getStockLevel() {
        return stockLevel;
    }

    /**
     * Sets the stock level of the product.
     */
    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    /**
     * Returns the minimum stock level of the product.
     */
    public int getMinStockLevel() {
        return minStockLevel;
    }

    /**
     * Sets the minimum stock level of the product.
     */
    public void setMinStockLevel(int minStockLevel) {
        this.minStockLevel = minStockLevel;
    }

    /**
     * Returns the maximum stock level of the product.
     */
    public int getMaxStockLevel() {
        return maxStockLevel;
    }

    /**
     * Sets the maximum stock level of the product.
     */
    public void setMaxStockLevel(int maxStockLevel) {
        this.maxStockLevel = maxStockLevel;
    }


    public ProductName getName() {
        return name;
    }

    /**
     * Returns true if both products have the same name.
     * This defines a weaker notion of equality between two products.
     */
    public boolean isSameProduct(Product otherSupplier) {
        if (otherSupplier == this) {
            return true;
        }

        return otherSupplier != null
                && otherSupplier.getName().equals(getName());
    }

    /**
     * Returns true if both products have the same identity and data fields.
     * This defines a stronger notion of equality between two products.
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
        return name.equals(otherProduct.name) && stockLevel == otherProduct.stockLevel
            && ((this.supplierName == null && otherProduct.supplierName == null)
            || supplierName.equals(otherProduct.supplierName));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        // TODO: Add supplier/stockLevel?
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .toString();
    }
}
