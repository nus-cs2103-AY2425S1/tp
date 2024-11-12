package seedu.address.logic.parser.event.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.commands.FindEventCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new FindEventCommand object
 */
public class FindEventCommandParser implements Parser<FindEventCommand> {
    private static final Logger logger = Logger.getLogger(FindEventCommandParser.class.getName());
    /**
     * Parses the given {@code String userInput} and returns a {@code FindEventCommand} object
     * for execution.
     *
     * @param userInput The input provided by the user, expected to contain an event description.
     * @return A {@code FindEventCommand} that contains the parsed {@code Event}.
     * @throws ParseException If the user input does not conform to the expected format or
     *     contains an invalid event. The error message will follow the format specified by
     *     {@code FindEventCommand#MESSAGE_USAGE}.
     */
    public FindEventCommand parse(String userInput) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(userInput);
            logger.log(Level.INFO, "Successfully parsed event: {0}", index);
            return new FindEventCommand(index);
        } catch (ParseException pe) {
            logger.log(Level.WARNING, "ParseException encountered: {0}", pe.getMessage());
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE), pe);
        }
    }
}
