package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddListingCommand;
import seedu.address.logic.commands.ClearListingCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteListingCommand;
import seedu.address.logic.commands.EditListingCommand;
import seedu.address.logic.commands.FindListingCommand;
import seedu.address.logic.commands.ListListingsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input specifically for listing commands.
 */
public class ListingParser {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(ListingParser.class);

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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListListingsCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddListingCommand.COMMAND_WORD:
            return new AddListingCommandParser().parse(arguments);

        case EditListingCommand.COMMAND_WORD:
            return new EditListingCommandParser().parse(arguments);

        case DeleteListingCommand.COMMAND_WORD:
            return new DeleteListingCommandParser().parse(arguments);

        case ClearListingCommand.COMMAND_WORD:
            return new ClearListingCommand();

        case FindListingCommand.COMMAND_WORD:
            return new FindListingsCommandParser().parse(arguments);

        case ListListingsCommand.COMMAND_WORD:
            return new ListListingsCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
