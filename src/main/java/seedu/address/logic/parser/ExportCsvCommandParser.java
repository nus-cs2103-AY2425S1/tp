package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ExportCsvCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and create a new ExportCsvCommand object
 */
public class ExportCsvCommandParser implements Parser<ExportCsvCommand> {
    
    public ExportCsvCommand parse(String args) throws ParseException {
        String fileName = args;
        if (!isValidFileName(fileName)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCsvCommand.MESSAGE_USAGE));
        }
        return new ExportCsvCommand(fileName);
    }

    private static boolean isValidFileName(String fileName) {
        return true;
    }
}
