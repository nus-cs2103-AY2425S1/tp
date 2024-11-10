package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CONFIRM_COMMAND;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CancelPendingCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ConfirmClearCommand;
import seedu.address.logic.commands.ConfirmDeleteCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeletePersonCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ViewCommand;
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
    private Command pendingCommand = null;

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {

        if (this.havePendingCommand()) {
            return executePendingCommand(userInput);
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

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            DeleteCommand deleteCommand = new DeleteCommandParser().parse(arguments);
            if (deleteCommand instanceof DeletePersonCommand deletePersonCommand) {
                this.pendingCommand = deletePersonCommand;
                return new ConfirmDeleteCommand(deletePersonCommand.getTargetIndex());
            } else {
                return deleteCommand;
            }

        case ClearCommand.COMMAND_WORD:
            this.pendingCommand = new ClearCommandParser().parse(arguments);
            return new ConfirmClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        case FilterCommand.COMMAND_WORD:
            return new FilterCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    private boolean havePendingCommand() {
        return this.pendingCommand != null;
    }

    /**
     * Polls the pending command.
     *
     * @return the pending command
     */
    public Command pollPendingCommand() {
        Command command = this.pendingCommand;
        this.pendingCommand = null;
        return command;
    }

    /**
     * Parses user input into command for executing pending command.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command executePendingCommand(String userInput) throws ParseException {

        if (userInput.equalsIgnoreCase("Y")) {
            return this.pollPendingCommand();
        }

        if (userInput.equalsIgnoreCase("N")) {
            this.pollPendingCommand();
            return new CancelPendingCommand();
        }

        logger.finer("This user input caused a ParseException: " + userInput);
        throw new ParseException(MESSAGE_INVALID_CONFIRM_COMMAND);
    }
}
