package seedu.address.logic.parser;

import seedu.address.logic.commands.ListAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListAttendanceCommand object
 */
public class ListAttendanceCommandParser implements Parser<ListAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListAttendanceCommand
     * and returns a ListAttendanceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ListAttendanceCommand parse(String userInput) throws ParseException {
        String[] parts = userInput.trim().split("\\s+");
        if (parts.length != 2) {
            throw new ParseException("Invalid command format. " + ListAttendanceCommand.MESSAGE_USAGE);
        }

        String eventName = parts[0];
        String status = parts[1].toLowerCase();

        boolean isPresent;
        if (status.equals("present")) {
            isPresent = true;
        } else if (status.equals("absent")) {
            isPresent = false;
        } else {
            throw new ParseException("Status must be 'present' or 'absent'.");
        }

        return new ListAttendanceCommand(eventName, isPresent);
    }
}
