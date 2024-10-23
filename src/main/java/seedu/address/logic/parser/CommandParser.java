package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.SwitchParserModeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Abstract class with default commands.
 */
public abstract class CommandParser {

    private static final Logger logger = LogsCenter.getLogger(CommandParser.class);

    /**
     * Parses the commandWord and arguments for execution.
     *
     * @param commandWord specifies type of command
     * @param arguments the arguments for the specified command
     * @return the command based on the commandWord and arguments
     * @throws ParseException if the command does not exist or is invalid
     */
    public Command parseCommand(String commandWord, String arguments) throws ParseException {

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case SwitchParserModeCommand.COMMAND_WORD:
            return new SwitchParserModeCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + commandWord);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
