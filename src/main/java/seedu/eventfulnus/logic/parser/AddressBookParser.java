package seedu.eventfulnus.logic.parser;

import static seedu.eventfulnus.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eventfulnus.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.eventfulnus.commons.core.LogsCenter;
import seedu.eventfulnus.logic.commands.AddCommand;
import seedu.eventfulnus.logic.commands.AddEventCommand;
import seedu.eventfulnus.logic.commands.ClearCommand;
import seedu.eventfulnus.logic.commands.Command;
import seedu.eventfulnus.logic.commands.DeleteCommand;
import seedu.eventfulnus.logic.commands.DeleteEventCommand;
import seedu.eventfulnus.logic.commands.EditCommand;
import seedu.eventfulnus.logic.commands.EditEventCommand;
import seedu.eventfulnus.logic.commands.ExitCommand;
import seedu.eventfulnus.logic.commands.FindCommand;
import seedu.eventfulnus.logic.commands.FindEventCommand;
import seedu.eventfulnus.logic.commands.HelpCommand;
import seedu.eventfulnus.logic.commands.ListCommand;
import seedu.eventfulnus.logic.commands.ListEventCommand;
import seedu.eventfulnus.logic.parser.exceptions.ParseException;

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
        case AddEventCommand.COMMAND_WORD -> new AddEventCommandParser().parse(arguments);
        case EditCommand.COMMAND_WORD -> new EditCommandParser().parse(arguments);
        case EditEventCommand.COMMAND_WORD -> new EditEventCommandParser().parse(arguments);
        case DeleteCommand.COMMAND_WORD -> new DeleteCommandParser().parse(arguments);
        case DeleteEventCommand.COMMAND_WORD -> new DeleteEventCommandParser().parse(arguments);
        case ClearCommand.COMMAND_WORD -> new ClearCommand();
        case FindCommand.COMMAND_WORD -> new FindCommandParser().parse(arguments);
        case FindEventCommand.COMMAND_WORD -> new FindEventCommandParser().parse(arguments);
        case ListCommand.COMMAND_WORD -> new ListCommand();
        case ListEventCommand.COMMAND_WORD -> new ListEventCommand();
        case ExitCommand.COMMAND_WORD -> new ExitCommand();
        case HelpCommand.COMMAND_WORD -> new HelpCommand();

        default -> {
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND + userInput);
        }
        };
    }

}
