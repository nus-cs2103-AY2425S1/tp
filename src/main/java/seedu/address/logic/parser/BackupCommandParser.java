package seedu.address.logic.parser;

import seedu.address.logic.commands.BackupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new BackupCommand object.
 * Supports an optional action description argument for flexibility in naming backups.
 */
public class BackupCommandParser implements Parser<BackupCommand> {

    @Override
    public BackupCommand parse(String args) throws ParseException {
        String actionDescription = args.trim();
        if (actionDescription.isEmpty()) {
            actionDescription = null; // Use null to signal default naming in BackupCommand
        }
        return new BackupCommand(actionDescription);
    }
}
