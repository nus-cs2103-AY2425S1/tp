package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CONCERT_CONTACT_DISPLAYED_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteConcertContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteConcertContactCommand object
 */
public class DeleteConcertContactCommandParser implements Parser<DeleteConcertContactCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteConcertContactCommand
     * and returns an DeleteConcertContactCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteConcertContactCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args);

        if (argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteConcertContactCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndexWithMessage(args, MESSAGE_INVALID_CONCERT_CONTACT_DISPLAYED_INDEX);
        return new DeleteConcertContactCommand(index);
    }

}
