package seedu.hireme.logic.parser;

import static seedu.hireme.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hireme.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.hireme.commons.core.LogsCenter;
import seedu.hireme.logic.commands.AddCommand;
import seedu.hireme.logic.commands.ChartCommand;
import seedu.hireme.logic.commands.ClearCommand;
import seedu.hireme.logic.commands.Command;
import seedu.hireme.logic.commands.DeleteCommand;
import seedu.hireme.logic.commands.ExitCommand;
import seedu.hireme.logic.commands.FilterCommand;
import seedu.hireme.logic.commands.FindCommand;
import seedu.hireme.logic.commands.HelpCommand;
import seedu.hireme.logic.commands.ListCommand;
import seedu.hireme.logic.commands.SortCommand;
import seedu.hireme.logic.commands.StatusCommand;
import seedu.hireme.logic.parser.exceptions.ParseException;
import seedu.hireme.model.internshipapplication.Status;

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
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
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

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case StatusCommand.COMMAND_WORD_ACCEPT:
            return new StatusCommandParser(Status.ACCEPTED).parse(arguments);

        case StatusCommand.COMMAND_WORD_PENDING:
            return new StatusCommandParser(Status.PENDING).parse(arguments);

        case StatusCommand.COMMAND_WORD_REJECT:
            return new StatusCommandParser(Status.REJECTED).parse(arguments);

        case ClearCommand.COMMAND_WORD:
            if (!arguments.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
            }
            return new ClearCommand();

        case FilterCommand.COMMAND_WORD:
            return new FilterCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            if (!arguments.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            if (!arguments.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExitCommand.MESSAGE_USAGE));
            }
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            if (!arguments.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
            }
            return new HelpCommand();

        case ChartCommand.COMMAND_WORD:
            if (!arguments.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChartCommand.MESSAGE_USAGE));
            }
            return new ChartCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
