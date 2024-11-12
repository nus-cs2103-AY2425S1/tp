package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ArgumentTokenizer.checkPrefixPresentAndValidPrefix;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHSERVICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;

import java.time.LocalDate;
import java.util.stream.Stream;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointmentdatefilter.AppointmentDateFilter;
import seedu.address.model.healthservice.HealthService;


/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {

        checkPrefixPresentAndValidPrefix(args, FilterCommand.MESSAGE_USAGE,
                PREFIX_STARTDATE, PREFIX_ENDDATE, PREFIX_HEALTHSERVICE);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STARTDATE, PREFIX_ENDDATE, PREFIX_HEALTHSERVICE);

        if (!arePrefixesPresent(argMultimap, PREFIX_ENDDATE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ENDDATE);

        String endDateString = argMultimap.getValue(PREFIX_ENDDATE).get();

        LocalDate endDate;
        LocalDate startDate = null;
        HealthService service = null;

        if (arePrefixesPresent(argMultimap, PREFIX_STARTDATE)) {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STARTDATE);
            endDate = ParserUtil.parseDate(endDateString);
            startDate = ParserUtil.parseStartDateAndCheck(argMultimap.getValue(PREFIX_STARTDATE).get(), endDate);
        } else {
            // if start date not present, end date should not be before today's date
            endDate = ParserUtil.parseEndDateAndCheck(endDateString);
        }

        if (arePrefixesPresent(argMultimap, PREFIX_HEALTHSERVICE)) {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_HEALTHSERVICE);
            service = ParserUtil.parseHealthService(argMultimap.getValue(PREFIX_HEALTHSERVICE).get());
        }
        AppointmentDateFilter dateFilter = new AppointmentDateFilter(startDate, endDate, service);

        return new FilterCommand(dateFilter);
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
