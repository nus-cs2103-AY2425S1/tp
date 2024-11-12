package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.UnassignCommand;
import seedu.address.logic.parser.eventcommandparser.EventCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.volunteercommandparser.VolunteerCommandParser;

/**
 * Parses user input.
 */

public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    public static final String EVENT_COMMAND_INDICATOR = "/e";
    public static final String VOLUNTEER_COMMAND_INDICATOR = "/v";
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
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
        case EVENT_COMMAND_INDICATOR:
            return new EventCommandParser().parseCommand(arguments);

        case VOLUNTEER_COMMAND_INDICATOR:
            return new VolunteerCommandParser().parseCommand(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case AssignCommand.COMMAND_WORD:
            return new AssignCommandParser().parseCommand(arguments);

        case UnassignCommand.COMMAND_WORD:
            return new UnassignCommandParser().parseCommand(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ExportCommand.COMMAND_WORD:
            return new ExportCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
