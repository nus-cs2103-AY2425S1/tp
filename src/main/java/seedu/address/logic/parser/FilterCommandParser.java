package seedu.address.logic.parser;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonWithCriteriaPredicate;
import seedu.address.model.person.Range;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.stream.Stream;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

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

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_AGE, PREFIX_APPOINTMENT);

        if(isPrefixPresent(argMultimap, PREFIX_APPOINTMENT) || isPrefixPresent(argMultimap, PREFIX_AGE)) {
            ArrayList<Range<?>> ranges = new ArrayList<>();

            if (isPrefixPresent(argMultimap, PREFIX_AGE)) {
                ranges.add(parseAgeRange(argMultimap.getValue(PREFIX_AGE).get()));
            }
            if (isPrefixPresent(argMultimap, PREFIX_APPOINTMENT)) {
                ranges.add(parseAppointmentRange(argMultimap.getValue(PREFIX_APPOINTMENT).get()));
            }
            PersonWithCriteriaPredicate criteria = new PersonWithCriteriaPredicate(ranges);
            return new FilterCommand(criteria);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

    }
    private Range<LocalDate> parseAppointmentRange(String trimmedRange) throws ParseException {
        String[] splittedRange = trimmedRange.split("-");

        if (splittedRange.length != 2) {
            throw new ParseException("Incorrect range format: Please provide your appointment dates range as " +
                    "dd/MM/yyyy - dd/MM/yyyy");
        }
        try {
            LocalDate lower = LocalDate.parse(splittedRange[0].trim(), FORMATTER);
            LocalDate upper = LocalDate.parse(splittedRange[1].trim(), FORMATTER);
            return new Range<LocalDate>(lower, upper);
        } catch (DateTimeParseException e) {
            throw new ParseException("Invalid Date Format: Please input dates in the format 'dd/MM/yyyy'");
        }
    }

    private Range<Integer> parseAgeRange(String trimmedRange) throws ParseException {
        String[] splittedRange = trimmedRange.split("-");

        if (splittedRange.length != 2) {
            throw new ParseException("Incorrect range format: Please provide your age range as " +
                    "ageValue - ageValue");
        }

        try {
            int lower = Integer.parseInt(splittedRange[0].trim());
            int upper = Integer.parseInt(splittedRange[1].trim());
            return new Range<Integer>(lower, upper);
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid Age Format: Please provide a valid age value");
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }

}
