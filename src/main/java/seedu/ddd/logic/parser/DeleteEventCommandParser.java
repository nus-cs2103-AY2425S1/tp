package seedu.ddd.logic.parser;

import static seedu.ddd.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.ddd.commons.core.index.Index;
import seedu.ddd.logic.commands.DeleteEventCommand;
import seedu.ddd.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new DeleteEventCommand object
 */
public class DeleteEventCommandParser implements Parser<DeleteEventCommand> {
    private static final Pattern OPTION_FORMAT = Pattern.compile("(?<flag>-\\w+)\\s+(?<index>\\S+)");
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteEventCommand
     * and returns a DeleteEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteEventCommand parse(String args) throws ParseException {
        final Matcher matcher = OPTION_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE));
        }
        String argIndex = matcher.group("index");
        try {
            Index index = ParserUtil.parseIndex(argIndex);
            return new DeleteEventCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE), pe);
        }
    }
}
