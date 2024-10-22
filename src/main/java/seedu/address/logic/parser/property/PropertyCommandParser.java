package seedu.address.logic.parser.property;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.property.ViewCommand;
import seedu.address.logic.parser.CommandParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses commands and arguments for Property-type commands.
 */
public class PropertyCommandParser extends CommandParser {

    private static final Logger logger = LogsCenter.getLogger(PropertyCommandParser.class);

    @Override
    public Command parseCommand(String commandWord, String arguments) throws ParseException {

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case ViewCommand.COMMAND_WORD:
            return new ViewCommand();

        default:
            return super.parseCommand(commandWord, arguments);
        }
    }

}
