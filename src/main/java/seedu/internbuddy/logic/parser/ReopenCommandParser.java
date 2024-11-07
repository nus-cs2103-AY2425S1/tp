package seedu.internbuddy.logic.parser;

import static seedu.internbuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.internbuddy.commons.core.index.Index;
import seedu.internbuddy.logic.commands.ReopenCommand;
import seedu.internbuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ReopenCommand object
 */
public class ReopenCommandParser implements Parser<ReopenCommand> {

    @Override
    public ReopenCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ReopenCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReopenCommand.MESSAGE_USAGE), pe);
        }
    }

}
