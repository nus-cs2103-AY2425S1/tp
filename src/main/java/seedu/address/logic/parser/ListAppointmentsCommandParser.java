package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListAppointmentsCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new ListAppointmentsCommand object.
 */
public class ListAppointmentsCommandParser implements Parser<ListAppointmentsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListAppointmentsCommand
     * and returns a ListAppointmentsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListAppointmentsCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListAppointmentsCommand.MESSAGE_USAGE));
        }
        return new ListAppointmentsCommand();
    }
}
