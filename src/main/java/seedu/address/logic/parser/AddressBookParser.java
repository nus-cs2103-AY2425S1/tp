package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserUtil.parseInvalidVariants;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.QuitCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
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
        if (userInput.trim().length() >= 2
                && userInput.trim().substring(userInput.trim().length() - 2).equals(
                PREFIX_TAG.getPrefix().trim()) && userInput.trim().length() < userInput.length()) {

            int lastIndex = userInput.lastIndexOf(PREFIX_TAG.getPrefix());
            userInput = userInput.substring(0, lastIndex + 2)
                    + userInput.substring(lastIndex + 2).replaceAll("\\s+", " ");
        } else {
            userInput = userInput.trim();
        }
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput);
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

        case AddCommand.SHORT_COMMAND_WORD, AddCommand.LONG_COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.SHORT_COMMAND_WORD, EditCommand.LONG_COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.SHORT_COMMAND_WORD, DeleteCommand.LONG_COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.SHORT_COMMAND_WORD, ClearCommand.LONG_COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.SHORT_COMMAND_WORD, FindCommand.LONG_COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.SHORT_COMMAND_WORD, ListCommand.LONG_COMMAND_WORD:
            return new ListCommand();

        case UndoCommand.SHORT_COMMAND_WORD, UndoCommand.LONG_COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.SHORT_COMMAND_WORD, RedoCommand.LONG_COMMAND_WORD:
            return new RedoCommand();

        case QuitCommand.SHORT_COMMAND_WORD, QuitCommand.LONG_COMMAND_WORD:
            return new QuitCommand();

        case HelpCommand.SHORT_COMMAND_WORD, HelpCommand.LONG_COMMAND_WORD:
            return new HelpCommand();

        case ExportCommand.SHORT_COMMAND_WORD, ExportCommand.LONG_COMMAND_WORD:
            return new ExportCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            parseInvalidVariants(commandWord);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);

        }
    }
}
