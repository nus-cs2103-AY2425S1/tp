package seedu.eventtory.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.eventtory.commons.core.index.Index;
import seedu.eventtory.logic.Messages;
import seedu.eventtory.logic.commands.exceptions.CommandException;
import seedu.eventtory.model.Model;
import seedu.eventtory.model.event.Event;
import seedu.eventtory.model.vendor.Vendor;

/**
 * Assigns a vendor to an event.
 */
public class AssignCommand extends Command {

    public static final String COMMAND_WORD = "assign";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns a vendor to an event. "
            + "Parameters: "
            + "v/<VENDOR_INDEX> e/<EVENT_INDEX> or e/<EVENT_INDEX> v/<VENDOR_INDEX>\n"
            + "Example: " + COMMAND_WORD + " v/1 e/2";

    public static final String MESSAGE_ASSIGN_SUCCESS = "Vendor %s assigned to Event %s";

    private final Index vendorIndex;
    private final Index eventIndex;

    /**
     * Creates an AssignCommand to assign the specified {@code Vendor} to the specified {@code Event}.
     */
    public AssignCommand(Index vendorIndex, Index eventIndex) {
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
        if (!(other instanceof AssignCommand)) {
            return false;
        }

        AssignCommand otherCommand = (AssignCommand) other;
        return vendorIndex.equals(otherCommand.vendorIndex)
                && eventIndex.equals(otherCommand.eventIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Vendor> vendorList = model.getFilteredVendorList();
        List<Event> eventList = model.getFilteredEventList();

        if (vendorIndex.getZeroBased() >= vendorList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX);
        }

        if (eventIndex.getZeroBased() >= eventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Vendor vendor = vendorList.get(vendorIndex.getZeroBased());
        Event event = eventList.get(eventIndex.getZeroBased());

        if (model.isVendorAssignedToEvent(vendor, event)) {
            throw new CommandException(Messages.MESSAGE_VENDOR_ALREADY_ASSIGNED);
        }

        model.assignVendorToEvent(vendor, event);

        return new CommandResult(
                String.format(MESSAGE_ASSIGN_SUCCESS,
                        vendorIndex.getOneBased(),
                        eventIndex.getOneBased()));
    }
}

