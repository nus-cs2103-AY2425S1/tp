package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.MarkTaskCommand;
import seedu.address.logic.commands.addcommands.AddGroupCommand;
import seedu.address.logic.commands.addcommands.AddStudentCommand;
import seedu.address.logic.commands.addcommands.AddStudentToGroupCommand;
import seedu.address.logic.commands.addcommands.AddTaskToGroupCommand;
import seedu.address.logic.commands.deletecommands.DeleteGroupCommand;
import seedu.address.logic.commands.deletecommands.DeleteStudentCommand;
import seedu.address.logic.commands.deletecommands.DeleteStudentFromGroupCommand;
import seedu.address.logic.commands.deletecommands.DeleteTaskFromGroupCommand;
import seedu.address.logic.commands.editcommands.EditGroupCommand;
import seedu.address.logic.commands.editcommands.EditStudentCommand;
import seedu.address.logic.commands.editcommands.EditTaskCommand;
import seedu.address.logic.commands.findcommands.FindCommand;
import seedu.address.logic.commands.listcommands.ListGroupCommand;
import seedu.address.logic.commands.listcommands.ListStudentCommand;
import seedu.address.logic.commands.listcommands.ListTaskCommand;
import seedu.address.logic.commands.versionhistorycommands.RedoCommand;
import seedu.address.logic.commands.versionhistorycommands.UndoCommand;
import seedu.address.logic.parser.addcommands.AddGroupCommandParser;
import seedu.address.logic.parser.addcommands.AddStudentCommandParser;
import seedu.address.logic.parser.addcommands.AddStudentToGroupCommandParser;
import seedu.address.logic.parser.addcommands.AddTaskToGroupCommandParser;
import seedu.address.logic.parser.deletecommands.DeleteGroupCommandParser;
import seedu.address.logic.parser.deletecommands.DeleteStudentCommandParser;
import seedu.address.logic.parser.deletecommands.DeleteStudentFromGroupCommandParser;
import seedu.address.logic.parser.deletecommands.DeleteTaskFromGroupCommandParser;
import seedu.address.logic.parser.editcommands.EditGroupCommandParser;
import seedu.address.logic.parser.editcommands.EditStudentCommandParser;
import seedu.address.logic.parser.editcommands.EditTaskCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.findcommands.FindCommandParser;
import seedu.address.logic.parser.listcommands.ListGroupCommandParser;
import seedu.address.logic.parser.listcommands.ListStudentCommandParser;
import seedu.address.logic.parser.listcommands.ListTaskCommandParser;

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

        // Student
        case ListStudentCommand.COMMAND_WORD_ALIS:
        case ListStudentCommand.COMMAND_WORD:
            return new ListStudentCommandParser().parse(arguments);

        case AddStudentCommand.COMMAND_WORD_ALIAS:
        case AddStudentCommand.COMMAND_WORD:
            return new AddStudentCommandParser().parse(arguments);

        case DeleteStudentCommand.COMMAND_WORD_ALIAS:
        case DeleteStudentCommand.COMMAND_WORD:
            return new DeleteStudentCommandParser().parse(arguments);

        case EditStudentCommand.COMMAND_WORD_ALIAS:
        case EditStudentCommand.COMMAND_WORD:
            return new EditStudentCommandParser().parse(arguments);

        case AddStudentToGroupCommand.COMMAND_WORD_ALIAS:
        case AddStudentToGroupCommand.COMMAND_WORD:
            return new AddStudentToGroupCommandParser().parse(arguments);

        case DeleteStudentFromGroupCommand.COMMAND_WORD_ALIAS:
        case DeleteStudentFromGroupCommand.COMMAND_WORD:
            return new DeleteStudentFromGroupCommandParser().parse(arguments);

        // Group
        case ListGroupCommand.COMMAND_WORD_ALIAS:
        case ListGroupCommand.COMMAND_WORD:
            return new ListGroupCommandParser().parse(arguments);

        case AddGroupCommand.COMMAND_WORD_ALIAS:
        case AddGroupCommand.COMMAND_WORD:
            return new AddGroupCommandParser().parse(arguments);

        case DeleteGroupCommand.COMMAND_WORD_ALIAS:
        case DeleteGroupCommand.COMMAND_WORD:
            return new DeleteGroupCommandParser().parse(arguments);

        case EditGroupCommand.COMMAND_WORD_ALIAS:
        case EditGroupCommand.COMMAND_WORD:
            return new EditGroupCommandParser().parse(arguments);

        // Task
        case ListTaskCommand.COMMAND_WORD_ALIAS:
        case ListTaskCommand.COMMAND_WORD:
            return new ListTaskCommandParser().parse(arguments);

        case AddTaskToGroupCommand.COMMAND_WORD_ALIAS:
        case AddTaskToGroupCommand.COMMAND_WORD:
            return new AddTaskToGroupCommandParser().parse(arguments);

        case DeleteTaskFromGroupCommand.COMMAND_WORD_ALIAS:
        case DeleteTaskFromGroupCommand.COMMAND_WORD:
            return new DeleteTaskFromGroupCommandParser().parse(arguments);

        case MarkTaskCommand.COMMAND_WORD_ALIAS:
        case MarkTaskCommand.COMMAND_WORD:
            return new MarkTaskCommandParser().parse(arguments);

        case EditTaskCommand.COMMAND_WORD_ALIAS:
        case EditTaskCommand.COMMAND_WORD:
            return new EditTaskCommandParser().parse(arguments);


        case UndoCommand.COMMAND_WORD:
            return new UndoCommandParser().parse(arguments);

        case RedoCommand.COMMAND_WORD:
            return new RedoCommandParser().parse(arguments);


        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            // throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            // will implement in v1.4
            return new FindCommandParser().parse(arguments);

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
