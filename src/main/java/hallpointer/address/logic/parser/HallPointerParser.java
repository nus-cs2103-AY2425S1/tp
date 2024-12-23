package hallpointer.address.logic.parser;

import static hallpointer.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hallpointer.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hallpointer.address.commons.core.LogsCenter;
import hallpointer.address.logic.commands.AddMemberCommand;
import hallpointer.address.logic.commands.AddSessionCommand;
import hallpointer.address.logic.commands.ClearCommand;
import hallpointer.address.logic.commands.Command;
import hallpointer.address.logic.commands.DeleteMemberCommand;
import hallpointer.address.logic.commands.DeleteSessionCommand;
import hallpointer.address.logic.commands.ExitCommand;
import hallpointer.address.logic.commands.FindMemberCommand;
import hallpointer.address.logic.commands.FindSessionCommand;
import hallpointer.address.logic.commands.HelpCommand;
import hallpointer.address.logic.commands.ListCommand;
import hallpointer.address.logic.commands.UpdateMemberCommand;
import hallpointer.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class HallPointerParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(HallPointerParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        // commandWord should not be case-sensitive
        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddMemberCommand.COMMAND_WORD:
            return new AddMemberCommandParser().parse(arguments);

        case UpdateMemberCommand.COMMAND_WORD:
            return new UpdateMemberCommandParser().parse(arguments);

        case DeleteMemberCommand.COMMAND_WORD:
            return new DeleteMemberCommandParser().parse(arguments);

        case AddSessionCommand.COMMAND_WORD:
            return new AddSessionCommandParser().parse(arguments);

        case DeleteSessionCommand.COMMAND_WORD:
            return new DeleteSessionCommandParser().parse(arguments);

        case FindSessionCommand.COMMAND_WORD:
            return new FindSessionCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindMemberCommand.COMMAND_WORD:
            return new FindMemberCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
