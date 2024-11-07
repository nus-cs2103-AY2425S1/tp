package seedu.eventtory.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_VENDOR;

import seedu.eventtory.commons.core.index.Index;
import seedu.eventtory.logic.Messages;
import seedu.eventtory.logic.commands.exceptions.CommandException;
import seedu.eventtory.logic.commands.util.IndexResolverUtil;
import seedu.eventtory.model.Model;
import seedu.eventtory.model.vendor.Vendor;

/**
 * Display details of a vendor in EventTory to the user.
 */
public class ViewVendorCommand extends ViewCommand {

    public static final String MESSAGE_SUCCESS = "Viewing %s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the vendor identified by the index number used in the displayed event list.\n" + "Parameters: "
            + PREFIX_VENDOR + "INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " " + PREFIX_VENDOR
            + "1";

    public ViewVendorCommand(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Vendor vendorToView = IndexResolverUtil.resolveVendor(model, targetIndex);

        model.viewVendor(vendorToView);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(vendorToView)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewVendorCommand)) {
            return false;
        }

        ViewVendorCommand otherViewVendorCommand = (ViewVendorCommand) other;
        return targetIndex.equals(otherViewVendorCommand.targetIndex);
    }
}
