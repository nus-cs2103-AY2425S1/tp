package seedu.address.logic.parser;

import seedu.address.logic.commands.BackupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments to create a new BackupCommand object.
 * Allows an optional action description for custom naming of the backup.
 */
public class BackupCommandParser implements Parser<BackupCommand> {

    /**
     * Parses the given {@code String} of arguments and returns a BackupCommand object for execution.
     * If no description is provided, a default naming convention will be used by the BackupCommand.
     *
     * @param args The user input arguments.
     * @return A BackupCommand with the specified or default action description.
     * @throws ParseException If an error occurs while parsing the arguments.
     */
    @Override
    public BackupCommand parse(String args) throws ParseException {
        String actionDescription = args.trim();
        if (actionDescription.isEmpty()) {
            actionDescription = null; // Use null to signal default naming in BackupCommand
        }
        return new BackupCommand(actionDescription);
    }
}
