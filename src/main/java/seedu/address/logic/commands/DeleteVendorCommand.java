package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.vendor.Vendor;

/**
 * Deletes a vendor identified using it's displayed index from the address book.
 */
public class DeleteVendorCommand extends DeleteCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed vendor list.\n"
            + "Parameters: "
            + PREFIX_VENDOR
            + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_VENDOR + "1";

    public static final String MESSAGE_DELETE_VENDOR_SUCCESS = "Deleted Vendor: %1$s";

    /**
     * Creates a DeleteVendorCommand to delete the vendor at the specified
     * {@code Index}.
     *
     * @param targetIndex Index of the vendor in the filtered vendor list to delete.
     */
    public DeleteVendorCommand(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Vendor> lastShownList = model.getFilteredVendorList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX);
        }

        Vendor vendorToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteVendor(vendorToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_VENDOR_SUCCESS, Messages.format(vendorToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteVendorCommand otherDeleteCommand = (DeleteVendorCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }
}
