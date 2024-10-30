package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.supplier.Supplier;

/**
 * Deletes a supplier identified using it's displayed index from the address book.
 */
public class DeleteSupplierCommand extends Command {
    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the supplier identified by the index number used in the displayed supplier list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " -s 1";
    public static final String MESSAGE_DELETE_SUPPLIER_SUCCESS = "Supplier index %1$s has been deleted successfully";
    private final Index targetIndex;
    public DeleteSupplierCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (targetIndex.getZeroBased() >= model.getFilteredSupplierList().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
        }
        Supplier supplierToDelete = model.getFilteredSupplierList().get(targetIndex.getZeroBased());
        model.deleteSupplier(supplierToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_SUPPLIER_SUCCESS, targetIndex.getOneBased()));
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteSupplierCommand)) {
            return false;
        }

        DeleteSupplierCommand otherDeleteCommand = (DeleteSupplierCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }
}
