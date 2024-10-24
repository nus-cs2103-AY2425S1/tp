package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnfavouriteGameCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses nputs arguments and creates a new UnfavouriteGameCommand object
 */
public class UnfavouriteGameCommandParser implements Parser<UnfavouriteGameCommand> {

    @Override
    public UnfavouriteGameCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GAME);

        Index index;
        String gameName;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException("invalid cmd");
        }

        gameName = argMultimap.getValue(PREFIX_GAME).orElse("");

        return new UnfavouriteGameCommand(index, gameName);
    }
}
