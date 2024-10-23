package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListLogsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.IdentityNumber;

/**
 * Parses input arguments and creates a new ListLogsCommand object
 */
public class ListLogsParser implements Parser<ListLogsCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListLogsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        // Ensure the input is exactly 11 characters long
        // eg: i/SxxxxxxxA
        if (trimmedArgs.length() != 11) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListLogsCommand.MESSAGE_USAGE));
        }

        // Extract the 9-character field after "/i"
        String arg = trimmedArgs.substring(2);
        IdentityNumber id;

        try {
            // Try to initialise id
            id = new IdentityNumber(arg);
        } catch (IllegalArgumentException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListLogsCommand.MESSAGE_USAGE));
        }

        return new ListLogsCommand(id);
    }
}
