package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FavouriteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input for the sort command.
 * The input should specify the order in which to sort the contacts (ascending or descending).
 */
public class FavouriteCommandParser implements Parser<FavouriteCommand> {

    /**
     * Parses the user input and returns a SortCommand object based on the specified order.
     *
     * @param args The input provided by the user. This should be either "asc" or "desc".
     * @return A SortCommand object with the specified sorting order.
     * @throws ParseException If the input is invalid or does not specify a valid sorting order.
     */
    @Override
    public FavouriteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (args.isEmpty()) {
            return new FavouriteCommand();
        }
        try {
            Index index = ParserUtil.parseIndex(args);
            return new FavouriteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE));
        }
    }
}
