package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteGameCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new DeleteGameCommand object.
 */
public class DeleteGameCommandParser implements Parser<DeleteGameCommand> {

    @Override
    public DeleteGameCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GAME);
        Index index;
        String gameName;
        if (!argMultimap.getValue(PREFIX_GAME).isPresent()) {
            throw new ParseException("Please specify a game!");
        }

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            gameName = argMultimap.getValue(PREFIX_GAME).get();
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteGameCommand.MESSAGE_USAGE), pe);
        }

        return new DeleteGameCommand(index, gameName);
    }
}
