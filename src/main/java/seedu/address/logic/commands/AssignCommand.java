package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.vendor.Vendor;

public class AssignCommand extends Command {
    public static final String COMMAND_WORD = "assign";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns a vendor to an event. "
            + "Parameters: "
            + "v/<VENDOR_ID> e/<EVENT_ID> or e/<EVENT_ID> v/<VENDOR_ID>\n"
            + "Example: " + COMMAND_WORD + " v/123 e/456";

    public static final String MESSAGE_ASSIGN_SUCCESS = "Vendor %s assigned to Event %s";
    public static final String MESSAGE_INVALID_VENDOR_ID = "The vendor ID provided is invalid.";
    public static final String MESSAGE_INVALID_EVENT_ID = "The event ID provided is invalid.";

    private final Vendor vendor;
    private final Event event;

    public AssignCommand(Vendor vendor, Event event) {
        requireNonNull(vendor);
        requireNonNull(event);
        this.vendor = vendor;
        this.event = event;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.assignVendorToEvent(vendor, event);
        return new CommandResult(String.format(MESSAGE_ASSIGN_SUCCESS, vendor.getVendorId(), event.getEventId()));
    }

    public static AssignCommand parse(String arguments, Model model) throws ParseException {
        requireNonNull(model);
        String[] tokens = arguments.trim().split("\\s+");

        int vendorId = -1;
        int eventId = -1;

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].startsWith("v/")) {
                try {
                    vendorId = Integer.parseInt(tokens[i].substring(2));
                } catch (NumberFormatException e) {
                    throw new ParseException(MESSAGE_INVALID_VENDOR_ID);
                }
            } else if (tokens[i].startsWith("e/")) {
                try {
                    eventId = Integer.parseInt(tokens[i].substring(2));
                } catch (NumberFormatException e) {
                    throw new ParseException(MESSAGE_INVALID_EVENT_ID);
                }
            }
        }

        if (vendorId == -1 || eventId == -1) {
            throw new ParseException(MESSAGE_USAGE);
        }

        // Retrieve Vendor and Event objects from the model
        Vendor vendor = model.getVendorById(vendorId)
                .orElseThrow(() -> new ParseException(MESSAGE_INVALID_VENDOR_ID));
        Event event = model.getEventById(eventId)
                .orElseThrow(() -> new ParseException(MESSAGE_INVALID_EVENT_ID));

        return new AssignCommand(vendor, event);
    }
}

