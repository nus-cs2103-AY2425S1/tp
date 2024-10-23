package seedu.internbuddy.logic.parser;

import static seedu.internbuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.internbuddy.commons.core.index.Index;
import seedu.internbuddy.logic.commands.FavCommand;
import seedu.internbuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FavCommand object
 */
public class FavCommandParser implements Parser<FavCommand> {

    @Override
    public FavCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new FavCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavCommand.MESSAGE_USAGE), pe);
        }
    }
}
