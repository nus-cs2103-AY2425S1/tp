package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddGradeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GitHubCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.commands.RemoveGradeCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UnmarkCommand;
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

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);

        case ExportCommand.COMMAND_WORD_SHORT_FORM:
            return new ExportCommandParser().parse(arguments);

        case ImportCommand.COMMAND_WORD:
            return new ImportCommandParser().parse(arguments);

        case ImportCommand.COMMAND_WORD_SHORT_FORM:
            return new ImportCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD_SHORT_FORM:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD_SHORT_FORM:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD_SHORT_FORM:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddGradeCommand.COMMAND_WORD_LOWER_CASE:
            return new AddGradeCommandParser().parse(arguments);

        case AddGradeCommand.COMMAND_WORD_SHORT_FORM:
            return new AddGradeCommandParser().parse(arguments);

        case RemoveGradeCommand.COMMAND_WORD_LOWER_CASE:
            return new RemoveGradeCommandParser().parse(arguments);

        case RemoveGradeCommand.COMMAND_WORD_SHORT_FORM:
            return new RemoveGradeCommandParser().parse(arguments);

        case FilterCommand.COMMAND_WORD:
            return new FilterCommandParser().parse(arguments);

        case FilterCommand.COMMAND_WORD_SHORT_FORM:
            return new FilterCommandParser().parse(arguments);

        case MarkCommand.COMMAND_WORD:
            return new MarkCommandParser().parse(arguments);

        case MarkCommand.COMMAND_WORD_SHORT_FORM:
            return new MarkCommandParser().parse(arguments);

        case UnmarkCommand.COMMAND_WORD:
            return new UnmarkCommandParser().parse(arguments);

        case UnmarkCommand.COMMAND_WORD_SHORT_FORM:
            return new UnmarkCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case GitHubCommand.COMMAND_WORD:
            return new GitHubCommandParser().parse(arguments);

        case GitHubCommand.COMMAND_WORD_SHORT_FORM:
            return new GitHubCommandParser().parse(arguments);

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        case ViewCommand.COMMAND_WORD_SHORT_FORM:
            return new ViewCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
