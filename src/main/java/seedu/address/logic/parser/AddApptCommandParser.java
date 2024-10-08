package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMEPERIOD;

import seedu.address.commons.util.DateUtil;
import seedu.address.logic.commands.AddApptCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricMatchesPredicate;

/**
 * Parses input arguments and creates a new AddApptCommand object
 */
public class AddApptCommandParser implements Parser<AddApptCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddApptCommand
     * and returns an AddAppt object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddApptCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NRIC, PREFIX_NAME, PREFIX_DATE, PREFIX_TIMEPERIOD);

        Nric patientNric;
        String newApptName;
        String newApptDate;
        String newApptTime;

        newApptName = argMultimap.getPreamble();
        if (!Appointment.isValidAppointmentName(newApptName)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddApptCommand.MESSAGE_USAGE));
        }

        newApptDate = argMultimap.getValue(PREFIX_DATE).orElse("");
        if (!DateUtil.isValidDate(newApptDate)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddApptCommand.MESSAGE_USAGE));
        }

        try {
            patientNric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).orElse(""));
            newApptTime = argMultimap.getValue(PREFIX_TIMEPERIOD).orElse("");
            Appointment.checkIsTimePeriodValid(newApptTime);
        } catch (ParseException | IllegalArgumentException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddApptCommand.MESSAGE_USAGE), pe);
        }

        return new AddApptCommand(new NricMatchesPredicate(patientNric), newApptName, newApptDate, newApptTime);
    }
}
