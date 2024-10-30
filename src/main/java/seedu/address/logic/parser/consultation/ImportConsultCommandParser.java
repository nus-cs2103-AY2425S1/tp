package seedu.address.logic.parser.consultation;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.consultation.ImportConsultCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ImportConsultCommand object
 */
public class ImportConsultCommandParser implements Parser<ImportConsultCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ImportConsultCommand
     * and returns an ImportConsultCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ImportConsultCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportConsultCommand.MESSAGE_USAGE)
            );
        }

        // Validate that the path doesn't try to access project directory
        if (trimmedArgs.contains("..") || trimmedArgs.startsWith("/") || trimmedArgs.startsWith("./")) {
            throw new ParseException(ImportConsultCommand.MESSAGE_FILE_OUTSIDE_PROJECT);
        }

        return new ImportConsultCommand(trimmedArgs);
    }
}
