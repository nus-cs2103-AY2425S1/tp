package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;

/**
 * Deletes a product from InvenTrack.
 */
public class DeleteProductCommand extends Command {

    public static final String COMMAND_WORD = CommandWords.DELETE_PRODUCT_COMMAND;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a product from InvenTrack. "
            + "Parameters: n/PRODUCT_NAME\n"
            + "Example: " + COMMAND_WORD + " n/Apple";

    public static final String MESSAGE_SUCCESS = "Product deleted: %1$s";
    public static final String MESSAGE_PRODUCT_NOT_FOUND = "This product does not exist.";

    private final ProductName productName;

    /**
     * Creates a DeleteProductCommand to delete the specified {@code Product} by name.
     */
    public DeleteProductCommand(ProductName productName) {
        requireNonNull(productName);
        this.productName = productName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Product productToDelete = model.findProductByName(productName);

        if (productToDelete == null) {
            throw new CommandException(MESSAGE_PRODUCT_NOT_FOUND);
        }

        model.deleteProduct(productToDelete);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(productToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // Short circuit if same object
                || (other instanceof DeleteProductCommand // instanceof handles nulls
                && productName.equals(((DeleteProductCommand) other).productName));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("productName", productName)
                .toString();
    }
}
