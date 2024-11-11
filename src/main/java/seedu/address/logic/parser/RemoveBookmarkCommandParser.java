package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveBookmarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the input arguments and creates a new RemoveBookmarkCommand object
 */
public class RemoveBookmarkCommandParser implements Parser<RemoveBookmarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveBookmarkCommand
     * and returns a RemoveBookmarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveBookmarkCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RemoveBookmarkCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveBookmarkCommand.MESSAGE_USAGE), pe);
        }
    }
}
