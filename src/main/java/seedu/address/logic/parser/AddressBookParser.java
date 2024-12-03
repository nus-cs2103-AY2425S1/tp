package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_LIST_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddDeliveryCommand;
import seedu.address.logic.commands.AddSupplierCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteDeliveryCommand;
import seedu.address.logic.commands.DeleteSupplierCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindDeliveryCommand;
import seedu.address.logic.commands.FindSupplierCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListAllCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListDeliveryCommand;
import seedu.address.logic.commands.ListSupplierCommand;
import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.commands.MarkDeliveryCommand;
import seedu.address.logic.commands.MarkSupplierCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SortDeliveryCommand;
import seedu.address.logic.commands.SortSupplierCommand;
import seedu.address.logic.commands.UpcomingCommand;
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
        String trimmedArguments = arguments.trim();

        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return parseAddCommand(trimmedArguments);

        case DeleteCommand.COMMAND_WORD:
            return parseDeleteCommand(trimmedArguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return parseFindCommand(trimmedArguments);

        case ListCommand.COMMAND_WORD:
            return parseListCommand(trimmedArguments);

        case MarkCommand.COMMAND_WORD:
            return parseMarkCommand(trimmedArguments);

        case SortCommand.COMMAND_WORD:
            return parseSortCommand(trimmedArguments);

        case UpcomingCommand.COMMAND_WORD:
            return parseUpcomingCommand(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    private AddCommand parseAddCommand(String arguments) throws ParseException {
        if (arguments.trim().startsWith("-s")) {
            return new AddSupplierCommandParser().parse(arguments.trim().substring(2));
        } else if (arguments.trim().startsWith("-d")) {
            return new AddDeliveryCommandParser().parse(arguments.trim().substring(2));
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddSupplierCommand.MESSAGE_USAGE + "\nOR\n" + AddDeliveryCommand.MESSAGE_USAGE));
        }
    }

    private DeleteCommand parseDeleteCommand(String trimmedArguments) throws ParseException {
        if (trimmedArguments.startsWith("-s")) {
            return new DeleteSupplierCommandParser().parse(trimmedArguments);
        } else if (trimmedArguments.startsWith("-d")) {
            return new DeleteDeliveryCommandParser().parse(trimmedArguments);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteSupplierCommand.MESSAGE_USAGE + "\nOR\n" + DeleteDeliveryCommand.MESSAGE_USAGE));
        }
    }

    private FindCommand parseFindCommand(String trimmedArguments) throws ParseException {
        if (trimmedArguments.startsWith("-s")) {
            return new FindSupplierCommandParser().parse(trimmedArguments.substring(2));
        } else if (trimmedArguments.startsWith("-d")) {
            return new FindDeliveryCommandParser().parse(trimmedArguments.substring(2));
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindSupplierCommand.MESSAGE_USAGE + "\nOR\n" + FindDeliveryCommand.MESSAGE_USAGE));
        }
    }

    private ListCommand parseListCommand(String trimmedArguments) throws ParseException {
        if (trimmedArguments.equals("-s")) {
            return new ListSupplierCommand();
        } else if (trimmedArguments.equals("-d")) {
            return new ListDeliveryCommand();
        } else if (trimmedArguments.equals("-a")) {
            return new ListAllCommand();
        } else {
            throw new ParseException(MESSAGE_INVALID_LIST_COMMAND_FORMAT);
        }
    }

    private MarkCommand parseMarkCommand(String trimmedArguments) throws ParseException {
        if (trimmedArguments.startsWith("-s")) {
            return new MarkSupplierCommandParser().parse(trimmedArguments);
        } else if (trimmedArguments.startsWith("-d")) {
            return new MarkDeliveryCommandParser().parse(trimmedArguments);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkSupplierCommand.MESSAGE_USAGE + "\nOR\n" + MarkDeliveryCommand.MESSAGE_USAGE));
        }
    }

    private SortCommand parseSortCommand(String trimmedArguments) throws ParseException {
        if (trimmedArguments.startsWith(PREFIX_DELIVERY.getPrefix())) {
            return new SortDeliveryCommandParser().parse(trimmedArguments);
        } else if (trimmedArguments.startsWith(PREFIX_SUPPLIER.getPrefix())) {
            return new SortSupplierCommandParser().parse(trimmedArguments);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SortSupplierCommand.MESSAGE_USAGE + "\nOR\n" + SortDeliveryCommand.MESSAGE_USAGE));
        }
    }

    private UpcomingCommand parseUpcomingCommand(String arguments) throws ParseException {
        if (arguments.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpcomingCommand.MESSAGE_USAGE));
        }
        return new UpcomingCommandParser().parse(arguments);
    }
}
