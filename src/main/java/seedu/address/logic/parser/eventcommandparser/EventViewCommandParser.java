package seedu.address.logic.parser.eventcommandparser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.eventcommands.EventDeleteCommand;
import seedu.address.logic.commands.eventcommands.EventViewCommand;
import seedu.address.logic.parser.EventParserUtil;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class EventViewCommandParser implements Parser<EventViewCommand> {

    public EventViewCommand parse(String args) throws ParseException {

        try {
            Index index = EventParserUtil.parseIndex(args);
            return new EventViewCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventDeleteCommand.MESSAGE_USAGE), pe);
        }

    }

}
