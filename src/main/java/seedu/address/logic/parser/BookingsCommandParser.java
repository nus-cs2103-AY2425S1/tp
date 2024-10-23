package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.BookingsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Parses input arguments and creates a new BookingsCommand object
 */
public class BookingsCommandParser implements Parser<BookingsCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the BookingsCommand
     * and returns a BookingsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BookingsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BookingsCommand.MESSAGE_USAGE));
        }

        return new BookingsCommand(ParserUtil.parseDate(trimmedArgs));
    }
}
