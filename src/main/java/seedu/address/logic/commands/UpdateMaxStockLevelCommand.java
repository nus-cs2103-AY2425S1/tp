package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STOCK_LEVEL;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PRODUCTS;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;

/**
 * Edits the details of an existing supplier in the address book.
 */
public class UpdateMaxStockLevelCommand extends Command {

    public static final String COMMAND_WORD = "update_maxstock";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the products identified "
            + "by the product name used in the displayed product list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters:  "
            + PREFIX_PRODUCT_NAME + "PRODUCT_NAME "
            + PREFIX_STOCK_LEVEL + "MAX_STOCK_LEVEL "
            + "Example: " + COMMAND_WORD + " pr/Rice sacks stk/425 ";

    public static final String MESSAGE_EDIT_PRODUCT_SUCCESS = "Edited Product: %1$s " + "with Stock Level: %2$s";
    public static final String MESSAGE_NOT_EDITED = "Stock level not provided.";
    public static final String MESSAGE_PRODUCT_NOT_FOUND = "Product not found in the list: %1$s";

    private ProductName productName;
    private int maxStockLevel;

    /**
     * @param pname name of the product in the filtered product list to edit
     */
    public UpdateMaxStockLevelCommand(ProductName pname, int maxStockLevel) {
        requireNonNull(pname);
        this.productName = pname;
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

        Product editedProduct = new Product(productName);
        editedProduct.setMaxStockLevel(maxStockLevel);

        model.setProduct(productToEdit, editedProduct);
        model.updateFilteredProductList(PREDICATE_SHOW_ALL_PRODUCTS);
        return new CommandResult(String.format(MESSAGE_EDIT_PRODUCT_SUCCESS,
                Messages.format(editedProduct), maxStockLevel));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateMaxStockLevelCommand)) {
            return false;
        }

        UpdateMaxStockLevelCommand otherUpdateMaxStockCommand = (UpdateMaxStockLevelCommand) other;
        return productName.equals(otherUpdateMaxStockCommand.productName)
                & (maxStockLevel == otherUpdateMaxStockCommand.maxStockLevel);
    }

}

