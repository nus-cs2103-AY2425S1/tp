package seedu.address.logic.parser.volunteercommandparser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.volunteercommands.FindVolunteerCommand;
import seedu.address.logic.commands.volunteercommands.VolunteerDeleteCommand;
import seedu.address.logic.commands.volunteercommands.VolunteerNewCommand;
import seedu.address.logic.commands.volunteercommands.VolunteerViewCommand;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses user input into volunteer-related commands.
 */
public class VolunteerCommandParser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input and returns the appropriate command based on the command word.
     *
     * @param userInput The full user input string.
     * @return The command based on the command word parsed from the input.
     * @throws ParseException If the user input does not match the expected command format.
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
        case VolunteerNewCommand.COMMAND_WORD:
            return new VolunteerNewCommandParser().parse(arguments);
        case VolunteerDeleteCommand.COMMAND_WORD:
            return new VolunteerDeleteCommandParser().parse(arguments);
        case FindVolunteerCommand.COMMAND_WORD:
            return new FindVolunteerCommandParser().parse(arguments);
        case VolunteerViewCommand.COMMAND_WORD:
            return new VolunteerViewCommandParser().parse(arguments);
        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }


}
