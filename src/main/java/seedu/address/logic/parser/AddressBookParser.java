package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.Pair;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddProductCommand;
import seedu.address.logic.commands.AddSupplierCommand;
import seedu.address.logic.commands.AssignProductCommand;
import seedu.address.logic.commands.AutoCompleteCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteProductCommand;
import seedu.address.logic.commands.DeleteSupplierCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.SetThresholdCommand;
import seedu.address.logic.commands.UnassignProductCommand;
import seedu.address.logic.commands.UpdateStockLevelCommand;
import seedu.address.logic.commands.ViewProductCommand;
import seedu.address.logic.commands.ViewSupplierCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @param autoComplete whether the user input is for auto-completion
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, Boolean autoComplete) throws ParseException {

        if (autoComplete) {
            logger.fine("AutoComplete trigger with input: " + userInput);
            Pair<String, String> rightmostArg = ArgumentTokenizer.getRightmostArgument(userInput);
            System.out.println("AutoComplete trigger with input: " + rightmostArg.getKey()
                + " " + rightmostArg.getValue());
            return new AutoCompleteCommand(rightmostArg.getKey(), rightmostArg.getValue());
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

        switch (commandWord.toLowerCase()) {

        case AddSupplierCommand.COMMAND_WORD:
            return new AddSupplierCommandParser().parse(arguments);

        case AddProductCommand.COMMAND_WORD:
            return new AddProductCommandParser().parse(arguments);

        case DeleteSupplierCommand.COMMAND_WORD:
            return new DeleteSupplierCommandParser().parse(arguments);

        case DeleteProductCommand.COMMAND_WORD:
            return new DeleteProductCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ViewProductCommand.COMMAND_WORD:
            return new ViewProductCommandParser().parse(arguments);

        case ViewSupplierCommand.COMMAND_WORD:
            return new ViewSupplierCommandParser().parse(arguments);

        case AssignProductCommand.COMMAND_WORD:
            return new AssignProductCommandParser().parse(arguments);

        case UnassignProductCommand.COMMAND_WORD:
            return new UnassignProductCommandParser().parse(arguments);

        case SetThresholdCommand.COMMAND_WORD:
            return new SetThresholdCommandParser().parse(arguments);

        case UpdateStockLevelCommand.COMMAND_WORD:
            return new UpdateStockLevelCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        return parseCommand(userInput, false);
    }
}
