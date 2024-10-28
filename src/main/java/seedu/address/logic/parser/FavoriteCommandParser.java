package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FavoriteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FavoriteCommand object.
 */
public class FavoriteCommandParser implements Parser<FavoriteCommand> {

    /**
     * Parses the given arguments and returns a FavoriteCommand.
     *
     * @param args The input arguments provided by the user.
     * @return A FavoriteCommand object based on the parsed index.
     * @throws ParseException If the arguments are in an invalid format or if parsing fails.
     */
    @Override
    public FavoriteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            Index index = ParserUtil.parseIndex(args.trim());
            return new FavoriteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavoriteCommand.MESSAGE_USAGE), pe);
        }
    }
}
