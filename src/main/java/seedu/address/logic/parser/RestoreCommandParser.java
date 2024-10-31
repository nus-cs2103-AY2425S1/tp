package seedu.address.logic.parser;

import seedu.address.logic.commands.RestoreCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments to create a new RestoreCommand object.
 * Expects an index argument to identify the specific backup to restore.
 */
public class RestoreCommandParser implements Parser<RestoreCommand> {

    /**
     * Parses the given {@code String} of arguments and returns a RestoreCommand object for execution.
     * Ensures the index argument is a valid integer within the expected range.
     *
     * @param args The user input arguments.
     * @return A RestoreCommand initialized with the parsed index.
     * @throws ParseException If the index is missing, out of range, or not a valid integer.
     */
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
