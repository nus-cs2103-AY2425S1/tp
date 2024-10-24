package keycontacts.logic.parser;

import static keycontacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static keycontacts.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import keycontacts.commons.core.LogsCenter;
import keycontacts.logic.commands.AddCommand;
import keycontacts.logic.commands.AssignPiecesCommand;
import keycontacts.logic.commands.CancelLessonCommand;
import keycontacts.logic.commands.ClearCommand;
import keycontacts.logic.commands.Command;
import keycontacts.logic.commands.DeleteCommand;
import keycontacts.logic.commands.EditCommand;
import keycontacts.logic.commands.ExitCommand;
import keycontacts.logic.commands.FindCommand;
import keycontacts.logic.commands.HelpCommand;
import keycontacts.logic.commands.ListCommand;
import keycontacts.logic.commands.MakeupLessonCommand;
import keycontacts.logic.commands.ScheduleCommand;
import keycontacts.logic.commands.SortCommand;
import keycontacts.logic.commands.UnassignPiecesCommand;
import keycontacts.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class KeyContactsParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(KeyContactsParser.class);

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

        return switch (commandWord) {
        case AddCommand.COMMAND_WORD -> new AddCommandParser().parse(arguments);
        case EditCommand.COMMAND_WORD -> new EditCommandParser().parse(arguments);
        case DeleteCommand.COMMAND_WORD -> new DeleteCommandParser().parse(arguments);
        case ClearCommand.COMMAND_WORD -> new ClearCommand();
        case FindCommand.COMMAND_WORD -> new FindCommandParser().parse(arguments);
        case ListCommand.COMMAND_WORD -> new ListCommand();
        case SortCommand.COMMAND_WORD -> new SortCommandParser().parse(arguments);
        case ExitCommand.COMMAND_WORD -> new ExitCommand();
        case HelpCommand.COMMAND_WORD -> new HelpCommand();
        case MakeupLessonCommand.COMMAND_WORD -> new MakeupCommandParser().parse(arguments);
        case ScheduleCommand.COMMAND_WORD -> new ScheduleCommandParser().parse(arguments);
        case AssignPiecesCommand.COMMAND_WORD -> new AssignPiecesCommandParser().parse(arguments);
        case UnassignPiecesCommand.COMMAND_WORD -> new UnassignPiecesCommandParser().parse(arguments);
        case CancelLessonCommand.COMMAND_WORD -> new CancelLessonCommandParser().parse(arguments);
        default -> {
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
        };
    }

}
