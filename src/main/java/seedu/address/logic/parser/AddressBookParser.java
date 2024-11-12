package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddParentCommand;
import seedu.address.logic.commands.AddStudentCommand;
import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindDayCommand;
import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.commands.GradeCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.LinkCommand;
import seedu.address.logic.commands.ListArchiveCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListParentsCommand;
import seedu.address.logic.commands.ListStudentsCommand;
import seedu.address.logic.commands.PinCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UnarchiveCommand;
import seedu.address.logic.commands.UnlinkCommand;
import seedu.address.logic.commands.UnpinCommand;
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

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddStudentCommand.COMMAND_WORD:
            return new AddStudentCommandParser().parse(arguments);

        case AddParentCommand.COMMAND_WORD:
            return new AddParentCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case FindTagCommand.COMMAND_WORD:
            return new FindTagCommandParser().parse(arguments);

        case FindDayCommand.COMMAND_WORD:
            return new FindDayCommandParser().parse(arguments);

        case GradeCommand.COMMAND_WORD:
            return new GradeCommandParser().parse(arguments);

        case PinCommand.COMMAND_WORD:
            return new PinCommandParser().parse(arguments);

        case UnpinCommand.COMMAND_WORD:
            return new UnpinCommandParser().parse(arguments);

        case ListStudentsCommand.COMMAND_WORD:
            return new ListStudentsCommand();

        case ListParentsCommand.COMMAND_WORD:
            return new ListParentsCommand();

        case ArchiveCommand.COMMAND_WORD:
            return new ArchiveCommandParser().parse(arguments);

        case UnarchiveCommand.COMMAND_WORD:
            return new UnarchiveCommandParser().parse(arguments);

        case ListArchiveCommand.COMMAND_WORD:
            return new ListArchiveCommand();

        case LinkCommand.COMMAND_WORD:
            return new LinkCommandParser().parse(arguments);

        case UnlinkCommand.COMMAND_WORD:
            return new UnlinkCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
