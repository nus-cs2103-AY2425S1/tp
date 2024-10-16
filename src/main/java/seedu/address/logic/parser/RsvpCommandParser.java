package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RsvpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RsvpCommand object
 */
public class RsvpCommandParser implements Parser<RsvpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RsvpCommand
     */
    public RsvpCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RsvpCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RsvpCommand.MESSAGE_USAGE), pe);
        }
    }
}
