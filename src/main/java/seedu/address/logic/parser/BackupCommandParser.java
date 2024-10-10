package seedu.address.logic.parser;

import seedu.address.logic.commands.BackupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new BackupCommand object
 */
public class BackupCommandParser implements Parser<BackupCommand> {

    /**
     * Parses the given {@code String} of arguments to create a new {@code BackupCommand} object.
     *
     * @param args User-provided arguments for the backup command.
     * @return A new BackupCommand object containing the parsed arguments.
     * @throws ParseException if the user input does not match the expected format.
     */
    @Override
    public BackupCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(BackupCommand.MESSAGE_USAGE);
        }
        return new BackupCommand(trimmedArgs);
    }
}
