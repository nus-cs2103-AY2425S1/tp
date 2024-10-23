package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PinContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PinContactCommand object
 */
public class PinContactCommandParser implements Parser<PinContactCommand> {

    private static Logger logger = LogsCenter.getLogger(PinContactCommandParser.class);
    /**
     * Parses the given {@code String} of arguments in the context of the PinContactCommand
     * and returns a PinContactCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PinContactCommand parse(String args) throws ParseException {
        logger.fine("Command word: " + PinContactCommand.COMMAND_WORD + "; Arguments: " + args);

        try {
            Index index = ParserUtil.parseIndex(args);
            logger.fine("Parsed index:" + index);
            return new PinContactCommand(index);
        } catch (ParseException pe) {
            logger.fine("Failed parsing for input:" + args);
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PinContactCommand.MESSAGE_USAGE), pe);
        }
    }

}
