package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnpinContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnpinContactCommand object
 */
public class UnpinContactCommandParser implements Parser<UnpinContactCommand> {

    private static Logger logger = LogsCenter.getLogger(UnpinContactCommandParser.class);
    /**
     * Parses the given {@code String} of arguments in the context of the UnpinContactCommand
     * and returns a UnpinContactCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnpinContactCommand parse(String args) throws ParseException {
        logger.fine("Command word: " + UnpinContactCommand.COMMAND_WORD + "; Arguments: " + args);

        try {
            Index index = ParserUtil.parseIndex(args);
            logger.fine("Parsed index:" + index);
            return new UnpinContactCommand(index);
        } catch (ParseException pe) {
            logger.fine("Failed parsing for input:" + args);
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnpinContactCommand.MESSAGE_USAGE), pe);
        }
    }

}
