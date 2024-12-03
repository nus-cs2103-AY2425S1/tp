package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddAssignmentByTgCommand;
import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.logic.commands.AddStudentCommand;
import seedu.address.logic.commands.CloseWindowCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteAllStudentsCommand;
import seedu.address.logic.commands.DeleteAssignmentCommand;
import seedu.address.logic.commands.DeleteAttendanceAllCommand;
import seedu.address.logic.commands.DeleteAttendanceCommand;
import seedu.address.logic.commands.DeleteStudentCommand;
import seedu.address.logic.commands.EditAssignmentCommand;
import seedu.address.logic.commands.EditStudentCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.GetAttendanceByTgCommand;
import seedu.address.logic.commands.GetAttendanceCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MarkAbsentAllCommand;
import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.commands.MarkPresentAllCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewStudentCommand;
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

        case ViewStudentCommand.COMMAND_WORD:
            return new ViewStudentCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddStudentCommand.COMMAND_WORD:
            return new AddStudentCommandParser().parse(arguments);

        case DeleteStudentCommand.COMMAND_WORD:
            return new DeleteStudentCommandParser().parse(arguments);

        case DeleteAllStudentsCommand.COMMAND_WORD:
            return new DeleteAllStudentsCommand();

        case MarkAttendanceCommand.COMMAND_WORD:
            return new MarkAttendanceCommandParser().parse(arguments);

        case DeleteAttendanceCommand.COMMAND_WORD:
            return new DeleteAttendanceCommandParser().parse(arguments);

        case AddAssignmentCommand.COMMAND_WORD:
            return new AddAssignmentCommandParser().parse(arguments);

        case DeleteAssignmentCommand.COMMAND_WORD:
            return new DeleteAssignmentCommandParser().parse(arguments);

        case EditStudentCommand.COMMAND_WORD:
            return new EditStudentCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case GetAttendanceByTgCommand.COMMAND_WORD:
            return new GetAttendanceByTgCommandParser().parse(arguments);

        case GetAttendanceCommand.COMMAND_WORD:
            return new GetAttendanceCommandParser().parse(arguments);

        case MarkPresentAllCommand.COMMAND_WORD:
            return new MarkPresentAllCommandParser().parse(arguments);

        case MarkAbsentAllCommand.COMMAND_WORD:
            return new MarkAbsentAllCommandParser().parse(arguments);

        case DeleteAttendanceAllCommand.COMMAND_WORD:
            return new DeleteAttendanceAllCommandParser().parse(arguments);

        case EditAssignmentCommand.COMMAND_WORD:
            return new EditAssignmentCommandParser().parse(arguments);

        case CloseWindowCommand.COMMAND_WORD:
            return new CloseWindowCommandParser().parse(arguments);

        case AddAssignmentByTgCommand.COMMAND_WORD:
            return new AddAssignmentByTgCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
