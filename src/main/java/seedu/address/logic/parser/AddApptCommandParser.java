package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.AddApptCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMEPERIOD;

import java.util.function.Supplier;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NRIC, PREFIX_DATE, PREFIX_TIMEPERIOD);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NRIC, PREFIX_DATE, PREFIX_TIMEPERIOD);

        String patientNricValue;
        String newApptName;
        String newApptDate;
        String newApptTime;

        newApptName = argMultimap.getPreamble();

        Supplier<ParseException> parseException = () -> new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        newApptTime = argMultimap.getValue(PREFIX_TIMEPERIOD).orElseThrow(parseException);
        newApptDate = argMultimap.getValue(PREFIX_DATE).orElseThrow(parseException);
        patientNricValue = argMultimap.getValue(PREFIX_NRIC).orElseThrow(parseException);

        if (newApptName.isBlank() || newApptName.isEmpty()) {
            throw new ParseException(Appointment.MESSAGE_CONSTRAINTS_BLANK);
        }

        if (!Appointment.isValidAppointmentName(newApptName)) {
            throw new ParseException(Appointment.MESSAGE_CONSTRAINTS);
        }

        if (newApptName.length() > 30) {
            throw new ParseException(Appointment.MESSAGE_TOO_LONG_CONSTRAINT);
        }

        if (!DateUtil.isCorrectDateFormat(newApptDate)) {
            throw new ParseException(Appointment.MESSAGE_CONSTRAINTS_APPT_DATE_WRONG_FORMAT);
        }

        if (!DateUtil.isValidDate(newApptDate)) {
            throw new ParseException(String.format(Appointment.MESSAGE_CONSTRAINTS_APPT_DATE_INVALID_DATE_1S,
                                                   newApptDate));
        }

        try {
            Appointment.checkIsTimePeriodValid(newApptTime);
        } catch (Exception e) {
            throw new ParseException(e.getMessage());
        }

        Nric patientNric = ParserUtil.parseNric(patientNricValue);

        return new AddApptCommand(new NricMatchesPredicate(patientNric), newApptName, newApptDate, newApptTime);
    }
}
