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
import seedu.address.logic.commands.CreateTagCommand;
import seedu.address.logic.commands.CreateTaskCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListTasksCommand;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.UntagCommand;
import seedu.address.logic.commands.findcommand.FindCommand;
import seedu.address.logic.commands.wedding.AssignWeddingCommand;
import seedu.address.logic.commands.wedding.CreateWeddingCommand;
import seedu.address.logic.commands.wedding.DeleteWeddingCommand;
import seedu.address.logic.commands.wedding.EditWeddingCommand;
import seedu.address.logic.commands.wedding.ListWeddingsCommand;
import seedu.address.logic.commands.wedding.UnassignWeddingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
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
        case ListWeddingsCommand.COMMAND_WORD -> new ListWeddingsCommand();
        case ExitCommand.COMMAND_WORD -> new ExitCommand();
        case HelpCommand.COMMAND_WORD -> new HelpCommand();
        case CreateTagCommand.COMMAND_WORD -> new CreateTagCommandParser().parse(arguments);
        case DeleteTagCommand.COMMAND_WORD -> new DeleteTagCommandParser().parse(arguments);
        case EditWeddingCommand.COMMAND_WORD -> new EditWeddingCommandParser().parse(arguments);
        case CreateWeddingCommand.COMMAND_WORD -> new CreateWeddingCommandParser().parse(arguments);
        case DeleteWeddingCommand.COMMAND_WORD -> new DeleteWeddingCommandParser().parse(arguments);
        case TagCommand.COMMAND_WORD -> new TagCommandParser().parse(arguments);
        case UntagCommand.COMMAND_WORD -> new UntagCommandParser().parse(arguments);
        case AssignWeddingCommand.COMMAND_WORD -> new AssignWeddingCommandParser().parse(arguments);
        case UnassignWeddingCommand.COMMAND_WORD -> new UnassignWeddingCommandParser().parse(arguments);
        case CreateTaskCommand.COMMAND_WORD -> new CreateTaskCommandParser().parse(arguments);
        case ListTasksCommand.COMMAND_WORD -> new ListTasksCommand();
        case DeleteTaskCommand.COMMAND_WORD -> new DeleteTaskCommandParser().parse(arguments);
        default -> {
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
        };
    }

}
