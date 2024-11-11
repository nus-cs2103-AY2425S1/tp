package seedu.eventtory.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eventtory.commons.core.index.Index;
import seedu.eventtory.logic.Messages;
import seedu.eventtory.logic.commands.exceptions.CommandException;
import seedu.eventtory.logic.commands.util.IndexResolverUtil;
import seedu.eventtory.model.Model;
import seedu.eventtory.model.event.Event;
import seedu.eventtory.model.vendor.Vendor;
import seedu.eventtory.ui.UiState;

/**
 * Assigns a vendor to an event.
 */
public class AssignCommand extends Command {

    public static final String COMMAND_WORD = "assign";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns a vendor to an event. "
            + "Parameters: "
            + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ASSIGN_VENDOR_SUCCESS = "Vendor %s assigned from Event %s";
    public static final String MESSAGE_ASSIGN_EVENT_SUCCESS = "Event %s assigned from Vendor %s";
    public static final String MESSAGE_ASSIGN_FAILURE_INVALID_VIEW =
            "You have to be viewing a vendor or event to use the assign command";

    private final Index selectedIndex;

    /**
     * Creates an AssignCommand to assign the specified {@code Vendor} to the specified {@code Event}.
     */
    public AssignCommand(Index selectedIndex) {
        requireNonNull(selectedIndex);
        this.selectedIndex = selectedIndex;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignCommand)) {
            return false;
        }

        AssignCommand otherCommand = (AssignCommand) other;
        return selectedIndex.equals(otherCommand.selectedIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        requireNonNull(model);

        UiState uiState = model.getUiState().getValue();
        switch (uiState) {
        case VENDOR_DETAILS:
            return assignEventToVendor(model);
        case EVENT_DETAILS:
            return assignVendorToEvent(model);
        default:
            throw new CommandException(MESSAGE_ASSIGN_FAILURE_INVALID_VIEW);
        }
    }

    private CommandResult assignEventToVendor(Model model) throws CommandException {
        Vendor vendor = model.getViewedVendor().getValue();

        Event event = IndexResolverUtil.resolveEvent(model, selectedIndex);

        if (model.isVendorAssignedToEvent(vendor, event)) {
            throw new CommandException(Messages.MESSAGE_VENDOR_ALREADY_ASSIGNED_TO_EVENT);
        }

        model.assignVendorToEvent(vendor, event);

        return new CommandResult(
                String.format(MESSAGE_ASSIGN_EVENT_SUCCESS, vendor.getName(), event.getName()));
    }

    private CommandResult assignVendorToEvent(Model model) throws CommandException {
        Event event = model.getViewedEvent().getValue();

        Vendor vendor = IndexResolverUtil.resolveVendor(model, selectedIndex);

        if (model.isVendorAssignedToEvent(vendor, event)) {
            throw new CommandException(Messages.MESSAGE_EVENT_ALREADY_CONTAINS_VENDOR);
        }

        model.assignVendorToEvent(vendor, event);

        return new CommandResult(
                String.format(MESSAGE_ASSIGN_VENDOR_SUCCESS, vendor.getName(), event.getName()));
    }
}
