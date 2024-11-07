package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_STOCK_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_STOCK_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STOCK_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Product;

/**
 * Adds a product to the address book.
 */
public class AddProductCommand extends Command {

    public static final String COMMAND_WORD = CommandWords.ADD_PRODUCT_COMMAND;
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a supplier to InvenTrack. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_STOCK_LEVEL + "STOCK LEVEL] "
            + "[" + PREFIX_MIN_STOCK_LEVEL + "MIN STOCK LEVEL] "
            + "[" + PREFIX_MAX_STOCK_LEVEL + "MAX STOCK LEVEL] "
            + "[" + PREFIX_SUPPLIER_NAME + "SUPPLIER NAME] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "apple "
            + PREFIX_STOCK_LEVEL + "100 "
            + PREFIX_MIN_STOCK_LEVEL + "10 "
            + PREFIX_MAX_STOCK_LEVEL + "500 "
            + PREFIX_SUPPLIER_NAME + "FreshFarms "
            + PREFIX_TAG + "new "
            + PREFIX_TAG + "featured";

    public static final String MESSAGE_SUCCESS = "New product added: %1$s";
    public static final String MESSAGE_DUPLICATE_PRODUCT = "This product already exists.";

    private final Product toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Supplier}.
     */
    public AddProductCommand(Product product) {
        requireNonNull(product);
        toAdd = product;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasProduct(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PRODUCT);
        }

        model.addProduct(toAdd);

        if (toAdd.getSupplierName() != null) { // TODO: Inform user that AssignProduct gets called here too
            AssignProductCommand assignProductCommand = new AssignProductCommand(toAdd.getName(),
                toAdd.getSupplierName());
            assignProductCommand.execute(model);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddProductCommand)) {
            return false;
        }

        AddProductCommand otherAddProductCommand = (AddProductCommand) other;
        return toAdd.equals(otherAddProductCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
