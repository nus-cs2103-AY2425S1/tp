package seedu.address.logic.parser;

import java.nio.file.Path;
import java.util.Optional;

import seedu.address.logic.commands.RestoreCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RestoreCommand object.
 */
public class RestoreCommandParser implements Parser<RestoreCommand> {

    @Override
    public RestoreCommand parse(String args) throws ParseException {
        if (args.trim().isEmpty()) {
            return new RestoreCommand(Optional.empty()); // No file path provided, restore the most recent backup
        } else {
            // Restore from a specific file path
            Path backupPath = Path.of(args.trim());
            return new RestoreCommand(Optional.of(backupPath));
        }
    }
}
