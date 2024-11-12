package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_APPOINTMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_APPOINTMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NEW_DATE_APPOINTMENT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NEW_END_TIME_APPOINTMENT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NEW_START_TIME_APPOINTMENT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_TIME_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_APPOINTMENT_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditAppointmentCommand;
import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.model.person.Nric;
import seedu.address.testutil.EditAppointmentDescriptorBuilder;

public class EditAppointmentCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            EditAppointmentCommand.MESSAGE_USAGE);

    private EditAppointmentParser parser = new EditAppointmentParser();

    @Test
    public void parse_allFieldsSpecified_success() {
        Nric targetNric = new Nric(VALID_NRIC_AMY);
        String userInput = NRIC_DESC_AMY + DATE_DESC_AMY + START_TIME_DESC_AMY
                + VALID_NEW_DATE_APPOINTMENT_DESC_BOB + VALID_NEW_START_TIME_APPOINTMENT_DESC_BOB
                + VALID_NEW_END_TIME_APPOINTMENT_DESC_BOB;
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder()
                .withDate(VALID_DATE_APPOINTMENT_BOB)
                .withStartTime(VALID_START_TIME_APPOINTMENT_BOB)
                .withEndTime(VALID_END_TIME_APPOINTMENT_BOB)
                .build();
        EditAppointmentCommand expectedCommand = new EditAppointmentCommand(targetNric,
                VALID_START_DATE_TIME_APPOINTMENT, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_dateFieldSpecified_success() {
        Nric targetNric = new Nric(VALID_NRIC_AMY);
        String userInput = NRIC_DESC_AMY + DATE_DESC_AMY + START_TIME_DESC_AMY
                + VALID_NEW_DATE_APPOINTMENT_DESC_BOB;
        System.out.println(userInput);
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder()
                .withDate(VALID_DATE_APPOINTMENT_BOB)
                .build();
        EditAppointmentCommand expectedCommand = new EditAppointmentCommand(targetNric,
                VALID_START_DATE_TIME_APPOINTMENT, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_startTimeFieldSpecified_success() {
        Nric targetNric = new Nric(VALID_NRIC_AMY);
        String userInput = NRIC_DESC_AMY + DATE_DESC_AMY + START_TIME_DESC_AMY
                + VALID_NEW_START_TIME_APPOINTMENT_DESC_BOB;
        System.out.println(userInput);
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder()
                .withStartTime(VALID_START_TIME_APPOINTMENT_BOB)
                .build();
        EditAppointmentCommand expectedCommand = new EditAppointmentCommand(targetNric,
                VALID_START_DATE_TIME_APPOINTMENT, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_endTimeFieldSpecified_success() {
        Nric targetNric = new Nric(VALID_NRIC_AMY);
        String userInput = NRIC_DESC_AMY + DATE_DESC_AMY + START_TIME_DESC_AMY
                + VALID_NEW_END_TIME_APPOINTMENT_DESC_BOB;
        System.out.println(userInput);
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder()
                .withEndTime(VALID_END_TIME_APPOINTMENT_BOB)
                .build();
        EditAppointmentCommand expectedCommand = new EditAppointmentCommand(targetNric,
                VALID_START_DATE_TIME_APPOINTMENT, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingParts_failure() {
        // no nric specified
        assertParseFailure(parser, DATE_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // no field changed
        assertParseFailure(parser, NRIC_DESC_AMY + DATE_DESC_AMY + START_TIME_DESC_AMY,
                EditAppointmentCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_invalidPreamble_failure() {

        String expectedString = "Please do not enter anything before the keywords!\n"
            + "Please remove this from your input: 1 some random string";

        assertParseFailure(parser, "1 some random string" + NRIC_DESC_AMY + DATE_DESC_AMY + START_TIME_DESC_AMY
            + VALID_NEW_DATE_APPOINTMENT_DESC_BOB + VALID_NEW_START_TIME_APPOINTMENT_DESC_BOB
            + VALID_NEW_END_TIME_APPOINTMENT_DESC_BOB, expectedString);

    }

}
