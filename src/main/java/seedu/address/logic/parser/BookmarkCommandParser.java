package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BookmarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the input arguments and creates a new BookmarkCommand object
 */
public class BookmarkCommandParser implements Parser<BookmarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BookmarkCommand
     * and returns a BookmarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BookmarkCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new BookmarkCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BookmarkCommand.MESSAGE_USAGE), pe);
        }
    }
}
