package seedu.sellsavvy.logic.parser;

import static seedu.sellsavvy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sellsavvy.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.sellsavvy.commons.core.LogsCenter;
import seedu.sellsavvy.logic.commands.Command;
import seedu.sellsavvy.logic.commands.customercommands.*;
import seedu.sellsavvy.logic.commands.generalcommands.ClearCommand;
import seedu.sellsavvy.logic.commands.generalcommands.ExitCommand;
import seedu.sellsavvy.logic.commands.generalcommands.HelpCommand;
import seedu.sellsavvy.logic.commands.ordercommands.AddOrderCommand;
import seedu.sellsavvy.logic.commands.ordercommands.DeleteOrderCommand;
import seedu.sellsavvy.logic.commands.ordercommands.EditOrderCommand;
import seedu.sellsavvy.logic.commands.ordercommands.FilterOrderCommand;
import seedu.sellsavvy.logic.commands.ordercommands.ListOrderCommand;
import seedu.sellsavvy.logic.commands.ordercommands.MarkOrderCommand;
import seedu.sellsavvy.logic.commands.ordercommands.UnmarkOrderCommand;
import seedu.sellsavvy.logic.commands.customercommands.DeleteCustomerCommand;
import seedu.sellsavvy.logic.parser.exceptions.ParseException;

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

        switch (commandWord.toLowerCase()) {

        case AddCustomerCommand.COMMAND_WORD:
        case AddCustomerCommand.COMMAND_ALIAS:
            return new AddPersonCommandParser().parse(arguments);

        case EditCustomerCommand.COMMAND_WORD:
        case EditCustomerCommand.COMMAND_ALIAS:
            return new EditPersonCommandParser().parse(arguments);

        case DeleteCustomerCommand.COMMAND_WORD:
        case DeleteCustomerCommand.COMMAND_ALIAS:
            return new DeletePersonCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCustomerCommand.COMMAND_WORD:
        case FindCustomerCommand.COMMAND_ALIAS:
            return new FindPersonCommandParser().parse(arguments);

        case ListCustomerCommand.COMMAND_WORD:
        case ListCustomerCommand.COMMAND_ALIAS:
            return new ListCustomerCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ListOrderCommand.COMMAND_WORD:
        case ListOrderCommand.COMMAND_ALIAS:
            return new ListOrderCommandParser().parse(arguments);

        case FilterOrderCommand.COMMAND_WORD:
        case FilterOrderCommand.COMMAND_ALIAS:
            return new FilterOrderCommandParser().parse(arguments);

        case AddOrderCommand.COMMAND_WORD:
        case AddOrderCommand.COMMAND_ALIAS:
            return new AddOrderCommandParser().parse(arguments);

        case DeleteOrderCommand.COMMAND_WORD:
        case DeleteOrderCommand.COMMAND_ALIAS:
            return new DeleteOrderCommandParser().parse(arguments);

        case EditOrderCommand.COMMAND_WORD:
        case EditOrderCommand.COMMAND_ALIAS:
            return new EditOrderCommandParser().parse(arguments);

        case MarkOrderCommand.COMMAND_WORD:
        case MarkOrderCommand.COMMAND_ALIAS:
            return new MarkOrderCommandParser().parse(arguments);

        case UnmarkOrderCommand.COMMAND_WORD:
        case UnmarkOrderCommand.COMMAND_ALIAS:
            return new UnmarkOrderCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
