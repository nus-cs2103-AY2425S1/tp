package seedu.internbuddy.logic.parser;

import static seedu.internbuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.internbuddy.commons.core.index.Index;
import seedu.internbuddy.logic.commands.UnfavCommand;
import seedu.internbuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FavCommand object
 */
public class UnfavCommandParser implements Parser<UnfavCommand> {

    @Override
    public UnfavCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnfavCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnfavCommand.MESSAGE_USAGE), pe);
        }
    }
}
