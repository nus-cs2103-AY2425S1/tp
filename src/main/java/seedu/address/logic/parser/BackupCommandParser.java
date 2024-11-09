package seedu.address.logic.parser;

import seedu.address.logic.commands.BackupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments to create a new {@code BackupCommand} object.
 * <p>
 * Allows an optional action description for custom naming of the backup.
 * Ensures that the description does not exceed a specified maximum length to prevent filename issues.
 * </p>
 */
public class BackupCommandParser implements Parser<BackupCommand> {

    private static final int MAX_DESCRIPTION_LENGTH = 250; // Arbitrary limit for action description

    /**
     * Parses the given {@code String} of arguments and returns a {@code BackupCommand} object for execution.
     * If no description is provided, a default naming convention will be used by the {@code BackupCommand}.
     *
     * @param args The user input arguments.
     * @return A {@code BackupCommand} with the specified or default action description.
     * @throws ParseException If the description exceeds the maximum allowed length.
     */
    @Override
    public BackupCommand parse(String args) throws ParseException {
        String actionDescription = args.trim();
        if (actionDescription.length() > MAX_DESCRIPTION_LENGTH) {
            throw new ParseException("Failed to create backup!! Backup file name exceeds the maximum length of "
                    + MAX_DESCRIPTION_LENGTH
                    + " characters. Please shorten your description.");
        }
        if (actionDescription.isEmpty()) {
            actionDescription = null; // Use null to signal default naming in BackupCommand
        }
        return new BackupCommand(actionDescription);
    }
}
