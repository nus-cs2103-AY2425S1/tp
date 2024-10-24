package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PRODUCTS;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;
import seedu.address.model.product.StockLevel;
import seedu.address.testutil.ProductBuilder;

/**
 * Edits the maximum stock level of an existing product in the address book.
 */
public class UpdateStockLevelCommand extends Command {

    public static final String COMMAND_WORD = "updateStock"; // Adjust as per your command words

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the maximum stock level "
            + "by the product name used in the displayed product list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters:  "
            + "p/PRODUCT_NAME "
            + "stk/MAX_STOCK_LEVEL "
            + "Example: " + COMMAND_WORD + " p/RiceSacks stk/425 ";

    public static final String MESSAGE_EDIT_PRODUCT_SUCCESS = "Edited Product: %1$s with Max Stock Level: %2$d";
    public static final String MESSAGE_NOT_EDITED = "Stock level not provided.";
    public static final String MESSAGE_PRODUCT_NOT_FOUND = "Product not found in the list: %1$s";
    public static final String MESSAGE_INVALID_MAX_STOCK = "Invalid maximum stock level.";

    private final ProductName productName;
    private final int maxStockLevel;

    /**
     * Constructs an {@code UpdateStockLevelCommand} to update the max stock level of the specified product.
     *
     * @param productName   Name of the product to update.
     * @param maxStockLevel New maximum stock level.
     */
    public UpdateStockLevelCommand(ProductName productName, int maxStockLevel) {
        requireNonNull(productName);
        this.productName = productName;
        this.maxStockLevel = maxStockLevel;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Product> lastShownProductList = model.getFilteredProductList();

        Product productToEdit = lastShownProductList.stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst()
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_PRODUCT_NOT_FOUND, productName)));

        // Retrieve existing StockLevel
        StockLevel originalStockLevel = productToEdit.getStockLevel();
        int currentStock = originalStockLevel.getStockLevel();
        int currentMinStock = originalStockLevel.getMinStockLevel();

        // Validate new max stock level
        if (maxStockLevel < currentMinStock) {
            throw new CommandException("Maximum stock level cannot be less than minimum stock level.");
        }

        if (currentStock > maxStockLevel) {
            throw new CommandException("Current stock level exceeds the new maximum stock level.");
        }

        // Create updated StockLevel
        StockLevel updatedStockLevel;
        try {
            updatedStockLevel = new StockLevel(currentStock, currentMinStock, maxStockLevel);
        } catch (Exception e) {
            throw new CommandException(MESSAGE_INVALID_MAX_STOCK);
        }

        // Create updated Product using ProductBuilder
        Product editedProduct = new ProductBuilder(productToEdit)
                .withStockLevel(updatedStockLevel)
                .build();

        // Update the model
        model.setProduct(productToEdit, editedProduct);
        model.updateFilteredProductList(PREDICATE_SHOW_ALL_PRODUCTS);
        return new CommandResult(String.format(MESSAGE_EDIT_PRODUCT_SUCCESS,
                Messages.format(editedProduct), maxStockLevel));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        }

        if (!(other instanceof UpdateStockLevelCommand)) { // instanceof handles nulls
            return false;
        }

        UpdateStockLevelCommand otherCommand = (UpdateStockLevelCommand) other;
        return productName.equals(otherCommand.productName)
                && maxStockLevel == otherCommand.maxStockLevel;
    }
}
