package seedu.ddd.logic.parser;

import static seedu.ddd.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ddd.logic.Messages.MESSAGE_INVALID_FLAGS;
import static seedu.ddd.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.ddd.logic.commands.AddCommand.FLAG_PARSE_ERROR;
import static seedu.ddd.logic.parser.CliFlags.FLAG_CLIENT;
import static seedu.ddd.logic.parser.CliFlags.FLAG_EVENT;
import static seedu.ddd.logic.parser.CliFlags.FLAG_VENDOR;
import static seedu.ddd.logic.parser.ParserUtil.parseFlags;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.ddd.commons.core.LogsCenter;
import seedu.ddd.logic.commands.AddCommand;
import seedu.ddd.logic.commands.ClearCommand;
import seedu.ddd.logic.commands.Command;
import seedu.ddd.logic.commands.DeleteCommand;
import seedu.ddd.logic.commands.EditCommand;
import seedu.ddd.logic.commands.ExitCommand;
import seedu.ddd.logic.commands.HelpCommand;
import seedu.ddd.logic.commands.ListCommand;
import seedu.ddd.logic.parser.exceptions.ParseException;

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

        ArgumentMultimap flagMultimap = ArgumentTokenizer.tokenize(arguments, FLAG_CLIENT, FLAG_VENDOR, FLAG_EVENT);
        CommandFlag commandFlag = parseFlags(flagMultimap);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:

            if (commandFlag == null) {
                throw new ParseException(String.format(MESSAGE_INVALID_FLAGS, FLAG_PARSE_ERROR));
            }

            switch(commandFlag) {

            case CLIENT:
                return new AddClientCommandParser().parse(arguments);

            case VENDOR:
                return new AddVendorCommandParser().parse(arguments);

            case EVENT:
                return new AddEventCommandParser().parse(arguments);

            default:
                logger.finer("This user input caused a ParseException: " + userInput);
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ListCommand.COMMAND_WORD:

            if (commandFlag == null) {
                return new ListContactCommandParser().parse(arguments);
            }

            switch(commandFlag) {

            case CLIENT:
                return new ListClientCommandParser().parse(arguments);

            case VENDOR:
                return new ListVendorCommandParser().parse(arguments);

            case EVENT:
                return new ListEventCommandParser().parse(arguments);

            default:
                logger.finer("This user input caused a ParseException: " + userInput);
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
