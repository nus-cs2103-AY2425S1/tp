package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPOINTMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEDICATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMARK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MEDICATION_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.MEDICATION_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICATION_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_ONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalNames.NAME_FIRST_PERSON;
import static seedu.address.testutil.TypicalNames.NAME_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.commands.NoteCommand.NoteDescriptor;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.note.Note;
import seedu.address.model.person.Name;
import seedu.address.testutil.NoteDescriptorBuilder;

public class NoteCommandParserTest {
    private static final String APPOINTMENT_EMPTY = " " + PREFIX_APPOINTMENT;
    private static final String MEDICATION_EMPTY = " " + PREFIX_MEDICATION;
    private static final String REMARK_EMPTY = " " + PREFIX_REMARK;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE);

    private NoteCommandParser parser = new NoteCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no name specified
        assertParseFailure(parser, "ap/01/01/2025 1200", MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "Bob", NoteCommand.MESSAGE_NOT_EDITED);

        // no name and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // Invalid name
        assertParseFailure(parser, "INVALID_NAME" + APPOINTMENT_DESC_TWO, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "INVALID_NAME p/99999999", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "Amy i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid appointment
        assertParseFailure(parser, "Test" + INVALID_APPOINTMENT_DESC, Appointment.MESSAGE_CONSTRAINTS);
        // invalid remark
        assertParseFailure(parser, "Test" + INVALID_REMARK_DESC, Note.MESSAGE_CONSTRAINTS);
        //invalid medications
        assertParseFailure(parser, "Test" + INVALID_MEDICATION_DESC, Note.MESSAGE_CONSTRAINTS);

        // invalid medications followed by valid appointment
        assertParseFailure(parser, "Test" + INVALID_MEDICATION_DESC
                                   + APPOINTMENT_DESC_TWO, Note.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_APPOINTMENT} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "Test" + APPOINTMENT_DESC_ONE + APPOINTMENT_DESC_TWO + APPOINTMENT_EMPTY,
                Appointment.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "Test" + APPOINTMENT_DESC_ONE + APPOINTMENT_EMPTY + APPOINTMENT_DESC_TWO,
                Appointment.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "Test" + APPOINTMENT_EMPTY + APPOINTMENT_DESC_ONE + APPOINTMENT_DESC_TWO,
                Appointment.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_MEDICATION} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "Test" + MEDICATION_DESC_ONE + MEDICATION_DESC_TWO + MEDICATION_EMPTY,
                Note.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "Test" + MEDICATION_DESC_ONE + MEDICATION_EMPTY + MEDICATION_DESC_TWO,
                Note.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "Test" + MEDICATION_EMPTY + MEDICATION_DESC_ONE + MEDICATION_DESC_TWO,
                Note.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_REMARK} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "Test" + REMARK_DESC_ONE + REMARK_DESC_TWO + REMARK_EMPTY,
                Note.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "Test" + REMARK_DESC_ONE + REMARK_EMPTY + REMARK_DESC_TWO,
                Note.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "Test" + REMARK_EMPTY + REMARK_DESC_ONE + REMARK_DESC_TWO,
                Note.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "Test" + INVALID_MEDICATION_DESC + INVALID_REMARK_DESC,
                Note.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Name targetName = NAME_FIRST_PERSON;
        String userInput = targetName + APPOINTMENT_DESC_ONE + REMARK_DESC_ONE + MEDICATION_DESC_ONE;

        NoteDescriptor descriptor = new NoteDescriptorBuilder().withAppointments(VALID_APPOINTMENT_ONE)
                .withRemarks(VALID_REMARK_ONE).withMedications(VALID_MEDICATION_ONE).build();
        NoteCommand expectedCommand = new NoteCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Name targetName = NAME_FIRST_PERSON;
        String userInput = targetName + APPOINTMENT_DESC_ONE + REMARK_DESC_ONE;

        NoteDescriptor descriptor = new NoteDescriptorBuilder().withAppointments(VALID_APPOINTMENT_ONE)
                .withRemarks(VALID_REMARK_ONE).build();
        NoteCommand expectedCommand = new NoteCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Name targetName = NAME_THIRD_PERSON;

        // appointments
        String userInput = targetName + APPOINTMENT_DESC_ONE;
        NoteDescriptor descriptor = new NoteDescriptorBuilder().withAppointments(VALID_APPOINTMENT_ONE).build();
        NoteCommand expectedCommand = new NoteCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // remark
        userInput = targetName + REMARK_DESC_ONE;
        descriptor = new NoteDescriptorBuilder().withRemarks(VALID_REMARK_ONE).build();
        expectedCommand = new NoteCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // medications
        userInput = targetName + MEDICATION_DESC_ONE;
        descriptor = new NoteDescriptorBuilder().withMedications(VALID_MEDICATION_ONE).build();
        expectedCommand = new NoteCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetAppointments_success() {
        Name targetName = NAME_THIRD_PERSON;
        String userInput = targetName + APPOINTMENT_EMPTY;

        NoteDescriptor descriptor = new NoteDescriptorBuilder().withAppointments().build();
        NoteCommand expectedCommand = new NoteCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetRemarks_success() {
        Name targetName = NAME_THIRD_PERSON;
        String userInput = targetName + REMARK_EMPTY;

        NoteDescriptor descriptor = new NoteDescriptorBuilder().withRemarks().build();
        NoteCommand expectedCommand = new NoteCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetMedications_success() {
        Name targetName = NAME_THIRD_PERSON;
        String userInput = targetName + MEDICATION_EMPTY;

        NoteDescriptor descriptor = new NoteDescriptorBuilder().withMedications().build();
        NoteCommand expectedCommand = new NoteCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
