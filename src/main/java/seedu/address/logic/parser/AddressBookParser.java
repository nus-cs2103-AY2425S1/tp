package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.consultation.AddConsultCommand;
import seedu.address.logic.commands.consultation.AddToConsultCommand;
import seedu.address.logic.commands.consultation.DeleteConsultCommand;
import seedu.address.logic.commands.consultation.ExportConsultCommand;
import seedu.address.logic.commands.consultation.ImportConsultCommand;
import seedu.address.logic.commands.consultation.ListConsultsCommand;
import seedu.address.logic.commands.consultation.RemoveFromConsultCommand;
import seedu.address.logic.commands.lesson.AddLessonCommand;
import seedu.address.logic.commands.lesson.AddToLessonCommand;
import seedu.address.logic.commands.lesson.DeleteLessonCommand;
import seedu.address.logic.commands.lesson.ListLessonsCommand;
import seedu.address.logic.commands.lesson.MarkLessonAttendanceCommand;
import seedu.address.logic.commands.lesson.MarkLessonParticipationCommand;
import seedu.address.logic.commands.lesson.RemoveFromLessonCommand;
import seedu.address.logic.parser.consultation.AddConsultCommandParser;
import seedu.address.logic.parser.consultation.AddToConsultCommandParser;
import seedu.address.logic.parser.consultation.DeleteConsultCommandParser;
import seedu.address.logic.parser.consultation.ExportConsultCommandParser;
import seedu.address.logic.parser.consultation.ImportConsultCommandParser;
import seedu.address.logic.parser.consultation.RemoveFromConsultCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.lesson.AddLessonCommandParser;
import seedu.address.logic.parser.lesson.AddToLessonCommandParser;
import seedu.address.logic.parser.lesson.DeleteLessonCommandParser;
import seedu.address.logic.parser.lesson.MarkLessonAttendanceCommandParser;
import seedu.address.logic.parser.lesson.MarkLessonParticipationCommandParser;
import seedu.address.logic.parser.lesson.RemoveFromLessonCommandParser;

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

        // Note to developers: Change the log level in config.json to enable lower level
        // (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case AddConsultCommand.COMMAND_WORD:
            return new AddConsultCommandParser().parse(arguments);

        case RemoveFromConsultCommand.COMMAND_WORD:
            return new RemoveFromConsultCommandParser().parse(arguments);

        case AddToConsultCommand.COMMAND_WORD:
            return new AddToConsultCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeleteConsultCommand.COMMAND_WORD:
            return new DeleteConsultCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListConsultsCommand.COMMAND_WORD:
            return new ListConsultsCommand();

        case ExportConsultCommand.COMMAND_WORD:
            return new ExportConsultCommandParser().parse(arguments);

        case ImportConsultCommand.COMMAND_WORD:
            return new ImportConsultCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);

        case ImportCommand.COMMAND_WORD:
            return new ImportCommandParser().parse(arguments);

        case AddLessonCommand.COMMAND_WORD:
            return new AddLessonCommandParser().parse(arguments);

        case DeleteLessonCommand.COMMAND_WORD:
            return new DeleteLessonCommandParser().parse(arguments);

        case ListLessonsCommand.COMMAND_WORD:
            return new ListLessonsCommand();

        case AddToLessonCommand.COMMAND_WORD:
            return new AddToLessonCommandParser().parse(arguments);

        case RemoveFromLessonCommand.COMMAND_WORD:
            return new RemoveFromLessonCommandParser().parse(arguments);

        case MarkLessonAttendanceCommand.COMMAND_WORD:
            return new MarkLessonAttendanceCommandParser().parse(arguments);

        case MarkLessonParticipationCommand.COMMAND_WORD:
            return new MarkLessonParticipationCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
