package seedu.address.logic.parser.eventcommandparser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.eventcommands.EventDeleteCommand;
import seedu.address.logic.commands.eventcommands.EventFilterCommand;
import seedu.address.logic.commands.eventcommands.EventNewCommand;
import seedu.address.logic.commands.eventcommands.EventViewCommand;
import seedu.address.logic.commands.eventcommands.FindEventCommand;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input for event-related commands.
 */
public class EventCommandParser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses the given user input and returns the appropriate command.
     *
     * @param userInput The full user input string to parse.
     * @return The corresponding command to execute.
     * @throws ParseException If the input does not match the expected format.
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {
        case EventNewCommand.COMMAND_WORD:
            return new EventNewCommandParser().parse(arguments);
        case EventDeleteCommand.COMMAND_WORD:
            return new EventDeleteCommandParser().parse(arguments);
        case FindEventCommand.COMMAND_WORD:
            return new FindEventCommandParser().parse(arguments);
        case EventViewCommand.COMMAND_WORD:
            return new EventViewCommandParser().parse(arguments);
        case EventFilterCommand.COMMAND_WORD:
            return new EventFilterCommandParser().parse(arguments);
        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }


}
