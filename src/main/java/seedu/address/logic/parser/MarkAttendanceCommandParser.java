package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkAttendanceCommand object
 */
public class MarkAttendanceCommandParser implements Parser<MarkAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkAttendanceCommand
     * and returns a MarkAttendanceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkAttendanceCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MarkAttendanceCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE), pe);
        }
    }

}
