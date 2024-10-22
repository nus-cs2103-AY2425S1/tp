package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindEventCommand;
import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandField>\\S+)(?<keywords>.*)");
    private static final Logger logger = LogsCenter.getLogger(FindCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a corresponding FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(trimmedArgs);

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandField = matcher.group("commandField");
        final String keywords = matcher.group("keywords");

        if (commandField.equals(FindPersonCommand.COMMAND_FIELD)) {
            return new FindPersonCommandParser().parse(keywords);
        }

        if (commandField.equals(FindEventCommand.COMMAND_FIELD)) {
            return new FindEventCommandParser().parse(keywords);
        }

        logger.finer("This user input caused a ParseException: find " + args);
        throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
    }

}
