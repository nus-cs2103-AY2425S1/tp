package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_STOCK_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_STOCK_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PRODUCTS;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;
import seedu.address.model.product.StockLevel;
import seedu.address.model.product.exceptions.InvalidMaxStockLevelException;
import seedu.address.model.product.exceptions.InvalidMinStockLevelException;
import seedu.address.model.product.exceptions.StockLevelOutOfBoundsException;

/**
 * Sets the minimum and/or maximum stock levels (thresholds) of a product.
 */
public class SetThresholdCommand extends Command {

    public static final String COMMAND_WORD = CommandWords.SET_THRESHOLD_COMMAND;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the minimum and/or maximum stock levels of the product identified "
            + "by the product name. "
            + "Parameters: "
            + PREFIX_PRODUCT_NAME + "NAME "
            + "[" + PREFIX_MIN_STOCK_LEVEL + "MIN_STOCK_LEVEL] "
            + "[" + PREFIX_MAX_STOCK_LEVEL + "MAX_STOCK_LEVEL]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PRODUCT_NAME + "Apple "
            + PREFIX_MIN_STOCK_LEVEL + "50 "
            + PREFIX_MAX_STOCK_LEVEL + "200";

    public static final String MESSAGE_SET_THRESHOLD_SUCCESS = "Updated Product: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one of minimum or maximum stock levels must be provided.";
    public static final String MESSAGE_PRODUCT_NOT_FOUND = "Product not found: %1$s";

    private final ProductName productName;
    private final Integer minStockLevel;
    private final Integer maxStockLevel;

    /**
     * Creates a SetThresholdCommand to set the thresholds of the specified product.
     *
     * @param productName Name of the product.
     * @param minStockLevel New minimum stock level (nullable).
     * @param maxStockLevel New maximum stock level (nullable).
     */
    public SetThresholdCommand(ProductName productName, Integer minStockLevel, Integer maxStockLevel) {
        requireNonNull(productName);
        if (minStockLevel == null && maxStockLevel == null) {
            throw new IllegalArgumentException(MESSAGE_NOT_EDITED);
        }
        this.productName = productName;
        this.minStockLevel = minStockLevel;
        this.maxStockLevel = maxStockLevel;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Product> productList = model.getFilteredProductList();

        Product productToEdit = productList.stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst()
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_PRODUCT_NOT_FOUND, productName)));

        // Retrieve current stock levels
        StockLevel originalStockLevel = productToEdit.getStockLevel();
        int currentStock = originalStockLevel.getStockLevel();
        int currentMinStock = originalStockLevel.getMinStockLevel();
        int currentMaxStock = originalStockLevel.getMaxStockLevel();

        // Determine new min and max stock levels
        int newMinStock = minStockLevel != null ? minStockLevel : currentMinStock;
        int newMaxStock = maxStockLevel != null ? maxStockLevel : currentMaxStock;

        // Create a new StockLevel object with updated thresholds
        StockLevel updatedStockLevel;
        try {
            updatedStockLevel = new StockLevel(currentStock, newMinStock, newMaxStock);
        } catch (InvalidMinStockLevelException | InvalidMaxStockLevelException | StockLevelOutOfBoundsException e) {
            throw new CommandException(e.getMessage());
        }

        // Create a new Product with the updated StockLevel
        Product editedProduct = new Product(productToEdit.getName(), updatedStockLevel, productToEdit.getTags());
        editedProduct.setSupplierName(productToEdit.getSupplierName());

        model.setProduct(productToEdit, editedProduct);
        model.updateFilteredProductList(PREDICATE_SHOW_ALL_PRODUCTS);
        return new CommandResult(String.format(MESSAGE_SET_THRESHOLD_SUCCESS, editedProduct));
    }

    @Override
    public boolean equals(Object other) {
        // Check for self-comparison
        if (other == this) {
            return true;
        }

        if (!(other instanceof SetThresholdCommand)) {
            return false;
        }

        SetThresholdCommand otherCommand = (SetThresholdCommand) other;
        return productName.equals(otherCommand.productName)
                && ((minStockLevel == null && otherCommand.minStockLevel == null)
                    || (minStockLevel != null && minStockLevel.equals(otherCommand.minStockLevel)))
                && ((maxStockLevel == null && otherCommand.maxStockLevel == null)
                    || (maxStockLevel != null && maxStockLevel.equals(otherCommand.maxStockLevel)));
    }
}
