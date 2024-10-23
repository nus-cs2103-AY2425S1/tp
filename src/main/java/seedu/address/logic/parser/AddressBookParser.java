package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListingAddCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.ShowCommand;
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
     * Used to keep track of all the commands available.
     */
    private final Map<String, ParseCommandFunction> commands;

    /**
     * Creates an AddressBookParser and registers all commands available.
     */
    public AddressBookParser() {
        commands = new HashMap<>();
        commands.put(AddCommand.COMMAND_WORD, arguments -> new AddCommandParser().parse(arguments));
        commands.put(EditCommand.COMMAND_WORD, arguments -> new EditCommandParser().parse(arguments));
        commands.put(DeleteCommand.COMMAND_WORD, arguments -> new DeleteCommandParser().parse(arguments));
        commands.put(ClearCommand.COMMAND_WORD, arguments -> new ClearCommand());
        commands.put(FindCommand.COMMAND_WORD, arguments -> new FindCommandParser().parse(arguments));
        commands.put(ListCommand.COMMAND_WORD, arguments -> new ListCommand());
        commands.put(ExitCommand.COMMAND_WORD, arguments -> new ExitCommand());
        commands.put(HelpCommand.COMMAND_WORD, arguments -> new HelpCommand());
        commands.put(FindTagCommand.COMMAND_WORD, arguments -> new FindTagCommandParser().parse(arguments));
        commands.put(RemarkCommand.COMMAND_WORD, arguments -> new RemarkCommandParser().parse(arguments));
        commands.put(ShowCommand.COMMAND_WORD, arguments -> new ShowCommandParser().parse(arguments));
        commands.put(ListingAddCommand.COMMAND_WORD_PREFIX, arguments -> new ListingCommandsParser().parse(arguments));
    }

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

        final String commandWord = matcher.group("commandWord").trim();
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.info("Command word: " + commandWord + "; Arguments: " + arguments);

        if (!commands.containsKey(commandWord)) {
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

        return commands.get(commandWord).process(arguments);
    }

    /**
     * This is a private functional interface to hold the command parsing process.
     */
    @FunctionalInterface
    private interface ParseCommandFunction {
        Command process(String arguments) throws ParseException;
    }

}
