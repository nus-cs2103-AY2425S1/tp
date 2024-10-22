package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AppointmentCommand;

public class AppointmentCommandParserTest {

    private final AppointmentCommandParser parser = new AppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // valid input with valid index, date, and time
        assertParseSuccess(parser, "1 a/15-08-2024 14:30",
                new AppointmentCommand(Index.fromZeroBased(0), "15-08-2024 14:30"));

        // valid input with non-zero index
        assertParseSuccess(parser, "3 a/01-01-2025 09:45",
                new AppointmentCommand(Index.fromZeroBased(2), "01-01-2025 09:45"));
    }

    @Test
    public void parse_invalidDateFormat_failure() {
        // invalid date format with missing hyphens
        assertParseFailure(parser, "1 a/15082024 14:30",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));

        // invalid date format with non-numeric date components
        assertParseFailure(parser, "1 a/aa-bb-cccc 14:30",
                "Invalid date format. Day, month, and year must be numbers.");

        // invalid date format with incorrect day/month range
        assertParseFailure(parser, "1 a/32-12-2024 14:30",
                "Day and months must be valid.");

        // invalid date format with incorrect month range
        assertParseFailure(parser, "1 a/15-13-2024 14:30",
                "Day and months must be valid.");
    }

    @Test
    public void parse_invalidTimeFormat_failure() {
        // invalid time format with missing colon
        assertParseFailure(parser, "1 a/15-08-2024 1430",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));

        // invalid time format with non-numeric time components
        assertParseFailure(parser, "1 a/15-08-2024 aa:bb",
                "Invalid time format. Hour and minute must be numbers.");

        // invalid hour range
        assertParseFailure(parser, "1 a/15-08-2024 25:30",
                "Hour and minute must be valid.");

        // invalid minute range
        assertParseFailure(parser, "1 a/15-08-2024 14:60",
                "Hour and minute must be valid.");
    }

    @Test
    public void parse_invalidIndex_failure() {
        // invalid non-numeric index
        assertParseFailure(parser, "a a/15-08-2024 14:30",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));

        // zero index (not allowed)
        assertParseFailure(parser, "0 a/15-08-2024 14:30",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));

        // negative index
        assertParseFailure(parser, "-1 a/15-08-2024 14:30",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingDateAndTime_failure() {
        // missing date and time
        assertParseFailure(parser, "1 a/",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));

        // empty date and time
        assertParseFailure(parser, "1 a/   ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingAppointmentPrefix_failure() {
        // missing 'a/' prefix for appointment
        assertParseFailure(parser, "1 15-08-2024 14:30",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingIndex_failure() {
        // missing index
        assertParseFailure(parser, "a/15-08-2024 14:30",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraArguments_failure() {
        // extra arguments after the valid appointment command
        assertParseFailure(parser, "1 a/15-08-2024 14:30 extraArg",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
    }
}
