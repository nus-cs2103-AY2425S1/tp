package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;

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
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            return new ListAppointmentsCommand(Optional.empty(), Optional.empty());
        }

        String[] arguments = trimmedArgs.split("\\s+");

        Optional<LocalDate> dateFilter = Optional.empty();
        Optional<LocalTime> timeFilter = Optional.empty();

        try {
            if (arguments.length >= 1 && !arguments[0].isEmpty()) {
                dateFilter = Optional.of(LocalDate.parse(arguments[0]));
            }
            if (arguments.length >= 2) {
                timeFilter = Optional.of(LocalTime.parse(arguments[1]));
                if (dateFilter.isEmpty()) {
                    throw new ParseException("Time filter can only be used with a date filter.");
                }
            }
        } catch (DateTimeParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListAppointmentsCommand.MESSAGE_USAGE));
        }

        return new ListAppointmentsCommand(dateFilter, timeFilter);
    }
}
