package seedu.address.logic.parser.eventcommandparser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.eventcommands.EventFilterCommand;
import seedu.address.logic.parser.EventParserUtil;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code EventFilterCommand} object.
 */
public class EventFilterCommandParser implements Parser<EventFilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EventFilterCommand
     * and returns a EventFilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EventFilterCommand parse(String args) throws ParseException {

        try {
            Index index = EventParserUtil.parseIndex(args);
            return new EventFilterCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventFilterCommand.MESSAGE_USAGE), pe);
        }

    }

}

