package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CONCERT_DISPLAYED_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteConcertCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteConcertCommand object
 */
public class DeleteConcertCommandParser implements Parser<DeleteConcertCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteConcertCommand and
     * returns a DeleteConcertCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteConcertCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args);

        if (argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteConcertCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndexWithMessage(args, MESSAGE_INVALID_CONCERT_DISPLAYED_INDEX);
        return new DeleteConcertCommand(index);
    }
}
