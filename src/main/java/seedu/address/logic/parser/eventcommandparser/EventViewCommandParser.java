package seedu.address.logic.parser.eventcommandparser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.eventcommands.EventViewCommand;
import seedu.address.logic.parser.EventParserUtil;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code EventViewCommand} object.
 */
public class EventViewCommandParser implements Parser<EventViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EventViewCommand
     * and returns a EventViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EventViewCommand parse(String args) throws ParseException {

        try {
            Index index = EventParserUtil.parseIndex(args);
            return new EventViewCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventViewCommand.MESSAGE_USAGE), pe);
        }

    }

}
