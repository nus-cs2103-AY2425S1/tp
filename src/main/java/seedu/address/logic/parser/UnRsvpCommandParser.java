package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RsvpCommand;
import seedu.address.logic.commands.UnRsvpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new RsvpCommand object
 */
public class UnRsvpCommandParser implements Parser<UnRsvpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RsvpCommand
     */
    public UnRsvpCommand parse(String args) throws ParseException {

        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnRsvpCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnRsvpCommand.MESSAGE_USAGE), pe);
        }
    }
}
