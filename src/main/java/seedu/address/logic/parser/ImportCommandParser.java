package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code ImportCommand} object.
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code ImportCommand}
     * and returns an {@code ImportCommand} object for execution.
     *
     * @param args the arguments provided by the user.
     * @return an {@code ImportCommand} object.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public ImportCommand parse(String args) throws ParseException {
        try {
            String fileName = ParserUtil.parseFileName(args);
            return new ImportCommand(fileName);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }
    }
}
