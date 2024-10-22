package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.DeletePersonCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandField>\\S+)(?<keywords>.*)");
    private static final Logger logger = LogsCenter.getLogger(DeleteCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a corresponding ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(trimmedArgs);

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandField = matcher.group("commandField");
        final String keywords = matcher.group("keywords");


        if (commandField.equals(DeletePersonCommand.COMMAND_FIELD)) {
            return new DeletePersonCommandParser().parse(keywords);
        }

        if (commandField.equals(DeleteEventCommand.COMMAND_FIELD)) {
            return new DeleteEventCommandParser().parse(keywords);
        }

        logger.finer("This user input caused a ParseException: view " + args);
        throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
    }

}
