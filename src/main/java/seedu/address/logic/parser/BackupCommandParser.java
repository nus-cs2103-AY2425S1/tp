package seedu.address.logic.parser;

import seedu.address.logic.commands.BackupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new BackupCommand object.
 * Supports an optional file name argument for flexibility in naming backups.
 */
public class BackupCommandParser implements Parser<BackupCommand> {

    @Override
    public BackupCommand parse(String args) throws ParseException {
        String fileName = args.trim();
        if (fileName.isEmpty()) {
            fileName = null; // Use null to signal default naming in BackupCommand
        }
        return new BackupCommand(fileName);
    }
}
