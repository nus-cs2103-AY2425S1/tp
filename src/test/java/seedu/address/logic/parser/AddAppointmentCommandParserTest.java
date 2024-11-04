package seedu.address.logic.parser;

import static seedu.address.commons.util.DateUtil.DATE_TIME_FORMATTER;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_END_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_END_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_START_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_START_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPOINTMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_DATETIME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_DATETIME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATETIME_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATETIME_2;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.DATETIME_INVALID_INDEX;
import static seedu.address.logic.parser.ParserUtil.DATETIME_INVALID_SPECIFY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.model.appointment.Appointment;

public class AddAppointmentCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE);

    private AddAppointmentCommandParser parser = new AddAppointmentCommandParser();
    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, APPOINTMENT_DESC_AMY + DATETIME_START_DESC_AMY + DATETIME_END_DESC_AMY,
                MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no field with prefix for appointment
        assertParseFailure(parser, "1" + " " + PREFIX_APPOINTMENT
                + DATETIME_START_DESC_AMY
                 + DATETIME_END_DESC_AMY, Appointment.MESSAGE_CONSTRAINTS);

        // no start datetime
        assertParseFailure(parser, APPOINTMENT_DESC_AMY + DATETIME_END_DESC_AMY,
                MESSAGE_INVALID_FORMAT);

        // no end datetime
        assertParseFailure(parser, APPOINTMENT_DESC_AMY + DATETIME_START_DESC_AMY,
                MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + APPOINTMENT_DESC_AMY
                + DATETIME_START_DESC_AMY
                + DATETIME_END_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + APPOINTMENT_DESC_AMY
                + DATETIME_START_DESC_AMY
                + DATETIME_END_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 s/string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_APPOINTMENT_DESC + DATETIME_START_DESC_AMY
                + DATETIME_END_DESC_AMY, Appointment.MESSAGE_CONSTRAINTS); // invalid appointment description
        assertParseFailure(parser, "1" + APPOINTMENT_DESC_AMY + INVALID_START_DATETIME_DESC
                + DATETIME_END_DESC_AMY, DATETIME_INVALID_INDEX + DATETIME_INVALID_SPECIFY
                + INVALID_START_DATETIME); // invalid start datetime
        assertParseFailure(parser, "1" + APPOINTMENT_DESC_AMY + DATETIME_START_DESC_AMY
                + INVALID_END_DATETIME_DESC, DATETIME_INVALID_INDEX + DATETIME_INVALID_SPECIFY
                + INVALID_END_DATETIME); // invalid end datetime

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_APPOINTMENT_DESC
                        + INVALID_START_DATETIME_DESC
                        + INVALID_END_DATETIME_DESC,
                Appointment.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + APPOINTMENT_DESC_BOB
                + DATETIME_START_DESC_BOB + DATETIME_END_DESC_BOB;
        Appointment appointment = new Appointment(VALID_APPOINTMENT_BOB,
                LocalDateTime.parse(VALID_START_DATETIME_2, DATE_TIME_FORMATTER),
                LocalDateTime.parse(VALID_END_DATETIME_2, DATE_TIME_FORMATTER));
        AddAppointmentCommand expectedCommand = new AddAppointmentCommand(appointment, targetIndex);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validDate_failure() {
        assertParseFailure(parser, "1" + APPOINTMENT_DESC_AMY + " s/32-12-2023-12-00"
                + DATETIME_END_DESC_AMY, DATETIME_INVALID_INDEX + DATETIME_INVALID_SPECIFY
                + "32-12-2023-12-00"); // non-existent day
        assertParseFailure(parser, "1" + APPOINTMENT_DESC_AMY + " s/10-13-2023-12-00"
                + DATETIME_END_DESC_AMY, DATETIME_INVALID_INDEX + DATETIME_INVALID_SPECIFY
                + "10-13-2023-12-00"); // non-existent month
        assertParseFailure(parser, "1" + APPOINTMENT_DESC_AMY + " s/10-12-2023-26-00"
                + DATETIME_END_DESC_AMY, DATETIME_INVALID_INDEX + DATETIME_INVALID_SPECIFY
                + "10-12-2023-26-00"); // non-existent hour
        assertParseFailure(parser, "1" + APPOINTMENT_DESC_AMY + " s/10-12-2023-12-70"
                + DATETIME_END_DESC_AMY, DATETIME_INVALID_INDEX + DATETIME_INVALID_SPECIFY
                + "10-12-2023-12-70"); // non-existent minute
        assertParseFailure(parser, "1" + APPOINTMENT_DESC_AMY + " s/05-11-2024-12-00"
                + " e/04-11-2024-13-00", "The end time must be after the start time."); // start time after end

    }
}
