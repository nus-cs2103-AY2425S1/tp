package seedu.address.testutil;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;
import seedu.address.model.product.StockLevel;
import seedu.address.model.product.exceptions.InvalidMaxStockLevelException;
import seedu.address.model.product.exceptions.InvalidMinStockLevelException;
import seedu.address.model.product.exceptions.InvalidStockLevelException;
import seedu.address.model.product.exceptions.StockLevelOutOfBoundsException;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Product objects.
 */
public class ProductBuilder {

    public static final String DEFAULT_NAME = "Apple";
    public static final int DEFAULT_CURRENT_STOCK = 50;
    public static final int DEFAULT_MIN_STOCK = 10;
    public static final int DEFAULT_MAX_STOCK = 100;

    private ProductName name;
    private int currentStock;
    private int minStock;
    private int maxStock;
    private Set<Tag> tags;

    /**
     * Creates a {@code ProductBuilder} with the default details.
     */
    public ProductBuilder() {
        name = new ProductName(DEFAULT_NAME);
        tags = new HashSet<>();
        currentStock = DEFAULT_CURRENT_STOCK;
        minStock = DEFAULT_MIN_STOCK;
        maxStock = DEFAULT_MAX_STOCK;
    }

    /**
     * Initializes the ProductBuilder with the data of {@code productToCopy}.
     */
    public ProductBuilder(Product productToCopy) {
        name = productToCopy.getName();
        currentStock = productToCopy.getStockLevel().getStockLevel();
        minStock = productToCopy.getStockLevel().getMinStockLevel();
        maxStock = productToCopy.getStockLevel().getMaxStockLevel();
        tags = new HashSet<>(productToCopy.getTags());
    }

    /**
     * Sets the {@code ProductName} of the {@code Product} that we are building.
     */
    public ProductBuilder withName(String name) {
        this.name = new ProductName(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Product} that we are building.
     */
    public ProductBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the current stock level of the {@code Product} that we are building.
     */
    public ProductBuilder withCurrentStock(int currentStock) {
        this.currentStock = currentStock;
        return this;
    }

    /**
     * Sets the minimum stock level of the {@code Product} that we are building.
     */
    public ProductBuilder withMinStock(int minStock) {
        this.minStock = minStock;
        return this;
    }

    /**
     * Sets the maximum stock level of the {@code Product} that we are building.
     */
    public ProductBuilder withMaxStock(int maxStock) {
        this.maxStock = maxStock;
        return this;
    }

    /**
     * Sets the {@code StockLevel} of the {@code Product} that we are building.
     */
    public ProductBuilder withStockLevel(StockLevel stockLevel) {
        this.currentStock = stockLevel.getStockLevel();
        this.minStock = stockLevel.getMinStockLevel();
        this.maxStock = stockLevel.getMaxStockLevel();
        return this;
    }

    /**
     * Builds and returns a {@code Product} object with the specified attributes.
     *
     * @return a {@code Product} object.
     * @throws RuntimeException if invalid stock levels are set.
     */
    public Product build() {
        try {
            StockLevel stockLevel = new StockLevel(currentStock, minStock, maxStock);
            return new Product(name, stockLevel);
        } catch (InvalidStockLevelException | InvalidMinStockLevelException
                | InvalidMaxStockLevelException | StockLevelOutOfBoundsException e) {
            // For testing purposes, wrap checked exceptions into unchecked exceptions
            throw new RuntimeException("Invalid stock levels set in ProductBuilder: " + e.getMessage(), e);
        }
    }
}
