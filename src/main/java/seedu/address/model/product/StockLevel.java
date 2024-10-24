package seedu.address.model.product;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.product.exceptions.InvalidMaxStockLevelException;
import seedu.address.model.product.exceptions.InvalidMinStockLevelException;
import seedu.address.model.product.exceptions.InvalidStockLevelException;
import seedu.address.model.product.exceptions.StockLevelOutOfBoundsException;

/**
 * Represents the stock levels of a product.
 * Guarantees: details are present and not null, field values are validated.
 */
public class StockLevel {

    private int stockLevel;
    private int minStockLevel;
    private int maxStockLevel;

    /**
     * Constructs a {@code StockLevel} with specified stock, min, and max levels.
     *
     * @param stockLevel The current stock level.
     * @param minStockLevel The minimum stock level.
     * @param maxStockLevel The maximum stock level.
     */
    public StockLevel(int stockLevel, int minStockLevel, int maxStockLevel) {
        requireAllNonNull(stockLevel, minStockLevel, maxStockLevel);
        validateMinAndMaxStockLevels(minStockLevel, maxStockLevel);
        validateStockLevel(stockLevel, minStockLevel, maxStockLevel);
        this.stockLevel = stockLevel;
        this.minStockLevel = minStockLevel;
        this.maxStockLevel = maxStockLevel;
    }

    private void validateStockLevel(int stockLevel, int minStockLevel, int maxStockLevel) {
        if (stockLevel < 0) {
            throw new InvalidStockLevelException("Stock level cannot be negative.");
        }
        if (stockLevel < minStockLevel || stockLevel > maxStockLevel) {
            throw new StockLevelOutOfBoundsException(
                    String.format("Stock level must be between min (%d) and max (%d) stock levels.",
                            minStockLevel, maxStockLevel));
        }
    }

    private void validateMinAndMaxStockLevels(int minStockLevel, int maxStockLevel) {
        if (minStockLevel < 0) {
            throw new InvalidMinStockLevelException("Minimum stock level cannot be negative.");
        }
        if (maxStockLevel < minStockLevel) {
            throw new InvalidMaxStockLevelException(
                    "Maximum stock level cannot be less than minimum stock level.");
        }
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public int getMinStockLevel() {
        return minStockLevel;
    }

    public int getMaxStockLevel() {
        return maxStockLevel;
    }

    public void setStockLevel(int stockLevel) {
        validateStockLevel(stockLevel, this.minStockLevel, this.maxStockLevel);
        this.stockLevel = stockLevel;
    }

    public void setMinStockLevel(int minStockLevel) {
        validateMinAndMaxStockLevels(minStockLevel, this.maxStockLevel);
        validateStockLevel(this.stockLevel, minStockLevel, this.maxStockLevel);
        this.minStockLevel = minStockLevel;
    }

    public void setMaxStockLevel(int maxStockLevel) {
        validateMinAndMaxStockLevels(this.minStockLevel, maxStockLevel);
        validateStockLevel(this.stockLevel, this.minStockLevel, maxStockLevel);
        this.maxStockLevel = maxStockLevel;
    }

    public boolean isBelowMinStock() {
        return stockLevel < minStockLevel;
    }

    public boolean isAboveMaxStock() {
        return stockLevel > maxStockLevel;
    }

    @Override
    public String toString() {
        return String.format("StockLevel{stockLevel=%d, minStockLevel=%d, maxStockLevel=%d}",
                stockLevel, minStockLevel, maxStockLevel);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof StockLevel)) {
            return false;
        }

        StockLevel otherStock = (StockLevel) other;
        return stockLevel == otherStock.stockLevel
                && minStockLevel == otherStock.minStockLevel
                && maxStockLevel == otherStock.maxStockLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockLevel, minStockLevel, maxStockLevel);
    }
}
