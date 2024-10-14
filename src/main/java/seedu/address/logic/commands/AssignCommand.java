package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR_ID;

import java.util.List;
import java.util.stream.Stream;
import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.tag.Tag;
import seedu.address.model.vendor.Vendor;

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
        model.assignVendorToEvent(vendor, event);

        return new CommandResult(String.format(MESSAGE_ASSIGN_SUCCESS, vendorIndex.getOneBased(), eventIndex.getOneBased()));
    }
}

