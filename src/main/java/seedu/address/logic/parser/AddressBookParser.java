package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitContactCommand;
import seedu.address.logic.commands.HelpContactCommand;
import seedu.address.logic.commands.contact.commands.AddContactCommand;
import seedu.address.logic.commands.contact.commands.ClearContactCommand;
import seedu.address.logic.commands.contact.commands.DeleteContactCommand;
import seedu.address.logic.commands.contact.commands.EditContactCommand;
import seedu.address.logic.commands.contact.commands.FindContactCommand;
import seedu.address.logic.commands.contact.commands.ListContactCommand;
import seedu.address.logic.commands.event.commands.AddEventCommand;
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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpContactCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddContactCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditContactCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteContactCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearContactCommand.COMMAND_WORD:
            return new ClearContactCommand();

        case FindContactCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListContactCommand.COMMAND_WORD:
            return new ListContactCommand();

        case ExitContactCommand.COMMAND_WORD:
            return new ExitContactCommand();

        case HelpContactCommand.COMMAND_WORD:
            return new HelpContactCommand();

        case AddEventCommand.COMMAND_WORD:
            return new NewEventCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
