package seedu.address.logic.parser.event.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.commands.EventAddAllCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input to create a new EventAddAllCommand object.
 */
public class EventAddAllCommandParser implements Parser<EventAddAllCommand> {
    /**
     * Parses the given {@code String} of user input in the context of the EventAddAllCommand
     * and returns an EventAddAllCommand object for execution.
     *
     * @param userInput The user input string to parse.
     * @return EventAddAllCommand object based on the parsed index.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public EventAddAllCommand parse(String userInput) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(userInput);
            return new EventAddAllCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventAddAllCommand.MESSAGE_USAGE), pe);
        }
    }
}
