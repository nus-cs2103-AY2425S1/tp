package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_DATE_TIME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_TYPE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MEDICINE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_ID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.SICKNESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_DATE_TIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_TYPE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICINE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PERSON_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SICKNESS_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.APPOINTMENT_ENTITY_STRING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPOINTMENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditAppointmentCommand;
import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.logic.commands.EditCommand;
import seedu.address.testutil.EditAppointmentDescriptorBuilder;

public class EditAppointmentCommandParserTest {
    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private final EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, APPOINTMENT_ENTITY_STRING + " -5" + APPOINTMENT_TYPE_DESC_AMY,
            Index.MESSAGE_CONSTRAINTS);

        // zero index
        assertParseFailure(parser, APPOINTMENT_ENTITY_STRING + " 0" + APPOINTMENT_TYPE_DESC_AMY,
            Index.MESSAGE_CONSTRAINTS);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, APPOINTMENT_ENTITY_STRING + " 1 some random string", EditCommand.MESSAGE_NOT_EDITED);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, APPOINTMENT_ENTITY_STRING + " 1 l/ string", EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_nullAppointmentIndex_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAppointmentCommand.MESSAGE_USAGE);

        // null appointment index
        assertParseFailure(parser, APPOINTMENT_ENTITY_STRING, expectedMessage);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_APPOINTMENT;
        String userInput = APPOINTMENT_ENTITY_STRING + " " + targetIndex.getOneBased()
                + APPOINTMENT_TYPE_DESC_AMY + APPOINTMENT_DATE_TIME_DESC_AMY + SICKNESS_DESC_AMY
                + MEDICINE_DESC_AMY + PERSON_ID_DESC;

        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder()
            .withAppointmentType(VALID_APPOINTMENT_TYPE_AMY)
            .withAppointmentDateTime(VALID_APPOINTMENT_DATE_TIME_AMY)
            .withSickness(VALID_SICKNESS_AMY)
            .withMedicine(VALID_MEDICINE_AMY)
            .withPersonId(VALID_PERSON_ID)
            .build();

        EditCommand expectedCommand = new EditAppointmentCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
