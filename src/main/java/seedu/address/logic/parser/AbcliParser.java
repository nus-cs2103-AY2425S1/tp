package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PARSER_MODE;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.buyer.BuyerCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.meetup.MeetUpCommandParser;
import seedu.address.logic.parser.property.PropertyCommandParser;

/**
 * Parses user input.
 */
public class AbcliParser {

    private static final BuyerCommandParser buyerCommandParser = new BuyerCommandParser();
    private static final MeetUpCommandParser meetUpCommandParser = new MeetUpCommandParser();
    private static final PropertyCommandParser propertyCommandParser = new PropertyCommandParser();
    private static CommandParser currentParser = buyerCommandParser;

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AbcliParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public static Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        return currentParser.parseCommand(commandWord, arguments);
    }

    /**
     * Switches the mode of the parser.
     *
     * @param mode mode to switch to
     * @throws ParseException if the mode doesn't exist
     */
    public static void switchMode(ParserMode mode) throws ParseException {
        requireNonNull(mode);

        switch (mode) {

        case BUYER:
            currentParser = buyerCommandParser;
            break;

        case MEETUP:
            currentParser = meetUpCommandParser;
            break;

        case PROPERTY:
            currentParser = propertyCommandParser;
            break;

        default:
            logger.finer("This mode does not exist: " + mode);
            throw new ParseException(MESSAGE_INVALID_PARSER_MODE);
        }
    }

}
