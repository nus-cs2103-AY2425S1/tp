package bizbook.logic.parser;

import static bizbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static bizbook.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bizbook.commons.core.LogsCenter;
import bizbook.logic.commands.AddCommand;
import bizbook.logic.commands.AddNotesCommand;
import bizbook.logic.commands.ClearCommand;
import bizbook.logic.commands.Command;
import bizbook.logic.commands.DeleteCommand;
import bizbook.logic.commands.DeleteNotesCommand;
import bizbook.logic.commands.EditCommand;
import bizbook.logic.commands.EditNotesCommand;
import bizbook.logic.commands.ExitCommand;
import bizbook.logic.commands.ExportCommand;
import bizbook.logic.commands.FindCommand;
import bizbook.logic.commands.HelpCommand;
import bizbook.logic.commands.ListCommand;
import bizbook.logic.commands.PinCommand;
import bizbook.logic.commands.ViewCommand;
import bizbook.logic.parser.exceptions.ParseException;

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

        final String commandWord = matcher.group("commandWord").toLowerCase();
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

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        case AddNotesCommand.COMMAND_WORD:
            return new AddNotesCommandParser().parse(arguments);

        case EditNotesCommand.COMMAND_WORD:
            return new EditNotesCommandParser().parse(arguments);

        case PinCommand.COMMAND_WORD:
            return new PinCommandParser().parse(arguments);

        case DeleteNotesCommand.COMMAND_WORD:
            return new DeleteNotesCommandParser().parse(arguments);

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
