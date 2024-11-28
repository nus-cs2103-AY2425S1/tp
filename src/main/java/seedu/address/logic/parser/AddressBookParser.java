package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddPropertyToBuyCommand;
import seedu.address.logic.commands.AddPropertyToSellCommand;
import seedu.address.logic.commands.BoughtPropertyCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandListCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeletePropertyToBuyCommand;
import seedu.address.logic.commands.DeletePropertyToSellCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindBuyCommand;
import seedu.address.logic.commands.FindNameCommand;
import seedu.address.logic.commands.FindPhoneNumberCommand;
import seedu.address.logic.commands.FindSellCommand;
import seedu.address.logic.commands.FindTagContactCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.PinContactCommand;
import seedu.address.logic.commands.SoldPropertyCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SortIndividualCommand;
import seedu.address.logic.commands.StatisticsCommand;
import seedu.address.logic.commands.UnpinContactCommand;
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

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindNameCommand.COMMAND_WORD:
            return new FindNameCommandParser().parse(arguments);

        case FindPhoneNumberCommand.COMMAND_WORD:
            return new FindPhoneNumberCommandParser().parse(arguments);

        case FindTagContactCommand.COMMAND_WORD:
            return new FindTagContactCommandParser().parse(arguments);

        case FindBuyCommand.COMMAND_WORD:
            return new FindBuyCommandParser().parse(arguments);

        case FindSellCommand.COMMAND_WORD:
            return new FindSellCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddPropertyToSellCommand.COMMAND_WORD:
            return new AddPropertyToSellParser().parse(arguments);

        case AddPropertyToBuyCommand.COMMAND_WORD:
            return new AddPropertyToBuyParser().parse(arguments);

        case DeletePropertyToBuyCommand.COMMAND_WORD:
            return new DeletePropertyToBuyCommandParser().parse(arguments);

        case DeletePropertyToSellCommand.COMMAND_WORD:
            return new DeletePropertyToSellCommandParser().parse(arguments);

        case PinContactCommand.COMMAND_WORD:
            return new PinContactCommandParser().parse(arguments);

        case UnpinContactCommand.COMMAND_WORD:
            return new UnpinContactCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case SortIndividualCommand.COMMAND_WORD:
            return new SortIndividualCommandParser().parse(arguments);

        case BoughtPropertyCommand.COMMAND_WORD:
            return new BoughtPropertyCommandParser().parse(arguments);

        case SoldPropertyCommand.COMMAND_WORD:
            return new SoldPropertyCommandParser().parse(arguments);

        case CommandListCommand.COMMAND_WORD:
            return new CommandListCommand();

        case StatisticsCommand.COMMAND_WORD:
            return new StatisticsCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
