package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.StaticContext;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddWeddingCommand;
import seedu.address.logic.commands.ClearAddressBookCommand;
import seedu.address.logic.commands.ClearWeddingBookCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteNCommand;
import seedu.address.logic.commands.DeleteWeddingCommand;
import seedu.address.logic.commands.DeleteYCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListWeddingCommand;
import seedu.address.logic.commands.TagAddCommand;
import seedu.address.logic.commands.TagDeleteCommand;
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

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case AddWeddingCommand.COMMAND_WORD, AddWeddingCommand.COMMAND_WORD_SHORT:
            return new AddWeddingCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeleteYCommand.COMMAND_WORD:
            if (StaticContext.getWeddingToDelete() != null) {
                return new DeleteYCommand(StaticContext.getWeddingToDelete());
            }
            return new DeleteYCommand(StaticContext.getPersonToDelete());

        case DeleteNCommand.COMMAND_WORD:
            return new DeleteNCommand();

        case DeleteWeddingCommand.COMMAND_WORD, DeleteWeddingCommand.COMMAND_WORD_SHORT:
            return new DeleteWeddingCommandParser().parse(arguments);

        case ClearAddressBookCommand.COMMAND_WORD, ClearAddressBookCommand.COMMAND_WORD_SHORT:
            return new ClearAddressBookCommand();

        case ClearWeddingBookCommand.COMMAND_WORD, ClearWeddingBookCommand.COMMAND_WORD_SHORT:
            return new ClearWeddingBookCommand();

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListWeddingCommand.COMMAND_WORD, ListWeddingCommand.COMMAND_WORD_SHORT:
            return new ListWeddingCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case TagAddCommand.COMMAND_WORD, TagAddCommand.COMMAND_WORD_SHORT:
            return new TagAddCommandParser().parse(arguments);

        case TagDeleteCommand.COMMAND_WORD, TagDeleteCommand.COMMAND_WORD_SHORT:
            return new TagDeleteCommandParser().parse(arguments);

        case FilterCommand.COMMAND_WORD, FilterCommand.COMMAND_WORD_SHORT:
            return new FilterCommandParser().parse(arguments);

        case ViewWeddingCommand.COMMAND_WORD, ViewWeddingCommand.COMMAND_WORD_SHORT:
            return new ViewWeddingCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
