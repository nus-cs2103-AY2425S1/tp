package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RsvpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;


/**
 * Parses input arguments and creates a new RsvpCommand object
 */
public class RsvpCommandParser implements Parser<RsvpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RsvpCommand
     * @param args
     * @return
     * @throws ParseException
     */
    public RsvpCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RsvpCommand.MESSAGE_USAGE));
        }


        // Split the trimmedArgs into commandWord and index
        String[] splitArgs = trimmedArgs.split("\\s+");
        if (splitArgs.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RsvpCommand.MESSAGE_USAGE));
        }

        // Check if the index is a valid number
        String commandWord = splitArgs[0];
        Index index;
        try {
            index = ParserUtil.parseIndex(splitArgs[1]);
        } catch (ParseException pe) {
            throw new ParseException("ERROR: Please enter a valid index (from 1 to [list length])");
        }

        boolean isRsvp = commandWord.equals(RsvpCommand.COMMAND_WORD);
        return new RsvpCommand(index, isRsvp);
    }
}
