package seedu.address.logic.parser;

import seedu.address.logic.commands.RestoreCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RestoreCommand object.
 */
public class RestoreCommandParser implements Parser<RestoreCommand> {

    @Override
    public RestoreCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException("Index is required for restore command.\n" + RestoreCommand.MESSAGE_USAGE);
        }

        try {
            int index = Integer.parseInt(trimmedArgs);
            if (index < 0 || index >= 10) {
                throw new ParseException("Index must be between 0 and 9.\n" + RestoreCommand.MESSAGE_USAGE);
            }
            return new RestoreCommand(index);
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid index: " + trimmedArgs + "\n" + RestoreCommand.MESSAGE_USAGE);
        }
    }
}
