package seedu.address.logic.parser;

import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.vendor.Vendor;

import java.util.Optional;

/**
 * Parses input arguments and creates a new AssignCommand object.
 */
public class AssignCommandParser implements Parser<AssignCommand> {

    @Override
    public AssignCommand parse(String args, Model model) throws ParseException {
        String[] tokens = args.trim().split("\\s+");

        int vendorId = -1;
        int eventId = -1;

        // Parse tokens to find vendorId and eventId
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equalsIgnoreCase("vendor") && i + 1 < tokens.length) {
                try {
                    vendorId = Integer.parseInt(tokens[i + 1]);
                } catch (NumberFormatException e) {
                    throw new ParseException("Invalid vendor ID format. Vendor ID should be a number.");
                }
            } else if (tokens[i].equalsIgnoreCase("event") && i + 1 < tokens.length) {
                try {
                    eventId = Integer.parseInt(tokens[i + 1]);
                } catch (NumberFormatException e) {
                    throw new ParseException("Invalid event ID format. Event ID should be a number.");
                }
            }
        }

        // Check if both vendorId and eventId were provided
        if (vendorId == -1 || eventId == -1) {
            throw new ParseException(AssignCommand.MESSAGE_USAGE);
        }

        // Retrieve Vendor and Event objects from the model
        Optional<Vendor> optionalVendor = model.getVendorById(vendorId);
        Optional<Event> optionalEvent = model.getEventById(eventId);

        if (optionalVendor.isEmpty()) {
            throw new ParseException(AssignCommand.MESSAGE_INVALID_VENDOR_ID);
        }

        if (optionalEvent.isEmpty()) {
            throw new ParseException(AssignCommand.MESSAGE_INVALID_EVENT_ID);
        }

        Vendor vendor = optionalVendor.get();
        Event event = optionalEvent.get();

        return new AssignCommand(vendor, event);
    }
}

