package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;


/**
 * Parses input arguments and creates a new AppointmentCommand object.
 */
public class AppointmentCommandParser implements Parser<AppointmentCommand> {

    private static final String APPOINTMENT_PREFIX = "a/";

    /**
     * Parses the given {@code String} of arguments in the context of the AppointmentCommand
     * and returns an AppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public AppointmentCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        // Split the arguments into components
        String[] argParts = trimmedArgs.split("\\s+");

        // Check if the input has exactly 3 parts: INDEX, a/DATE, TIME
        if (argParts.length < 3 || !argParts[1].startsWith(APPOINTMENT_PREFIX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
        }

        // Parse the index
        String indexString = argParts[0];
        Index index;
        try {
            int indexValue = Integer.parseInt(indexString);
            if (indexValue <= 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
            }
            index = Index.fromZeroBased(indexValue - 1); // Adjust to zero-based index
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
        }

        // Extract and validate the appointment date and time
        String appointmentDateTime = trimmedArgs.substring(trimmedArgs.indexOf(APPOINTMENT_PREFIX) + 2).trim();

        // If appointmentDateTime is not valid, throw an error
        if (appointmentDateTime.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
        }

        // Return the constructed AppointmentCommand
        return new AppointmentCommand(index, appointmentDateTime);
    }
}
