package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.logic.commands.event.commands.ViewEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;

/**
 * Parses input arguments and creates a new ViewEventCommand object
 */
public class ViewEventCommandParser implements Parser<ViewEventCommand> {
    private static final Logger logger = Logger.getLogger(ViewEventCommandParser.class.getName());
    /**
     * Parses the given {@code String userInput} and returns a {@code NewEventCommand} object
     * for execution.
     *
     * @param userInput The input provided by the user, expected to contain an event description.
     * @return A {@code NewEventCommand} that contains the parsed {@code Event}.
     * @throws ParseException If the user input does not conform to the expected format or
     *     contains an invalid event. The error message will follow the format specified by
     *     {@code NewEventCommand#MESSAGE_USAGE}.
     */
    public ViewEventCommand parse(String userInput) throws ParseException {
        try {
            Event event = ParserUtil.parseEvent(userInput);
            logger.log(Level.INFO, "Successfully parsed event: {0}", event);
            return new ViewEventCommand(event);
        } catch (ParseException pe) {
            logger.log(Level.WARNING, "ParseException encountered: {0}", pe.getMessage());
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewEventCommand.MESSAGE_USAGE), pe);
        }
    }
}
