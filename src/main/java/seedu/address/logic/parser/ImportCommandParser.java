package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ImportCommandParser implements Parser<ImportCommand> {
    @Override
    public ImportCommand parse(String args) throws ParseException {
        try {
            String fileName = ParserUtil.parseFileName(args);
            return new ImportCommand(fileName);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, "lol"));
        }
    }
}
