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
import seedu.address.logic.commands.ConsecutiveCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindGroupCommand;
import seedu.address.logic.commands.GroupCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListGroupsCommand;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.UntagCommand;
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

    private String lastCommandWord = null; // Track the last command executed
    private String lastArgument = null;

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

        // Check if the command is consecutive
        if (commandWord.equals(lastCommandWord) & arguments.equals(lastArgument)
            && !commandWord.equals("delete") && !commandWord.equals("help")) {
            return new ConsecutiveCommand(commandWord, arguments);
        }
        Command command;
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            command = new AddCommandParser().parse(arguments);
            break;

        case EditCommand.COMMAND_WORD:
            command = new EditCommandParser().parse(arguments);
            break;

        case ExportCommand.COMMAND_WORD:
            command = new ExportCommand();
            break;

        case DeleteCommand.COMMAND_WORD:
            command = new DeleteCommandParser().parse(arguments);
            break;

        case DeleteGroupCommand.COMMAND_WORD:
            command = new DeleteGroupCommandParser().parse(arguments);
            break;

        case ClearCommand.COMMAND_WORD:
            command = new ClearCommand();
            break;

        case FindGroupCommand.COMMAND_WORD:
            command = new FindGroupCommandParser().parse(arguments);
            break;

        case FindCommand.COMMAND_WORD:
            command = new FindCommandParser().parse(arguments);
            break;

        case ImportCommand.COMMAND_WORD:
            command = new ImportCommandParser().parse(arguments);
            break;

        case ListCommand.COMMAND_WORD:
            command = new ListCommand();
            break;

        case ListGroupsCommand.COMMAND_WORD:
            command = new ListGroupsCommand();
            break;

        case GroupCommand.COMMAND_WORD:
            command = new GroupCommandParser().parse(arguments);
            break;

        case ExitCommand.COMMAND_WORD:
            command = new ExitCommand();
            break;

        case HelpCommand.COMMAND_WORD:
            command = new HelpCommand();
            break;

        case TagCommand.COMMAND_WORD:
            command = new TagCommandParser().parse(arguments);
            break;

        case UntagCommand.COMMAND_WORD:
            command = new UntagCommandParser().parse(arguments);
            break;

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);


        }

        // Only update lastCommandWord if parsing was successful
        lastCommandWord = commandWord;
        lastArgument = arguments;
        return command;
    }

}
