package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddConcertCommand;
import seedu.address.logic.commands.AddConcertContactCommand;
import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteConcertCommand;
import seedu.address.logic.commands.DeleteConcertContactCommand;
import seedu.address.logic.commands.DeletePersonCommand;
import seedu.address.logic.commands.EditConcertCommand;
import seedu.address.logic.commands.EditPersonCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindConcertCommand;
import seedu.address.logic.commands.FindConcertContactCommand;
import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListConcertCommand;
import seedu.address.logic.commands.ListConcertContactCommand;
import seedu.address.logic.commands.ListPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile(
            "(?<commandWord>\\S+)(?<arguments>.*)");
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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e.,
        // FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddPersonCommand.COMMAND_WORD:
            return new AddPersonCommandParser().parse(arguments);

        case EditPersonCommand.COMMAND_WORD:
            return new EditPersonCommandParser().parse(arguments);

        case DeletePersonCommand.COMMAND_WORD:
            return new DeletePersonCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindPersonCommand.COMMAND_WORD:
            return new FindPersonCommandParser().parse(arguments);

        case FindConcertCommand.COMMAND_WORD:
            return new FindConcertCommandParser().parse(arguments);

        case FindConcertContactCommand.COMMAND_WORD:
            return new FindConcertContactCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListConcertCommand.COMMAND_WORD:
            return new ListConcertCommand();

        case ListPersonCommand.COMMAND_WORD:
            return new ListPersonCommand();

        case ListConcertContactCommand.COMMAND_WORD:
            return new ListConcertContactCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddConcertCommand.COMMAND_WORD:
            return new AddConcertCommandParser().parse(arguments);

        case AddConcertContactCommand.COMMAND_WORD:
            return new AddConcertContactCommandParser().parse(arguments);

        case DeleteConcertContactCommand.COMMAND_WORD:
            return new DeleteConcertContactCommandParser().parse(arguments);

        case DeleteConcertCommand.COMMAND_WORD:
            return new DeleteConcertCommandParser().parse(arguments);

        case EditConcertCommand.COMMAND_WORD:
            return new EditConcertCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
