package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Supplier;

/**
 * Deletes a supplier from InvenTrack.
 */
public class DeleteSupplierCommand extends Command {

    public static final String COMMAND_WORD = CommandWords.DELETE_SUPPLIER_COMMAND;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a supplier from InvenTrack. "
            + "Parameters: n/SUPPLIER_NAME\n"
            + "Example: " + COMMAND_WORD + " n/John Doe";

    public static final String MESSAGE_SUCCESS = "Supplier deleted: %1$s";
    public static final String MESSAGE_SUPPLIER_NOT_FOUND = "This supplier does not exist.";

    private final Name supplierName;

    /**
     * Creates a DeleteSupplierCommand to delete the specified {@code Supplier} by name.
     */
    public DeleteSupplierCommand(Name supplierName) {
        requireNonNull(supplierName);
        this.supplierName = supplierName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Supplier supplierToDelete = model.findSupplier(supplierName);

        if (supplierToDelete == null) {
            throw new CommandException(MESSAGE_SUPPLIER_NOT_FOUND);
        }

        model.deleteSupplier(supplierToDelete);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(supplierToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // Short circuit if same object
                || (other instanceof DeleteSupplierCommand // instanceof handles nulls
                && supplierName.equals(((DeleteSupplierCommand) other).supplierName));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("supplierName", supplierName)
                .toString();
    }
}

