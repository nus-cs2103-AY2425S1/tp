package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STATUS_DESC_NULL;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_DESC_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_DESC_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_COMPLETED_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UpdateAppointmentStatusCommand;
import seedu.address.model.appointment.Status;
import seedu.address.model.person.Nric;

public class UpdateAppointmentStatusParserTest {

    private UpdateAppointmentStatusParser parser = new UpdateAppointmentStatusParser();

    @Test
    public void parse_allFieldsPresent_success() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        UpdateAppointmentStatusCommand expectedCommand = new UpdateAppointmentStatusCommand(
            new Nric(VALID_NRIC_AMY),
            LocalDate.parse(VALID_DATE_APPOINTMENT, dateFormatter),
            LocalTime.parse(VALID_START_TIME_APPOINTMENT, timeFormatter),
            Status.COMPLETED
        );

        assertParseSuccess(parser, NRIC_DESC_AMY + VALID_DATE_DESC_APPOINTMENT
            + VALID_START_TIME_DESC_APPOINTMENT + VALID_STATUS_COMPLETED_DESC, expectedCommand);
    }

    @Test
    public void parse_optionalFieldsMissing_failure() {
        // Missing NRIC
        assertParseFailure(parser, VALID_DATE_DESC_APPOINTMENT + VALID_START_TIME_DESC_APPOINTMENT
            + VALID_STATUS_COMPLETED_DESC,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateAppointmentStatusCommand.MESSAGE_USAGE));

        // Missing date
        assertParseFailure(parser, NRIC_DESC_AMY + VALID_START_TIME_DESC_APPOINTMENT + VALID_STATUS_COMPLETED_DESC,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateAppointmentStatusCommand.MESSAGE_USAGE));

        // Missing start time
        assertParseFailure(parser, NRIC_DESC_AMY + VALID_DATE_DESC_APPOINTMENT + VALID_STATUS_COMPLETED_DESC,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateAppointmentStatusCommand.MESSAGE_USAGE));

        // Missing status
        assertParseFailure(parser, NRIC_DESC_AMY + VALID_DATE_DESC_APPOINTMENT + VALID_START_TIME_DESC_APPOINTMENT,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateAppointmentStatusCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidNric_failure() {
        // Invalid NRIC
        assertParseFailure(parser, INVALID_NRIC_DESC + VALID_DATE_DESC_APPOINTMENT
            + VALID_START_TIME_DESC_APPOINTMENT + VALID_STATUS_COMPLETED_DESC, Nric.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidDateFormat_failure() {
        // Invalid date format
        assertParseFailure(parser, NRIC_DESC_AMY + INVALID_DATE_DESC_APPOINTMENT + VALID_START_TIME_DESC_APPOINTMENT
            + VALID_STATUS_COMPLETED_DESC, UpdateAppointmentStatusCommand.MESSAGE_INVALID_DATE);
    }

    @Test
    public void parse_invalidStartTimeFormat_failure() {
        // Invalid start time format
        assertParseFailure(parser, NRIC_DESC_AMY + VALID_DATE_DESC_APPOINTMENT + INVALID_START_TIME_DESC
            + VALID_STATUS_COMPLETED_DESC,
            UpdateAppointmentStatusCommand.MESSAGE_INVALID_TIME);
    }

    @Test
    public void parse_invalidStatus_failure() {
        // Invalid status
        assertParseFailure(parser, NRIC_DESC_AMY + VALID_DATE_DESC_APPOINTMENT + VALID_START_TIME_DESC_APPOINTMENT
            + INVALID_STATUS_DESC, Status.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidStatus_failureNull() {
        // Invalid status
        assertParseFailure(parser, NRIC_DESC_AMY + VALID_DATE_DESC_APPOINTMENT + VALID_START_TIME_DESC_APPOINTMENT
            + INVALID_STATUS_DESC_NULL, Status.MESSAGE_CONSTRAINTS);
    }

}
