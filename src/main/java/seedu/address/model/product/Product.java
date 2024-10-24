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
    private final ProductName name;
    private Name supplierName;
    private final StockLevel stockLevel;
    private final Set<Tag> tags = new HashSet<>(); // TODO: Implement storage of tags

    /**
     * Constructs a {@code Product} with the specified name and default stock levels.
     */
    public Product(ProductName name) {
        requireAllNonNull(name);
        this.name = name;
        this.stockLevel = new StockLevel(0, Integer.MAX_VALUE, 0); // Default values
    }

    /**
     * Constructs a {@code Product} with the specified name and stock levels.
     */
    public Product(ProductName name, StockLevel stockLevel) {
        requireAllNonNull(name, stockLevel);
        this.name = name;
        this.stockLevel = stockLevel;
    }

    /**
     * Constructs a {@code Product} with the specified name, stock levels, and tags.
     */
    public Product(ProductName name, StockLevel stockLevel, Set<Tag> tags) {
        requireAllNonNull(name, stockLevel, tags);
        this.name = name;
        this.stockLevel = stockLevel;
        this.tags.addAll(tags);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Removes assigned supplier.
     */
    public void unsetSupplier() {
        this.supplierName = null;
    }

    /**
     * Removes assigned supplier if is supplied by the specified supplier.
     */
    public void removeSupplier(Name supplierName) {
        if (this.supplierName.equals(supplierName)) {
            this.supplierName = null;
        }
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

    public ProductName getName() {
        return name;
    }

    /**
     * Returns the current stock level of the product.
     */
    public void setMaxStockLevel(int maxStockLevel) {
        stockLevel.setMaxStockLevel(maxStockLevel);
    }

    public int getMaxStockLevel() {
        return stockLevel.getMaxStockLevel();
    }

    public void setStockLevel(int currentStockLevel) {
        stockLevel.setStockLevel(currentStockLevel);
    }
    /**
     * Returns the stock level object of the product.
     */
    public StockLevel getStockLevel() {
        return stockLevel;
    }

    /**
     * Returns true if both products have the same name.
     * This defines a weaker notion of equality between two products.
     */
    public boolean isSameProduct(Product otherProduct) {
        if (otherProduct == this) {
            return true;
        }
        return otherProduct != null && otherProduct.getName().equals(getName());
    }

    /**
     * Returns true if both products have the same identity and data fields.
     * This defines a stronger notion of equality between two products.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof Product)) {
            return false;
        }
        Product otherProduct = (Product) other;
        return name.equals(otherProduct.name)
                && Objects.equals(supplierName, otherProduct.supplierName)
                && stockLevel.equals(otherProduct.stockLevel)
                && tags.equals(otherProduct.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, supplierName, stockLevel, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("stockLevel", stockLevel)
                .add("supplierName", supplierName)
                .add("tags", tags)
                .toString();
    }
}
