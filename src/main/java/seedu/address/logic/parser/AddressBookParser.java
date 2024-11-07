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
import seedu.address.logic.commands.findcommand.FindCommand;
import seedu.address.logic.commands.person.AddCommand;
import seedu.address.logic.commands.person.DeleteCommand;
import seedu.address.logic.commands.person.EditCommand;
import seedu.address.logic.commands.person.ListCommand;
import seedu.address.logic.commands.tag.CreateTagCommand;
import seedu.address.logic.commands.tag.DeleteTagCommand;
import seedu.address.logic.commands.tag.ListTagsCommand;
import seedu.address.logic.commands.tag.TagCommand;
import seedu.address.logic.commands.tag.UntagCommand;
import seedu.address.logic.commands.task.AssignTaskCommand;
import seedu.address.logic.commands.task.CreateTaskCommand;
import seedu.address.logic.commands.task.DeleteTaskCommand;
import seedu.address.logic.commands.task.ListTasksCommand;
import seedu.address.logic.commands.task.MarkTaskCommand;
import seedu.address.logic.commands.task.UnassignTaskCommand;
import seedu.address.logic.commands.task.UnmarkTaskCommand;
import seedu.address.logic.commands.vendor.AddVendorCommand;
import seedu.address.logic.commands.vendor.AssignVendorCommand;
import seedu.address.logic.commands.vendor.UnassignVendorCommand;
import seedu.address.logic.commands.wedding.AssignWeddingCommand;
import seedu.address.logic.commands.wedding.CreateWeddingCommand;
import seedu.address.logic.commands.wedding.DeleteWeddingCommand;
import seedu.address.logic.commands.wedding.EditWeddingCommand;
import seedu.address.logic.commands.wedding.ListWeddingsCommand;
import seedu.address.logic.commands.wedding.UnassignWeddingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.task.AssignTaskCommandParser;
import seedu.address.logic.parser.task.CreateTaskCommandParser;
import seedu.address.logic.parser.task.DeleteTaskCommandParser;
import seedu.address.logic.parser.task.MarkTaskCommandParser;
import seedu.address.logic.parser.task.UnassignTaskCommandParser;
import seedu.address.logic.parser.task.UnmarkTaskCommandParser;
import seedu.address.logic.parser.vendor.AddVendorCommandParser;
import seedu.address.logic.parser.vendor.AssignVendorCommandParser;
import seedu.address.logic.parser.vendor.UnassignVendorCommandParser;
import seedu.address.logic.parser.wedding.AssignWeddingCommandParser;
import seedu.address.logic.parser.wedding.CreateWeddingCommandParser;
import seedu.address.logic.parser.wedding.DeleteWeddingCommandParser;
import seedu.address.logic.parser.wedding.EditWeddingCommandParser;
import seedu.address.logic.parser.wedding.UnassignWeddingCommandParser;

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

        return switch (commandWord) {
        case AddCommand.COMMAND_WORD -> new AddCommandParser().parse(arguments);
        case EditCommand.COMMAND_WORD -> new EditCommandParser().parse(arguments);
        case DeleteCommand.COMMAND_WORD -> new DeleteCommandParser().parse(arguments);
        case ClearCommand.COMMAND_WORD -> new ClearCommand();
        case FindCommand.COMMAND_WORD -> new FindCommandParser().parse(arguments);
        case ListCommand.COMMAND_WORD -> new ListCommand();
        case ListWeddingsCommand.COMMAND_WORD, ListWeddingsCommand.COMMAND_KEYWORD -> new ListWeddingsCommand();
        case ExitCommand.COMMAND_WORD -> new ExitCommand();
        case HelpCommand.COMMAND_WORD -> new HelpCommand();
        case CreateTagCommand.COMMAND_WORD, CreateTagCommand.COMMAND_KEYWORD
                -> new CreateTagCommandParser().parse(arguments);
        case DeleteTagCommand.COMMAND_WORD, DeleteTagCommand.COMMAND_KEYWORD
                -> new DeleteTagCommandParser().parse(arguments);
        case EditWeddingCommand.COMMAND_WORD, EditWeddingCommand.COMMAND_KEYWORD
                -> new EditWeddingCommandParser().parse(arguments);
        case CreateWeddingCommand.COMMAND_WORD, CreateWeddingCommand.COMMAND_KEYWORD
                -> new CreateWeddingCommandParser().parse(arguments);
        case DeleteWeddingCommand.COMMAND_WORD, DeleteWeddingCommand.COMMAND_KEYWORD
                -> new DeleteWeddingCommandParser().parse(arguments);
        case TagCommand.COMMAND_WORD -> new TagCommandParser().parse(arguments);
        case UntagCommand.COMMAND_WORD -> new UntagCommandParser().parse(arguments);
        case AssignWeddingCommand.COMMAND_WORD, AssignWeddingCommand.COMMAND_KEYWORD
                -> new AssignWeddingCommandParser().parse(arguments);
        case UnassignWeddingCommand.COMMAND_WORD, UnassignWeddingCommand.COMMAND_KEYWORD
                -> new UnassignWeddingCommandParser().parse(arguments);
        case CreateTaskCommand.COMMAND_WORD, CreateTaskCommand.COMMAND_KEYWORD
                -> new CreateTaskCommandParser().parse(arguments);
        case ListTasksCommand.COMMAND_WORD, ListTasksCommand.COMMAND_KEYWORD
                -> new ListTasksCommand();
        case ListTagsCommand.COMMAND_WORD, ListTagsCommand.COMMAND_KEYWORD
                -> new ListTagsCommand();
        case AssignTaskCommand.COMMAND_WORD, AssignTaskCommand.COMMAND_KEYWORD
                -> new AssignTaskCommandParser().parse(arguments);
        case UnassignTaskCommand.COMMAND_WORD, UnassignTaskCommand.COMMAND_KEYWORD
                -> new UnassignTaskCommandParser().parse(arguments);
        case MarkTaskCommand.COMMAND_WORD, MarkTaskCommand.COMMAND_KEYWORD
                -> new MarkTaskCommandParser().parse(arguments);
        case UnmarkTaskCommand.COMMAND_WORD, UnmarkTaskCommand.COMMAND_KEYWORD
                -> new UnmarkTaskCommandParser().parse(arguments);
        case DeleteTaskCommand.COMMAND_WORD, DeleteTaskCommand.COMMAND_KEYWORD
                -> new DeleteTaskCommandParser().parse(arguments);
        case AssignVendorCommand.COMMAND_WORD, AssignVendorCommand.COMMAND_KEYWORD
                -> new AssignVendorCommandParser().parse(arguments);
        case AddVendorCommand.COMMAND_WORD, AddVendorCommand.COMMAND_KEYWORD
                -> new AddVendorCommandParser().parse(arguments);
        case UnassignVendorCommand.COMMAND_WORD, UnassignVendorCommand.COMMAND_KEYWORD
                -> new UnassignVendorCommandParser().parse(arguments);
        default -> {
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
        };
    }

}
