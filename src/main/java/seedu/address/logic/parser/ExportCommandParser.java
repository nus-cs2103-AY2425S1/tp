package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class ExportCommandParser implements Parser<ExportCommand> {
    public static final String MESSAGE_INVALID_FILENAME =
            "Filename cannot contain periods or slashes. Please provide a simple filename.";
    private static final String INVALID_FILENAME_CHARS = "[./\\\\]";

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExportCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        boolean isForceExport = false;
        String filename = trimmedArgs;

        // Check if force flag is present
        if (trimmedArgs.equals(ExportCommand.FORCE_FLAG)
                || trimmedArgs.startsWith(ExportCommand.FORCE_FLAG + " ")) {
            isForceExport = true;
            // Extract filename after the force flag and space
            filename = trimmedArgs.equals(ExportCommand.FORCE_FLAG) ? ""
                    : trimmedArgs.substring(ExportCommand.FORCE_FLAG.length()).trim();
        }

        // Validate filename
        if (filename.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        if (filename.matches(".*" + INVALID_FILENAME_CHARS + ".*")) {
            throw new ParseException(MESSAGE_INVALID_FILENAME);
        }

        return new ExportCommand(filename, isForceExport);
    }
}
