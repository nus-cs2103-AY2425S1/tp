package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.InvalidIdException;
import seedu.address.logic.commands.CheckAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CheckAppointmentCommand object.
 */
public class CheckAppointmentCommandParser implements Parser<CheckAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CheckAppointmentCommand
     * and returns a CheckAppointmentCommand object for execution.
     *
     * @param args the input arguments string.
     * @return a CheckAppointmentCommand object.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public CheckAppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Tokenize the arguments and look for the /d (date) and /id (doctor ID) prefixes
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ID, PREFIX_DAY_DATE);

        // Check if both /d and /id prefixes are present, and there is no unexpected preamble
        if (!arePrefixesPresent(argumentMultimap, PREFIX_ID, PREFIX_DAY_DATE)
                || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CheckAppointmentCommand.MESSAGE_USAGE));
        }

        // Parse the doctor ID
        int doctorId;
        try {
            doctorId = ParserUtil.parsePersonId(argumentMultimap.getAllValues(PREFIX_ID).get(0));
        } catch (InvalidIdException e) {
            throw new ParseException(MESSAGE_INVALID_ID, e);
        }

        LocalDate date = null;
        Optional<String> dateString = argumentMultimap.getValue(PREFIX_DAY_DATE);
        if (dateString.isPresent()) {
            try {
                date = ParserUtil.parseDayDate(dateString.get().trim());
            } catch (ParseException e) {
                throw new ParseException("Invalid date format. Please use yyyy-MM-dd.");
            }
        }

        // Return the constructed CheckAppointmentCommand with doctorId and the parsed or null date
        return new CheckAppointmentCommand(doctorId, date);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
