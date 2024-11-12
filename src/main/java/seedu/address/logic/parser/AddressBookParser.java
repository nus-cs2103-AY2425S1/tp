package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddCustomerCommand;
import seedu.address.logic.commands.AddCustomerOrderCommand;
import seedu.address.logic.commands.AddIngredientCommand;
import seedu.address.logic.commands.AddPastryCommand;
import seedu.address.logic.commands.AddSupplierCommand;
import seedu.address.logic.commands.AddSupplyOrderCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteCustomerOrderCommand;
import seedu.address.logic.commands.DeleteSupplyOrderCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MarkCustomerOrderCommand;
import seedu.address.logic.commands.MarkSupplyOrderCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.RemoveIngredientCommand;
import seedu.address.logic.commands.RemovePastryCommand;
import seedu.address.logic.commands.UnmarkCustomerOrderCommand;
import seedu.address.logic.commands.UnmarkSupplyOrderCommand;
import seedu.address.logic.commands.ViewIngredientCatalogueCommand;
import seedu.address.logic.commands.ViewPastryCatalogueCommand;
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

        case AddSupplierCommand.COMMAND_WORD:
            return new AddSupplierCommandParser().parse(arguments);

        // Add this case for the "add customer" command
        case AddCustomerCommand.COMMAND_WORD:
            return new AddCustomerCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case RemarkCommand.COMMAND_WORD:
            return new RemarkCommandParser().parse(arguments);

        case AddPastryCommand.COMMAND_WORD:
            return new AddPastryCommandParser().parse(arguments);

        case RemovePastryCommand.COMMAND_WORD:
            return new RemovePastryCommandParser().parse(arguments);

        case AddIngredientCommand.COMMAND_WORD:
            return new AddIngredientCommandParser().parse(arguments);

        case RemoveIngredientCommand.COMMAND_WORD:
            return new RemoveIngredientCommandParser().parse(arguments);

        case AddCustomerOrderCommand.COMMAND_WORD:
            return new AddCustomerOrderCommandParser().parse(arguments);

        case AddSupplyOrderCommand.COMMAND_WORD:
            return new AddSupplyOrderCommandParser().parse(arguments);

        case DeleteCustomerOrderCommand.COMMAND_WORD:
            return new DeleteCustomerOrderCommandParser().parse(arguments);

        case DeleteSupplyOrderCommand.COMMAND_WORD:
            return new DeleteSupplyOrderCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ViewIngredientCatalogueCommand.COMMAND_WORD:
            return new ViewIngredientCatalogueCommand();

        case ViewPastryCatalogueCommand.COMMAND_WORD:
            return new ViewPastryCatalogueCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case FilterCommand.COMMAND_WORD:
            return new FilterCommandParser().parse(arguments);

        case MarkCustomerOrderCommand.COMMAND_WORD:
            return new MarkCustomerOrderCommandParser().parse(arguments);

        case UnmarkCustomerOrderCommand.COMMAND_WORD:
            return new UnmarkCustomerOrderCommandParser().parse(arguments);

        case MarkSupplyOrderCommand.COMMAND_WORD:
            return new MarkSupplierOrderCommandParser().parse(arguments);

        case UnmarkSupplyOrderCommand.COMMAND_WORD:
            return new UnmarkSupplyOrderCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
