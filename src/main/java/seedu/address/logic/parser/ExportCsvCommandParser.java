package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ExportCsvCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and create a new ExportCsvCommand object
 */
public class ExportCsvCommandParser implements Parser<ExportCsvCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCsvCommand
     * and returns a ExportCsvCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExportCsvCommand parse(String args) throws ParseException {
        String fileName = args;
        if (!isValidFileName(fileName)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCsvCommand.MESSAGE_USAGE));
        }
        return new ExportCsvCommand(fileName);
    }

    /**
     * Returns true if the parameter is in the valid csv filename format.
    */
    private static boolean isValidFileName(String fileName) {
        // todo: implement a regex check for filename
        return true;
    }
}
