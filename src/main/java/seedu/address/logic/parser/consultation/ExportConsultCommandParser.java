package seedu.address.logic.parser.consultation;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.consultation.ExportConsultCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportConsultCommand object
 */
public class ExportConsultCommandParser implements Parser<ExportConsultCommand> {

    public static final String MESSAGE_INVALID_FILENAME =
            "Filename cannot contain periods or slashes. Please provide a simple filename.";
    private static final String INVALID_FILENAME_CHARS = "[./\\\\]";

    /**
     * Parses the given {@code String} of arguments in the context of the ExportConsultCommand
     * and returns an ExportConsultCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ExportConsultCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportConsultCommand.MESSAGE_USAGE));
        }

        boolean isForceExport = false;
        String filename = trimmedArgs;

        // Check if force flag is present
        if (trimmedArgs.equals(ExportConsultCommand.FORCE_FLAG)
                || trimmedArgs.startsWith(ExportConsultCommand.FORCE_FLAG + " ")) {
            isForceExport = true;
            // Extract filename after the force flag and space
            filename = trimmedArgs.equals(ExportConsultCommand.FORCE_FLAG) ? ""
                    : trimmedArgs.substring(ExportConsultCommand.FORCE_FLAG.length()).trim();
        }

        // Validate filename
        if (filename.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportConsultCommand.MESSAGE_USAGE));
        }

        if (filename.matches(".*" + INVALID_FILENAME_CHARS + ".*")) {
            throw new ParseException(MESSAGE_INVALID_FILENAME);
        }

        return new ExportConsultCommand(filename, isForceExport);
    }
}
