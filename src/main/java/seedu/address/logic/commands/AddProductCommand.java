package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a product to InvenTrack. ";

    public static final String MESSAGE_SUCCESS = "New product added: %1$s";
    public static final String MESSAGE_DUPLICATE_SUPPLIER = "This product already exists.";

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
            throw new CommandException(MESSAGE_DUPLICATE_SUPPLIER);
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
