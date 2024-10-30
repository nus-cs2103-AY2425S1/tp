package seedu.eventtory.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.value.ObservableValue;
import seedu.eventtory.commons.core.index.Index;
import seedu.eventtory.logic.Messages;
import seedu.eventtory.logic.commands.exceptions.CommandException;
import seedu.eventtory.model.Model;
import seedu.eventtory.model.event.Event;
import seedu.eventtory.model.vendor.Vendor;
import seedu.address.ui.UiState;

/**
 * Unassigns a vendor from an event.
 */
public class UnassignCommand extends Command {

    public static final String COMMAND_WORD = "unassign";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unassigns a vendor from an event. "
            + "Parameters: "
            + "v/<VENDOR_INDEX> e/<EVENT_INDEX> or e/<EVENT_INDEX> v/<VENDOR_INDEX>\n"
            + "Example: " + COMMAND_WORD + " v/1 e/2";

    public static final String MESSAGE_UNASSIGN_SUCCESS = "Vendor %s unassigned from Event %s";

    private final Index vendorIndex;
    private final Index eventIndex;

    /**
     * Creates an UnassignCommand to unassign the specified {@code Vendor} from the
     * specified {@code Event}.
     */
    public UnassignCommand(Index vendorIndex, Index eventIndex) {
        requireNonNull(vendorIndex);
        requireNonNull(eventIndex);
        this.vendorIndex = vendorIndex;
        this.eventIndex = eventIndex;
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
        return vendorIndex.equals(otherCommand.vendorIndex)
                && eventIndex.equals(otherCommand.eventIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ObservableValue<UiState> uiState = model.getUiState();
        switch (uiState.getValue()) {
        case VENDOR_DETAILS:
            return handleVendorDetailsView(model);
        case EVENT_DETAILS:
            return handleEventDetailsView(model);
        default:
            return handleMainView(model);
        }
    }

    private CommandResult handleMainView(Model model) throws CommandException {
        List<Vendor> vendorList = model.getFilteredVendorList();
        List<Event> eventList = model.getFilteredEventList();

        Vendor vendor = getVendorByIndex(vendorList, vendorIndex);
        Event event = getEventByIndex(eventList, eventIndex);

        if (!model.isVendorAssignedToEvent(vendor, event)) {
            throw new CommandException(Messages.MESSAGE_VENDOR_NOT_ASSIGNED_TO_EVENT);
        }

        model.unassignVendorFromEvent(vendor, event);

        return new CommandResult(
                String.format(MESSAGE_UNASSIGN_SUCCESS, vendorIndex.getOneBased(), eventIndex.getOneBased()));
    }

    private CommandResult handleVendorDetailsView(Model model) throws CommandException {
        Vendor vendor = model.getViewedVendor().getValue();
        List<Vendor> vendorList = model.getFilteredVendorList();

        Vendor vendorToUnassign = getVendorByIndex(vendorList, vendorIndex);

        if (!vendor.equals(vendorToUnassign)) {
            // Treat as if the user is in main view, to unassign other vendor from main list indexed event
            // Or should it throw an error?
            return handleMainView(model);
        }

        Event event = getEventByIndex(model.getAssociatedEvents(vendor), eventIndex);

        assert model.isVendorAssignedToEvent(vendor, event) : "Vendor is not assigned to event although in association";

        model.unassignVendorFromEvent(vendor, event);

        return new CommandResult(
                String.format(MESSAGE_UNASSIGN_SUCCESS, vendor.getName(), event.getName()));
    }

    private CommandResult handleEventDetailsView(Model model) throws CommandException {
        Event event = model.getViewedEvent().getValue();
        List<Event> eventList = model.getFilteredEventList();

        Event eventToUnassign = getEventByIndex(eventList, eventIndex);

        if (!event.equals(eventToUnassign)) {
            // Treat as if the user is in main view, to unassign other event from main list indexed vendor
            // Or should it throw an error?
            return handleMainView(model);
        }

        Vendor vendor = getVendorByIndex(model.getAssociatedVendors(event), vendorIndex);

        assert model.isVendorAssignedToEvent(vendor, event) : "Vendor is not assigned to event although in association";

        model.unassignVendorFromEvent(vendor, event);

        return new CommandResult(
                String.format(MESSAGE_UNASSIGN_SUCCESS, vendor.getName(), event.getName()));
    }

    private Vendor getVendorByIndex(List<Vendor> vendorList, Index index) throws CommandException {
        if (index.getZeroBased() >= vendorList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX);
        }
        return vendorList.get(index.getZeroBased());
    }

    private Event getEventByIndex(List<Event> eventList, Index index) throws CommandException {
        if (index.getZeroBased() >= eventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }
        return eventList.get(index.getZeroBased());
    }
}
