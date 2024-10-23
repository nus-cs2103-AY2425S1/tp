package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewWeddingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewWeddingCommand object.
 */
public class ViewWeddingCommandParser implements Parser<ViewWeddingCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ViewWeddingCommand
     * and returns a ViewWeddingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewWeddingCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewWeddingCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewWeddingCommand.MESSAGE_USAGE), pe);
        }
    }
}
