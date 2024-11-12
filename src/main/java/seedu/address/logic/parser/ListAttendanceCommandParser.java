package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_EVENT, PREFIX_STATUS);

        if (!argMultimap.getValue(PREFIX_EVENT).isPresent() || !argMultimap.getValue(PREFIX_STATUS).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListAttendanceCommand.MESSAGE_USAGE));
        }

        String eventName = argMultimap.getValue(PREFIX_EVENT).get().trim();

        validateEventName(eventName);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT, PREFIX_STATUS);

        if (eventName.isEmpty()) {
            throw new ParseException("Event name cannot be empty.");
        }

        String statusString = argMultimap.getValue(PREFIX_STATUS).get().trim().toLowerCase();

        boolean isPresent;
        if (statusString.equals("present")) {
            isPresent = true;
        } else if (statusString.equals("absent")) {
            isPresent = false;
        } else {
            throw new ParseException("Status must be 'present' or 'absent'.");
        }

        return new ListAttendanceCommand(eventName, isPresent);
    }

    private void validateEventName(String eventName) throws ParseException {
        if (eventName.contains("/")) {
            throw new ParseException("Event name cannot contain '/'.");
        }
    }

}
