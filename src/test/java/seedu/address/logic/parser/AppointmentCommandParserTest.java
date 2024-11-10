package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AppointmentCommand;
import seedu.address.model.person.Nric;

public class AppointmentCommandParserTest {

    private final AppointmentCommandParser parser = new AppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // valid input with valid nric, date, and time
        assertParseSuccess(parser, "S1231231D app/15-08-2024 14:30",
                new AppointmentCommand(new Nric("S1231231D"), "15-08-2024 14:30"));

        // valid input with non-zero nric
        assertParseSuccess(parser, "T1234567D app/01-01-2025 09:45",
                new AppointmentCommand(new Nric("T1234567D"), "01-01-2025 09:45"));
    }

    @Test
    public void parse_invalidDateFormat_failure() {
        // invalid date format with missing hyphens
        assertParseFailure(parser, "S1231231D app/15082024 14:30",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));

        // invalid date format with non-numeric date components
        assertParseFailure(parser, "S1231231D app/aa-bb-cccc 14:30",
                "Invalid date format. Day, month, and year must be numbers.");

        // invalid date format with incorrect day/month range
        assertParseFailure(parser, "S1231231D app/32-12-2024 14:30",
                "Day and months must be valid.");

        // invalid date format with incorrect month range
        assertParseFailure(parser, "S1231231D app/15-13-2024 14:30",
                "Day and months must be valid.");
    }

    @Test
    public void parse_invalidTimeFormat_failure() {
        // invalid time format with missing colon
        assertParseFailure(parser, "S1231231D app/15-08-2024 1430",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));

        // invalid time format with non-numeric time components
        assertParseFailure(parser, "S1231231D app/15-08-2024 aa:bb",
                "Invalid time format. Hour and minute must be numbers.");

        // invalid hour range
        assertParseFailure(parser, "S1231231D app/15-08-2024 25:30",
                "Hour and minute must be valid.");

        // invalid minute range
        assertParseFailure(parser, "S1231231D app/15-08-2024 14:60",
                "Hour and minute must be valid.");
    }

    @Test
    public void parse_invalidNric_failure() {
        // invalid nric
        assertParseFailure(parser, "1 app/15-08-2024 14:30",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingDateAndTime_failure() {
        // missing date and time
        assertParseFailure(parser, "S1231231D app/",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));

        // empty date and time
        assertParseFailure(parser, "S1231231D app/   ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingAppointmentPrefix_failure() {
        // missing 'a/' prefix for appointment
        assertParseFailure(parser, "S1231231D 15-08-2024 14:30",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingNric_failure() {
        // missing nric
        assertParseFailure(parser, "app/15-08-2024 14:30",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraArguments_failure() {
        // extra arguments after the valid appointment command
        assertParseFailure(parser, "S1231231D app/15-08-2024 14:30 extraArg",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
    }
}
