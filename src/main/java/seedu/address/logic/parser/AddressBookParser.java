package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddNotesCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ScheduleAllCommand;
import seedu.address.logic.commands.ScheduleDateCommand;
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

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            logger.fine(String.format("Add command identified, parsing args..."));
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            logger.fine(String.format("Edit command identified, parsing args..."));
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            logger.fine(String.format("Delete command identified, parsing args..."));
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            logger.fine(String.format("Clear command identified."));
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            logger.fine(String.format("Find command identified, parsing args..."));
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            logger.fine(String.format("List command identified."));
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            logger.fine(String.format("Exit command identified."));
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            logger.fine(String.format("Help command identified."));
            return new HelpCommand();

        case AddNotesCommand.COMMAND_WORD:
            logger.fine(String.format("Add Notes command identified, parsing args..."));
            return new AddNotesCommandParser().parse(arguments);

        case ViewCommand.COMMAND_WORD:
            logger.fine(String.format("View command identified, parsing args..."));
            return new ViewCommandParser().parse(arguments);

        case AddAppointmentCommand.COMMAND_WORD:
            logger.fine(String.format("Add Appointment command identified, parsing args..."));
            return new AddAppointmentCommandParser().parse(arguments);

        case DeleteAppointmentCommand.COMMAND_WORD:
            logger.fine(String.format("Delete Appointment command identified, parsing args..."));
            return new DeleteAppointmentCommandParser().parse(arguments);

        case ScheduleDateCommand.COMMAND_WORD:
            logger.fine(String.format("Schedule Date command identified, parsing args..."));
            return new ScheduleDateCommandParser().parse(arguments);

        case ScheduleAllCommand.COMMAND_WORD:
            logger.fine(String.format("Schedule All command identified, parsing args..."));
            return new ScheduleAllCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
