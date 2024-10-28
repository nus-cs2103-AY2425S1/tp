package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddClientCommand;
import seedu.address.logic.commands.AddRentalCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteClientCommand;
import seedu.address.logic.commands.DeleteRentalCommand;
import seedu.address.logic.commands.EditClientCommand;
import seedu.address.logic.commands.EditRentalCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NextCommandHistoryCommand;
import seedu.address.logic.commands.PreviousCommandHistoryCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.ViewRentalCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.CommandHistoryStorage;

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

        case AddClientCommand.COMMAND_WORD:
            CommandHistoryStorage.writeToFile(userInput);
            return new AddClientCommandParser().parse(arguments);

        case AddRentalCommand.COMMAND_WORD:
            CommandHistoryStorage.writeToFile(userInput);
            return new AddRentalCommandParser().parse(arguments);

        case EditClientCommand.COMMAND_WORD:
            CommandHistoryStorage.writeToFile(userInput);
            return new EditClientCommandParser().parse(arguments);

        case EditRentalCommand.COMMAND_WORD:
            CommandHistoryStorage.writeToFile(userInput);
            return new EditRentalCommandParser().parse(arguments);

        case DeleteClientCommand.COMMAND_WORD:
            CommandHistoryStorage.writeToFile(userInput);
            return new DeleteClientCommandParser().parse(arguments);

        case DeleteRentalCommand.COMMAND_WORD:
            CommandHistoryStorage.writeToFile(userInput);
            return new DeleteRentalCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            CommandHistoryStorage.writeToFile(userInput);
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            CommandHistoryStorage.writeToFile(userInput);
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            CommandHistoryStorage.writeToFile(userInput);
            return new ListCommand();

        case ViewRentalCommand.COMMAND_WORD:
            CommandHistoryStorage.writeToFile(userInput);
            return new ViewRentalCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            CommandHistoryStorage.writeToFile(userInput);
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            CommandHistoryStorage.writeToFile(userInput);
            return new HelpCommand();

        case PreviousCommandHistoryCommand.COMMAND_WORD:
            return new PreviousCommandHistoryCommand();

        case NextCommandHistoryCommand.COMMAND_WORD:
            return new NextCommandHistoryCommand();

        case SortCommand.COMMAND_WORD:
            return new SortCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
