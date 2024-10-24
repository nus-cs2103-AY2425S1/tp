package seedu.academyassist.logic.parser;

import static seedu.academyassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.academyassist.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.academyassist.commons.core.LogsCenter;
import seedu.academyassist.logic.commands.AddClassCommand;
import seedu.academyassist.logic.commands.AddCommand;
import seedu.academyassist.logic.commands.ClearCommand;
import seedu.academyassist.logic.commands.Command;
import seedu.academyassist.logic.commands.DeleteCommand;
import seedu.academyassist.logic.commands.DetailCommand;
import seedu.academyassist.logic.commands.EditCommand;
import seedu.academyassist.logic.commands.ExitCommand;
import seedu.academyassist.logic.commands.FilterCommand;
import seedu.academyassist.logic.commands.FindCommand;
import seedu.academyassist.logic.commands.HelpCommand;
import seedu.academyassist.logic.commands.ListCommand;
import seedu.academyassist.logic.commands.SortCommand;
import seedu.academyassist.logic.commands.TrackSubjectCommand;
import seedu.academyassist.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AcademyAssistParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AcademyAssistParser.class);

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

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case AddClassCommand.COMMAND_WORD:
            return new AddClassCommandParser().parse(arguments);

        case TrackSubjectCommand.COMMAND_WORD:
            return new TrackSubjectCommand();
            
        case DetailCommand.COMMAND_WORD:
            return new DetailCommandParser().parse(arguments);

        case FilterCommand.COMMAND_WORD:
            return new FilterCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
