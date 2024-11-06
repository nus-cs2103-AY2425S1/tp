package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import seedu.address.logic.commands.UpdateAppointmentStatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Status;
import seedu.address.model.person.Nric;

/**
 * Parses input arguments and creates a new UpdateAppointmentStatusCommand object
 */
public class UpdateAppointmentStatusParser implements Parser<UpdateAppointmentStatusCommand> {

    @Override
    public UpdateAppointmentStatusCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_NRIC,
            PREFIX_DATE, PREFIX_START_TIME, PREFIX_STATUS);

        if (!argumentMultimap.getPreamble().equals("")) {
            throw new ParseException("Please do not enter anything before the keywords!\n"
            + "Please remove this from your input: " + argumentMultimap.getPreamble());
        }

        if (!arePrefixesPresent(argumentMultimap, PREFIX_NRIC, PREFIX_DATE, PREFIX_START_TIME, PREFIX_STATUS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UpdateAppointmentStatusCommand.MESSAGE_USAGE));
        }

        Nric nric = ParserUtil.parseNric(argumentMultimap.getValue(PREFIX_NRIC).get());
        LocalDate date = ParserUtil.parseDate(argumentMultimap.getValue(PREFIX_DATE).get());
        LocalTime startTime = ParserUtil.parseTime(argumentMultimap.getValue(PREFIX_START_TIME).get());
        Status status = ParserUtil.parseStatus(argumentMultimap.getValue(PREFIX_STATUS).get());

        return new UpdateAppointmentStatusCommand(nric, date, startTime, status);

    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
