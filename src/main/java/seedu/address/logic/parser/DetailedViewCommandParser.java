package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DetailedViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;



/**
 * Parses input arguments and creates a new DetailedViewCommand object
 */
public class DetailedViewCommandParser implements Parser<DetailedViewCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DetailedViewCommand
     * and returns a DetailedViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DetailedViewCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DetailedViewCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DetailedViewCommand.MESSAGE_USAGE), pe);
        }
    }
}
