package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.contact.commands.AddCommand;
import seedu.address.logic.commands.contact.commands.ClearCommand;
import seedu.address.logic.commands.contact.commands.DeleteCommand;
import seedu.address.logic.commands.contact.commands.EditCommand;
import seedu.address.logic.commands.contact.commands.FindNameCommand;
import seedu.address.logic.commands.contact.commands.FindRoleCommand;
import seedu.address.logic.commands.contact.commands.ListCommand;
import seedu.address.logic.commands.event.commands.AddEventCommand;
import seedu.address.logic.commands.event.commands.AddPersonToEventCommand;
import seedu.address.logic.commands.event.commands.DeleteEventCommand;
import seedu.address.logic.commands.event.commands.EventAddAllCommand;
import seedu.address.logic.commands.event.commands.FindEventCommand;
import seedu.address.logic.commands.event.commands.RemovePersonFromEventCommand;
import seedu.address.logic.commands.searchmode.CheckExcludedCommand;
import seedu.address.logic.commands.searchmode.ClearExcludedCommand;
import seedu.address.logic.commands.searchmode.ExcludePersonCommand;
import seedu.address.logic.commands.searchmode.ExitSearchModeCommand;
import seedu.address.logic.commands.searchmode.InitSearchModeCommand;
import seedu.address.logic.commands.searchmode.SearchModeSearchCommand;
import seedu.address.logic.parser.contact.parser.AddCommandParser;
import seedu.address.logic.parser.contact.parser.DeleteCommandParser;
import seedu.address.logic.parser.contact.parser.EditCommandParser;
import seedu.address.logic.parser.contact.parser.FindNameCommandParser;
import seedu.address.logic.parser.contact.parser.FindRoleCommandParser;
import seedu.address.logic.parser.event.parser.AddPersonToEventParser;
import seedu.address.logic.parser.event.parser.DeleteEventCommandParser;
import seedu.address.logic.parser.event.parser.EventAddAllCommandParser;
import seedu.address.logic.parser.event.parser.FindEventCommandParser;
import seedu.address.logic.parser.event.parser.NewEventCommandParser;
import seedu.address.logic.parser.event.parser.RemovePersonFromEventParser;
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

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindNameCommand.COMMAND_WORD:
        case FindNameCommand.COMMAND_WORD_SHORT_FORM:
            return new FindNameCommandParser().parse(arguments);

        case FindRoleCommand.COMMAND_WORD:
        case FindRoleCommand.COMMAND_WORD_SHORT_FORM:
            return new FindRoleCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddEventCommand.COMMAND_WORD:
            return new NewEventCommandParser().parse(arguments);

        case InitSearchModeCommand.COMMAND_WORD:
        case InitSearchModeCommand.COMMAND_WORD_SHORT_FORM:
            return new InitSearchModeCommand();

        case FindEventCommand.COMMAND_WORD:
        case FindEventCommand.COMMAND_WORD_SHORT_FORM:
            return new FindEventCommandParser().parse(arguments);

        case RemovePersonFromEventCommand.COMMAND_WORD:
            return new RemovePersonFromEventParser().parse(arguments);

        case AddPersonToEventCommand.COMMAND_WORD:
        case AddPersonToEventCommand.COMMAND_WORD_SHORT_FORM:
            return new AddPersonToEventParser().parse(arguments);

        case DeleteEventCommand.COMMAND_WORD:
            return new DeleteEventCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses user input into command for execution in search mode.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseSearchModeCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            logger.warning("This user input caused a ParseException: " + userInput);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Using FindRoleCommandParser to parse user input: " + userInput);
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);
        switch (commandWord) {
        case ExitSearchModeCommand.COMMAND_WORD:
        case ExitSearchModeCommand.COMMAND_WORD_SHORT_FORM:
            return new ExitSearchModeCommand();
        case SearchModeSearchCommand.COMMAND_WORD:
            return new SearchModeSearchCommandParser().parse(arguments);
        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
        case ExcludePersonCommand.COMMAND_WORD:
            return new ExcludePersonCommandParser().parse(arguments);
        case ClearExcludedCommand.COMMAND_WORD:
        case ClearExcludedCommand.COMMAND_WORD_SHORT_FORM:
            return new ClearExcludedCommand();
        case CheckExcludedCommand.COMMAND_WORD:
        case CheckExcludedCommand.COMMAND_WORD_SHORT_FORM:
            return new CheckExcludedCommand();
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();
        case EventAddAllCommand.COMMAND_WORD:
            return new EventAddAllCommandParser().parse(arguments);
        default:

            throw new ParseException(
                    MESSAGE_UNKNOWN_COMMAND + Messages.SEARCHMODE_UNKNOWN_COMMAND);
        }
    }

}
