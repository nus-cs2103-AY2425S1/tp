package spleetwaise.transaction.logic.parser;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import spleetwaise.commons.core.LogsCenter;
import spleetwaise.commons.logic.commands.Command;
import spleetwaise.commons.logic.parser.exceptions.ParseException;
import spleetwaise.transaction.logic.commands.AddCommand;
import spleetwaise.transaction.logic.commands.ClearCommand;
import spleetwaise.transaction.logic.commands.DeleteCommand;
import spleetwaise.transaction.logic.commands.EditCommand;
import spleetwaise.transaction.logic.commands.FilterCommand;
import spleetwaise.transaction.logic.commands.ListCommand;
import spleetwaise.transaction.logic.commands.MarkCommand;
import spleetwaise.transaction.logic.commands.UnmarkCommand;

/**
 * Parses user input.
 */
public class TransactionBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT =
            Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(TransactionBookParser.class);

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
            return null;
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
        case ListCommand.COMMAND_WORD:
            return new ListCommand();
        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();
        case FilterCommand.COMMAND_WORD:
            return new FilterCommandParser().parse(arguments);
        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);
        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);
        case MarkCommand.COMMAND_WORD:
            return new MarkCommandParser().parse(arguments);
        case UnmarkCommand.COMMAND_WORD:
            return new UnmarkCommandParser().parse(arguments);
        default:
            return null;
        }
    }

}
