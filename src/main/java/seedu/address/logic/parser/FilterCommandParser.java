package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Range;
import seedu.address.model.person.PersonWithCriteriaPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object.
 */
public class FilterCommandParser implements Parser<FilterCommand> {

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

        if (isPrefixPresent(argMultimap, PREFIX_APPOINTMENT) || isPrefixPresent(argMultimap, PREFIX_AGE)) {
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

    /**
     * Parses a range in string into Range object with LocalDate type.
     * The input string must be in the format "dd/MM/yyyy - dd/MM/yyyy".
     *
     * @param trimmedRange the string representing the date range, expected in the format "dd/MM/yyyy - dd/MM/yyyy".
     * @return a {@code Range<LocalDate>} object containing the parsed start and end dates.
     * @throws ParseException if the input string is not in the correct format or contains invalid dates.
     */
    private Range<LocalDate> parseAppointmentRange(String trimmedRange) throws ParseException {
        String[] splittedRange = trimmedRange.split("-");

        if (splittedRange.length != 2) {
            throw new ParseException("Incorrect range format: Please provide your appointment dates range as "
                   + "dd/MM/yyyy - dd/MM/yyyy");
        }
        try {
            LocalDate lower = LocalDate.parse(splittedRange[0].trim(), FORMATTER);
            LocalDate upper = LocalDate.parse(splittedRange[1].trim(), FORMATTER);
            return new Range<LocalDate>(lower, upper);
        } catch (DateTimeParseException e) {
            throw new ParseException("Invalid Date Format: Please input dates in the format 'dd/MM/yyyy'");
        }
    }

    /**
     * Parses a range in string into Range object with type Integer.
     *
     * @param trimmedRange the string representing the age range.
     * @return a {@code Range<Integer>} object containing the parsed start and end dates.
     * @throws ParseException if the input string is not in the correct format or not a valid integer.
     */
    private Range<Integer> parseAgeRange(String trimmedRange) throws ParseException {
        String[] splittedRange = trimmedRange.split("-");

        if (splittedRange.length != 2) {
            throw new ParseException("Incorrect range format: Please provide your age range as "
                   + "ageValue - ageValue");
        }

        try {
            int lower = Integer.parseInt(splittedRange[0].trim());
            int upper = Integer.parseInt(splittedRange[1].trim());
            return new Range<Integer>(lower, upper);
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid Age Format: Please provide a valid age value");
        }
    }

    /**
     * Returns true if at least one of the prefixes contains values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }

}
