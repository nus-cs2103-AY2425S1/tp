package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RsvpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;

/**
 * Parses input arguments and creates a new RsvpCommand object
 */
public class RsvpCommandParser implements Parser<RsvpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RsvpCommand
     */
    public RsvpCommand parse(String args) throws ParseException {
        // Split the trimmedArgs into commandWord and index
        String[] splitArgs = args.split("\\s+");
        if (splitArgs.length != 2) {
            throw new ParseException(RsvpCommand.MESSAGE_INVALID_FORMAT);
        }

        String commandWord = splitArgs[0];
        Index index;
        try {
            index = ParserUtil.parseIndex(splitArgs[1]);
            boolean isRsvp = commandWord.equals(RsvpCommand.RSVP_COMMAND_WORD);
            return new RsvpCommand(index, isRsvp);
        } catch (ParseException pe) {
            throw new ParseException(RsvpCommand.MESSAGE_INVALID_INDEX);
        }

    }
}
