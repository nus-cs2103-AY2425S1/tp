package seedu.address.logic.parser;

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
    @Override
    public MarkAttendanceCommand parse(String userInput) throws ParseException {
        String[] parts = userInput.trim().split("\\s+");
        if (parts.length != 2) {
            throw new ParseException("Invalid command format. " + MarkAttendanceCommand.MESSAGE_USAGE);
        }

        String eventName = parts[0];
        Index index;
        try {
            index = ParserUtil.parseIndex(parts[1]);
        } catch (ParseException pe) {
            throw new ParseException("Invalid index provided.");
        }

        return new MarkAttendanceCommand(eventName, index);
    }
}
