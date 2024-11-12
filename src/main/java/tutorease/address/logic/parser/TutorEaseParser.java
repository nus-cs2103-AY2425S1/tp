package tutorease.address.logic.parser;

import static tutorease.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorease.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tutorease.address.commons.core.LogsCenter;
import tutorease.address.logic.commands.ClearCommand;
import tutorease.address.logic.commands.Command;
import tutorease.address.logic.commands.ContactCommand;
import tutorease.address.logic.commands.ExitCommand;
import tutorease.address.logic.commands.HelpCommand;
import tutorease.address.logic.commands.LessonCommand;
import tutorease.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class TutorEaseParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(TutorEaseParser.class);

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

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {
        case ContactCommand.COMMAND_WORD:
            return new ContactCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case LessonCommand.COMMAND_WORD:
            return new LessonCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
