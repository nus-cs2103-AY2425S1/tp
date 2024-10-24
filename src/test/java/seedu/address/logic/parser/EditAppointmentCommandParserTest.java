package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.FIND_DATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.FIND_NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.FIND_START_TIME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_APPOINTMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_APPOINTMENT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_APPOINTMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_APPOINTMENT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_TIME_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_APPOINTMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_APPOINTMENT_DESC_AMY;
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
        String userInput = FIND_NRIC_DESC_AMY + FIND_DATE_DESC_AMY + FIND_START_TIME_DESC_AMY + NRIC_DESC_AMY
                + VALID_DATE_APPOINTMENT_DESC_AMY + VALID_START_TIME_APPOINTMENT_DESC_AMY
                + VALID_END_TIME_APPOINTMENT_DESC_AMY;
        System.out.println(userInput);
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder()
                .withNric(VALID_NRIC_AMY)
                .withDate(VALID_DATE_APPOINTMENT_AMY)
                .withStartTime(VALID_START_TIME_APPOINTMENT_AMY)
                .withEndTime(VALID_END_TIME_APPOINTMENT_AMY)
                .build();
        EditAppointmentCommand expectedCommand = new EditAppointmentCommand(targetNric,
                VALID_START_DATE_TIME_APPOINTMENT, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingParts_failure() {
        // no nric specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

}
