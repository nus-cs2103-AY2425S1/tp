package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.CloseWindowCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CloseWindowCommand object
 */
public class CloseWindowCommandParser implements Parser<CloseWindowCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the CloseWindowCommand
     */
    public CloseWindowCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CloseWindowCommand.MESSAGE_USAGE));
        }
        return new CloseWindowCommand();
    }
}
