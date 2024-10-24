package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommentCommand;
import seedu.address.logic.commands.CreateAttendanceEventCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListAttendanceCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListEventsCommand;
import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.commands.OfflineHelpCommand;
import seedu.address.logic.commands.RandomCommand;
import seedu.address.logic.commands.ShowCommand;
import seedu.address.logic.commands.UnmarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
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

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommandParser().parse(userInput);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(userInput);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommandParser().parse(userInput);

        case HelpCommand.COMMAND_WORD:
            return new HelpCommandParser().parse(userInput);

        case CommentCommand.COMMAND_WORD:
            return new CommentCommandParser().parse(arguments);
        case ShowCommand.COMMAND_WORD:
            return new ShowCommandParser().parse(arguments);

        case CreateAttendanceEventCommand.COMMAND_WORD:
            return new CreateAttendanceEventCommandParser().parse(arguments);

        case MarkAttendanceCommand.COMMAND_WORD:
            return new MarkAttendanceCommandParser().parse(arguments);

        case UnmarkAttendanceCommand.COMMAND_WORD:
            return new UnmarkAttendanceCommandParser().parse(arguments);

        case ListAttendanceCommand.COMMAND_WORD:
            return new ListAttendanceCommandParser().parse(arguments);

        case ListEventsCommand.COMMAND_WORD:
            return new ListEventsCommandParser().parse(arguments);

        case DeleteEventCommand.COMMAND_WORD:
            return new DeleteEventCommandParser().parse(arguments);

        case RandomCommand.COMMAND_WORD:
            return new RandomCommandParser().parse(arguments);

        case OfflineHelpCommand.COMMAND_WORD:
            return new OfflineHelpCommandParser().parse(userInput);
        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
