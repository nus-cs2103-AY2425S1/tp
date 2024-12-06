package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.InvalidIdException;
import seedu.address.logic.commands.MarkAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkAppointmentCommand object
 */
public class MarkAppointmentCommandParser implements Parser<MarkAppointmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MarkAppointmentCommand
     * and returns a MarkAppointmentCommand object for execution.
     *
     * @param args the input arguments string.
     * @return a MarkAppointmentCommand object.
     * @throws ParseException if the user input does not conform to the expected format.
     */

    @Override
    public MarkAppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_ID);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_DATE, PREFIX_ID)
                || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkAppointmentCommand.MESSAGE_USAGE));
        }
        int patientId;
        int doctorId;
        try {
            patientId = ParserUtil.parsePersonId(argumentMultimap.getAllValues(PREFIX_ID).get(0));
            doctorId = ParserUtil.parsePersonId(argumentMultimap.getAllValues(PREFIX_ID).get(1));
        } catch (InvalidIdException e) {
            throw new ParseException(MESSAGE_INVALID_ID, e);
        }
        String appointmentTime = String.valueOf(requireNonNull(argumentMultimap.getValue(PREFIX_DATE)));
        LocalDateTime time = ParserUtil.parseDateWithNoLimit(appointmentTime);
        return new MarkAppointmentCommand(time, patientId, doctorId);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
