package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkpaidCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkpaidCommand object
 */
public class MarkpaidCommandParser implements Parser<MarkpaidCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkpaidCommand
     * and returns a MarkpaidCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkpaidCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MarkpaidCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkpaidCommand.MESSAGE_USAGE), pe);
        }
    }
}
