package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListLogsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListLogsCommand object
 */
public class ListLogsParser implements Parser<ListLogsCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListLogsCommand parse(String args) throws ParseException {
        try {
            String nric = ParserUtil.parseNric(args);
            return new ListLogsCommand(nric);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListLogsCommand.MESSAGE_USAGE), pe);
        }
    }
}
