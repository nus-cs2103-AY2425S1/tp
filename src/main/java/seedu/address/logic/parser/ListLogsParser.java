package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ListLogsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class ListLogsParser implements Parser<ListLogsCommand>{
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListLogsCommand parse(String args) throws ParseException {
        try {
            String NRIC = ParserUtil.parseNRIC(args);
            return new ListLogsCommand(NRIC);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListLogsCommand.MESSAGE_USAGE), pe);
        }
    }
}
