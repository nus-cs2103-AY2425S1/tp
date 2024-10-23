package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        Optional<LocalDate> dateFilter = Optional.empty();
        Optional<LocalTime> timeFilter = Optional.empty();

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            String dateTimeString = argMultimap.getValue(PREFIX_DATE).get();
            String[] dateTimeParts = dateTimeString.split("\\s+", 2);

            try {
                dateFilter = Optional.of(LocalDate.parse(dateTimeParts[0]));
                if (dateTimeParts.length > 1) {
                    String timeString = dateTimeParts[1];
                    // Parse time in HHmm format
                    timeFilter = Optional.of(LocalTime.of(
                            Integer.parseInt(timeString.substring(0, 2)),
                            Integer.parseInt(timeString.substring(2))
                    ));
                }
            } catch (DateTimeParseException | NumberFormatException | StringIndexOutOfBoundsException e) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ListAppointmentsCommand.MESSAGE_USAGE));
            }
        }

        return new ListAppointmentsCommand(dateFilter, timeFilter);
    }
}
