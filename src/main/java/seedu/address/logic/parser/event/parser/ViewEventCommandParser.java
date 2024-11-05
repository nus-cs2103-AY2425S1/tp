package seedu.address.logic.parser.event.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.commands.ViewEventCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new ViewEventCommand object
 */
public class ViewEventCommandParser implements Parser<ViewEventCommand> {
    private static final Logger logger = Logger.getLogger(ViewEventCommandParser.class.getName());
    /**
     * Parses the given {@code String userInput} and returns a {@code ViewEventCommand} object
     * for execution.
     *
     * @param userInput The input provided by the user, expected to contain an event description.
     * @return A {@code ViewEventCommand} that contains the parsed {@code Event}.
     * @throws ParseException If the user input does not conform to the expected format or
     *     contains an invalid event. The error message will follow the format specified by
     *     {@code ViewEventCommand#MESSAGE_USAGE}.
     */
    public ViewEventCommand parse(String userInput) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(userInput);
            logger.log(Level.INFO, "Successfully parsed event: {0}", index);
            return new ViewEventCommand(index);
        } catch (ParseException pe) {
            logger.log(Level.WARNING, "ParseException encountered: {0}", pe.getMessage());
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewEventCommand.MESSAGE_USAGE), pe);
        }
    }
}
