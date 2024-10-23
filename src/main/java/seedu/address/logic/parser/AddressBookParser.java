package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddWeddingCommand;
import seedu.address.logic.commands.AssignContactToWeddingCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteWeddingCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditWeddingCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListTagsCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.UnassignContactFromWeddingCommand;
import seedu.address.logic.commands.UntagCommand;
import seedu.address.logic.commands.ViewWeddingCommand;
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
    private boolean pendingClear;

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        if (pendingClear) {
            pendingClear = false;
            return new ActualClearCommandParser().parse(userInput);
        }

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

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);
        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);
        case TagCommand.COMMAND_WORD:
            return new TagCommandParser().parse(arguments);
        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);
        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();
        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);
        case FilterCommand.COMMAND_WORD:
            return new FilterCommandParser().parse(arguments);
        case ListCommand.COMMAND_WORD:
            return new ListCommand();
        case ListTagsCommand.COMMAND_WORD:
            return new ListTagsCommand();
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();
        case UntagCommand.COMMAND_WORD:
            return new UntagCommandParser().parse(arguments);
        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
        case SortCommand.COMMAND_WORD:
            return new SortCommand();
        case ViewWeddingCommand.COMMAND_WORD:
            return new ViewWeddingCommandParser().parse(arguments);
        case AssignContactToWeddingCommand.COMMAND_WORD:
            return new AssignContactToWeddingCommandParser().parse(arguments);
        case AddWeddingCommand.COMMAND_WORD:
            return new AddWeddingCommandParser().parse(arguments);
        case EditWeddingCommand.COMMAND_WORD:
            return new EditWeddingCommandParser().parse(arguments);
        case DeleteWeddingCommand.COMMAND_WORD:
            return new DeleteWeddingCommandParser().parse(arguments);
        case UnassignContactFromWeddingCommand.COMMAND_WORD:
            return new UnassignContactFromWeddingCommandParser().parse(arguments);
        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    public void setClearPendingStatus(boolean pendingStatus) {
        this.pendingClear = pendingStatus;
    }
}
