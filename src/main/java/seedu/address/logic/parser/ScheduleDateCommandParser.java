package seedu.address.logic.parser;

import java.time.LocalDate;

import seedu.address.logic.commands.ScheduleDateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.AppointmentContainsDatePredicate;

/**
 * Parses input arguments and creates a new ScheduleDateCommand object.
 */
public class ScheduleDateCommandParser implements Parser<ScheduleDateCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleDateCommand
     * and returns a ScheduleDateCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleDateCommand parse(String args) throws ParseException {
        // Try to parse the date, through ParserUtil
        LocalDate date = ParserUtil.parseLocalDate(args);
        AppointmentContainsDatePredicate predicate = new AppointmentContainsDatePredicate(date);
        return new ScheduleDateCommand(predicate);
    }
}
