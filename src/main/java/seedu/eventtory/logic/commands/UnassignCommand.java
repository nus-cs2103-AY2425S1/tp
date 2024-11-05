package seedu.eventtory.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.beans.value.ObservableValue;
import seedu.eventtory.commons.core.index.Index;
import seedu.eventtory.logic.Messages;
import seedu.eventtory.logic.commands.exceptions.CommandException;
import seedu.eventtory.logic.commands.util.IndexResolverUtil;
import seedu.eventtory.model.Model;
import seedu.eventtory.model.event.Event;
import seedu.eventtory.model.vendor.Vendor;
import seedu.eventtory.ui.UiState;

/**
 * Unassigns a vendor from an event.
 */
public class UnassignCommand extends Command {

    public static final String COMMAND_WORD = "unassign";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unassigns a vendor from an event. "
            + "Parameters: "
            + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNASSIGN_VENDOR_SUCCESS = "Vendor %s unassigned from Event %s";
    public static final String MESSAGE_UNASSIGN_EVENT_SUCCESS = "Event %s unassigned from Vendor %s";
    public static final String MESSAGE_UNASSIGN_FAILURE_INVALID_VIEW =
        "You have to be viewing a vendor or event to use the unassign command";

    private final Index selectedIndex;

    /**
     * Creates an UnassignCommand to unassign the specified {@code Vendor} from the
     * specified {@code Event}.
     */
    public UnassignCommand(Index selectedIndex) {
        requireNonNull(selectedIndex);
        this.selectedIndex = selectedIndex;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnassignCommand)) {
            return false;
        }

        UnassignCommand otherCommand = (UnassignCommand) other;
        return selectedIndex.equals(otherCommand.selectedIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ObservableValue<UiState> uiState = model.getUiState();
        switch (uiState.getValue()) {
        case VENDOR_DETAILS:
            return assignEventToVendor(model);
        case EVENT_DETAILS:
            return assignVendorToEvent(model);
        default:
            return new CommandResult(MESSAGE_UNASSIGN_FAILURE_INVALID_VIEW);
        }
    }

    private CommandResult assignEventToVendor(Model model) throws CommandException {
        Vendor vendor = model.getViewedVendor().getValue();

        Event event = IndexResolverUtil.resolveEvent(model, selectedIndex);

        if (!model.isVendorAssignedToEvent(vendor, event)) {
            throw new CommandException(Messages.MESSAGE_VENDOR_NOT_ASSIGNED_TO_EVENT);
        }

        model.unassignVendorFromEvent(vendor, event);

        return new CommandResult(
                String.format(MESSAGE_UNASSIGN_EVENT_SUCCESS, event.getName(), vendor.getName()));
    }

    private CommandResult assignVendorToEvent(Model model) throws CommandException {
        Event event = model.getViewedEvent().getValue();

        Vendor vendor = IndexResolverUtil.resolveVendor(model, selectedIndex);

        if (!model.isVendorAssignedToEvent(vendor, event)) {
            throw new CommandException(Messages.MESSAGE_EVENT_DOES_NOT_CONTAIN_VENDOR);
        }

        model.unassignVendorFromEvent(vendor, event);

        return new CommandResult(
                String.format(MESSAGE_UNASSIGN_VENDOR_SUCCESS, vendor.getName(), event.getName()));
    }
}
