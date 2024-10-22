package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.personcommands.AddPersonCommand;
import seedu.address.logic.commands.personcommands.DeletePersonCommand;
import seedu.address.logic.commands.personcommands.EditPersonCommand;
import seedu.address.logic.commands.personcommands.ExitCommand;
import seedu.address.logic.commands.personcommands.FindPersonCommand;
import seedu.address.logic.commands.personcommands.ListCommand;
import seedu.address.logic.commands.personcommands.SearchPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile(
        "(?<commandWord>\\S+)(?<combined>\\s+(?<modelType>\\S+)?(?<arguments>.*)?)?"
    );
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
        final String modelTypeShortHand = matcher.group("modelType");
        final ModelType modelType = ModelType.fromShorthand(modelTypeShortHand);
        final String arguments = (modelType == ModelType.NEITHER)
                ? matcher.group("combined")
                : matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Model Type: " + modelType + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddPersonCommand.COMMAND_WORD:
            return new AddCommandParser().parse(modelType, arguments);

        case EditPersonCommand.COMMAND_WORD:
            return new EditCommandParser().parse(modelType, arguments);

        case DeletePersonCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(modelType, arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindPersonCommand.COMMAND_WORD:
            return new FindCommandParser().parse(modelType, arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case SearchPersonCommand.COMMAND_WORD:
            return new SearchCommandParser().parse(modelType, arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
