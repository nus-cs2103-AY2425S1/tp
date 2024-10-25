package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

import seedu.address.logic.commands.ApptCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Nric;

/**
 * Parses input arguments and creates a new ApptCommand object
 * @throws ParseException
 */
public class ApptCommandParser implements Parser<ApptCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ApptCommand
     * and returns an ApptCommand object for execution.
     * @param args
     * @return ApptCommand
     * @throws ParseException if the user input does not conform the expected format
     */
    public ApptCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATETIME, PREFIX_NRIC);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATETIME, PREFIX_NRIC)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ApptCommand.MESSAGE_USAGE));
        }

        LocalDateTime dateTime;
        try {
            String datetime = argMultimap.getValue(PREFIX_DATETIME).get();
            dateTime = LocalDateTime.parse(datetime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ApptCommand.MESSAGE_USAGE), e);
        } catch (Exception e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ApptCommand.MESSAGE_USAGE), e);
        }

        Nric nric = new Nric(argMultimap.getValue(PREFIX_NRIC).get());

        return new ApptCommand(dateTime, nric);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
