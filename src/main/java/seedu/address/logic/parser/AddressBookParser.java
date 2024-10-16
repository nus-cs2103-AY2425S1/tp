package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddGroupCommand;
import seedu.address.logic.commands.AddStudentCommand;
import seedu.address.logic.commands.AddStudentToGroupCommand;
import seedu.address.logic.commands.AddTaskToGroupCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.commands.DeleteStudentCommand;
import seedu.address.logic.commands.DeleteStudentFromGroupCommand;
import seedu.address.logic.commands.DeleteTaskFromGroupCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListGroupCommand;
import seedu.address.logic.commands.ListStudentCommand;
import seedu.address.logic.commands.ListTaskCommand;
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

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteStudentCommand.COMMAND_WORD:
            return new DeleteStudentCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddGroupCommand.COMMAND_WORD:
            return new AddGroupCommandParser().parse(arguments);

        case ListStudentCommand.COMMAND_WORD:
            return new ListStudentCommandParser().parse(arguments);

        case ListGroupCommand.COMMAND_WORD:
            return new ListGroupCommandParser().parse(arguments);

        case AddStudentToGroupCommand.COMMAND_WORD:
            return new AddStudentToGroupCommandParser().parse(arguments);

        case DeleteStudentFromGroupCommand.COMMAND_WORD:
            return new DeleteStudentFromGroupCommandParser().parse(arguments);

        case ListTaskCommand.COMMAND_WORD:
            return new ListTaskCommandParser().parse(arguments);

        case DeleteGroupCommand.COMMAND_WORD:
            return new DeleteGroupCommandParser().parse(arguments);

        case AddTaskToGroupCommand.COMMAND_WORD:
            return new AddTaskToGroupCommandParser().parse(arguments);

        case DeleteTaskFromGroupCommand.COMMAND_WORD:
            return new DeleteTaskFromGroupCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
