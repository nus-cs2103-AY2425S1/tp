package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHSERVICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.appointmentdatefilter.AppointmentDateFilter;
import seedu.address.model.healthservice.HealthService;

public class FilterCommandParserTest {
    private final FilterCommandParser parser = new FilterCommandParser();
    private final String validStartdate = " " + PREFIX_STARTDATE + "2000-10-10";
    private final String validEnddate = " " + PREFIX_ENDDATE + "2025-10-10";
    private final String validHealthService = " " + PREFIX_HEALTHSERVICE + "BLOOD TYPE";
    private final String validFilterString = validStartdate + validEnddate + validHealthService;

    private final String invalidEndDateWithNoStartDate = " " + PREFIX_ENDDATE + "2010-10-10";
    private final String invalidStartDate = " " + PREFIX_STARTDATE + "2000-10/10";
    private final String invalidEndDate = " " + PREFIX_ENDDATE + "2025-10/10";
    private final String invalidHealthSerivce = " " + PREFIX_HEALTHSERVICE + "burger";
    private final String endDateBeforeStartDate = " " + PREFIX_ENDDATE + "1990-10-10";

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        LocalDate startDate = null;
        HealthService service = null;
        AppointmentDateFilter filter = new AppointmentDateFilter(startDate,
                LocalDate.parse("2025-10-10"), service);
        FilterCommand expectedFilterCommand = new FilterCommand(filter);
        assertParseSuccess(parser, " ed|2025-10-10", expectedFilterCommand);
    }

    @Test
    public void parse_repeatedValues_failure() {

        // multiple start dates
        assertParseFailure(parser, validStartdate + validFilterString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STARTDATE));

        // multiple end dates
        assertParseFailure(parser, validEnddate + validFilterString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ENDDATE));

        // multiple health services
        assertParseFailure(parser, validHealthService + validFilterString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_HEALTHSERVICE));

        //invalid value followed by valid value

        //invalid start date
        assertParseFailure(parser, invalidStartDate + validFilterString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STARTDATE));

        //invalid end date
        assertParseFailure(parser, invalidEndDate + validFilterString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ENDDATE));

        //invalid health service
        assertParseFailure(parser, invalidHealthSerivce + validFilterString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_HEALTHSERVICE));

        // valid value followed by invalid value

        //invalid start date
        assertParseFailure(parser, validFilterString + invalidStartDate,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STARTDATE));

        //invalid end date
        assertParseFailure(parser, validFilterString + invalidEndDate,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ENDDATE));

        //invalid health service
        assertParseFailure(parser, validFilterString + invalidHealthSerivce,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_HEALTHSERVICE));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE);

        // missing end date prefix with start date present
        assertParseFailure(parser, " sd/2000-10-10", expectedMessage);

        // missing end date prefix with health service present
        assertParseFailure(parser, " h/Blood Test", expectedMessage);

        // missing end date prefix with start date and health service present
        assertParseFailure(parser, " sd/2000-10-10 h/Blood Test", expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {
        //invalid startDate
        assertParseFailure(parser, invalidStartDate + validEnddate + validHealthService,
                AppointmentDateFilter.ONE_DATE_MESSAGE_CONSTRAINTS);

        //invalid endDate
        assertParseFailure(parser, invalidEndDate + validStartdate + validHealthService,
                AppointmentDateFilter.ONE_DATE_MESSAGE_CONSTRAINTS);

        //invalid healthService
        assertParseFailure(parser, validEnddate + validStartdate + validHealthService,
                HealthService.MESSAGE_CONSTRAINTS);

        // end date before start date
        assertParseFailure(parser, endDateBeforeStartDate + validStartdate + validHealthService,
                AppointmentDateFilter.TWO_DATE_MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, invalidEndDate + invalidHealthSerivce + validStartdate,
                AppointmentDateFilter.ONE_DATE_MESSAGE_CONSTRAINTS);

        // end date earlier than today's date when no start date is specified
        assertParseFailure(parser, invalidEndDateWithNoStartDate + validHealthService,
                AppointmentDateFilter.END_DATE_MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + validEnddate,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }
}
