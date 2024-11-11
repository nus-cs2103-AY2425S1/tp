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
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindAddressCommand;
import seedu.address.logic.commands.FindClientTypeCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindNameCommand;
import seedu.address.logic.commands.FindPhoneCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.reminder.AddReminderCommand;
import seedu.address.logic.commands.reminder.DeleteReminderCommand;
import seedu.address.logic.commands.reminder.EditReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.reminder.AddReminderCommandParser;
import seedu.address.logic.parser.reminder.DeleteReminderCommandParser;
import seedu.address.logic.parser.reminder.EditReminderCommandParser;

/**
 * Parses user input.
 */
public class ClientHubParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(ClientHubParser.class);

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

        case AddCommand.COMMAND_WORD, AddCommand.SHORT_COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD, DeleteCommand.SHORT_COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case FindPhoneCommand.COMMAND_WORD:
            return new FindPhoneCommandParser().parse(arguments);

        case FindClientTypeCommand.COMMAND_WORD:
            return new FindClientTypeCommandParser().parse(arguments);

        case FindNameCommand.COMMAND_WORD:
            return new FindNameCommandParser().parse(arguments);

        case FindAddressCommand.COMMAND_WORD:
            return new FindAddressCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD, ListCommand.SHORT_COMMAND_WORD:
            return new ListCommand();

        case SortCommand.COMMAND_WORD, SortCommand.SHORT_COMMAND_WORD:
            return new SortCommand();

        case ViewCommand.COMMAND_WORD, ViewCommand.SHORT_COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case DeleteReminderCommand.COMMAND_WORD, DeleteReminderCommand.COMMAND_WORD_SHORT:
            return new DeleteReminderCommandParser().parse(arguments);

        case AddReminderCommand.COMMAND_WORD, AddReminderCommand.COMMAND_WORD_SHORT:
            return new AddReminderCommandParser().parse(arguments);

        case EditReminderCommand.COMMAND_WORD, EditReminderCommand.COMMAND_WORD_SHORT:
            return new EditReminderCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
