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
import seedu.address.logic.parser.exceptions.InvalidParserModeException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.meetup.MeetUpCommandParser;
import seedu.address.logic.parser.property.PropertyCommandParser;

/**
 * Parses user input.
 */
public class AbcliParser {
    private static ParserMode currentMode = ParserMode.BUYER;

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
        assert currentMode == ParserMode.BUYER || currentMode == ParserMode.MEETUP
                || currentMode == ParserMode.PROPERTY;

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        final CommandParser currentParser;

        switch (currentMode) {

        case MEETUP:
            currentParser = new MeetUpCommandParser();
            break;

        case PROPERTY:
            currentParser = new PropertyCommandParser();
            break;

        default:
            currentParser = new BuyerCommandParser();
        }

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Passing to: " + currentParser + "; Command word: "
                + commandWord + "; Arguments: " + arguments);


        return currentParser.parseCommand(commandWord, arguments);
    }

    /**
     * Switches the mode of the parser.
     *
     * @param mode mode to switch to
     * @throws InvalidParserModeException if the mode doesn't exist
     */
    public static void switchMode(ParserMode mode) throws InvalidParserModeException {
        requireNonNull(mode);
        if (mode != ParserMode.BUYER && mode != ParserMode.MEETUP && mode != ParserMode.PROPERTY) {
            logger.finer("This mode does not exist: " + mode);
            throw new InvalidParserModeException(MESSAGE_INVALID_PARSER_MODE);
        } else {
            currentMode = mode;
        }
    }

}
