package seedu.address.logic.parser;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Age;
import seedu.address.model.person.PersonWithCriteriaPredicate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;

public class FilterCommandParser implements Parser<FilterCommand>{

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public FilterCommand parse(String args) throws ParseException {
        String trimmedRange = args.trim();
        if (trimmedRange.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AGE, PREFIX_APPOINTMENT);

//        Age age = ParserUtil.parseAge(argMultimap.getValue(PREFIX_AGE).get());

        if(isPrefixPresent(argMultimap, PREFIX_APPOINTMENT)) {
            return new FilterCommand<LocalDate>(parseAppointmentRange(argMultimap.getValue(PREFIX_APPOINTMENT).get()));
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

    }
    private PersonWithCriteriaPredicate<LocalDate> parseAppointmentRange(String trimmedRange) throws ParseException {
        String[] splittedRange = trimmedRange.split("-");

        if (splittedRange.length != 2) {
            throw new ParseException("Incorrect range format: Please provide your age/appointment dates range as " +
                    "lower - upper");
        }
        try {
            LocalDate lower = LocalDate.parse(splittedRange[0].trim(), FORMATTER);
            LocalDate upper = LocalDate.parse(splittedRange[1].trim(), FORMATTER);
            return new PersonWithCriteriaPredicate<LocalDate>(lower, upper);
        } catch (DateTimeParseException e) {
            throw new ParseException("Invalid Date Format: Please input dates in the format 'dd/MM/yyyy'");
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }

}
