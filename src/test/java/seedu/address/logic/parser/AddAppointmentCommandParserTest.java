package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.DOCTOR_NAME_DESC_JANE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DOCTOR_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PATIENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PATIENT_NAME_DESC_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOCTOR_NAME_JANE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PATIENT_NAME_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalAppointments.APPOINTMENT_2;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Time;
import seedu.address.model.person.Name;
import seedu.address.model.shared.Date;
import seedu.address.testutil.AppointmentBuilder;

/**
 * Unit tests for {@code AddAppointmentCommandParser}.
 */
public class AddAppointmentCommandParserTest {

    private final AddAppointmentCommandParser parser = new AddAppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Appointment expectedAppointment = new AppointmentBuilder().build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PATIENT_NAME_DESC_JOHN + DOCTOR_NAME_DESC_JANE
                + DATE_DESC + TIME_DESC, new AddAppointmentCommand(expectedAppointment.getPatient().getName(),
                expectedAppointment.getDoctor().getName(), expectedAppointment.getDate(),
                expectedAppointment.getTime()));
    }

    @Test
    public void parse_repeatedArguments_failure() {
        String validExpectedAppointmentString = PATIENT_NAME_DESC_JOHN + DOCTOR_NAME_DESC_JANE + DATE_DESC + TIME_DESC;

        // multiple patient names
        assertParseFailure(parser, APPOINTMENT_2.getPatientName() + validExpectedAppointmentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PATIENT_NAME));

        // multiple doctor names
        assertParseFailure(parser, APPOINTMENT_2.getDoctorName() + validExpectedAppointmentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DOCTOR_NAME));

        // multiple dates
        assertParseFailure(parser, APPOINTMENT_2.getDate().value + validExpectedAppointmentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // multiple times
        assertParseFailure(parser, APPOINTMENT_2.getTime().value + validExpectedAppointmentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TIME));

        // multiple fields repeated
        assertParseFailure(parser, validExpectedAppointmentString + APPOINTMENT_2.getPatientName()
                + APPOINTMENT_2.getDoctorName() + APPOINTMENT_2.getDate().value + APPOINTMENT_2.getTime().value
                + validExpectedAppointmentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PATIENT_NAME, PREFIX_DOCTOR_NAME, PREFIX_DATE,
                        PREFIX_TIME));

        // invalid value followed by valid value

        // invalid patient name
        assertParseFailure(parser, INVALID_PATIENT_NAME_DESC + validExpectedAppointmentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PATIENT_NAME));

        // invalid doctor name
        assertParseFailure(parser, INVALID_DOCTOR_NAME_DESC + validExpectedAppointmentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DOCTOR_NAME));

        // invalid date
        assertParseFailure(parser, INVALID_DATE_DESC + validExpectedAppointmentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // invalid time
        assertParseFailure(parser, INVALID_TIME_DESC + validExpectedAppointmentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TIME));

        // valid value followed by invalid value

        // invalid patient name
        assertParseFailure(parser, validExpectedAppointmentString + INVALID_PATIENT_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PATIENT_NAME));

        // invalid doctor name
        assertParseFailure(parser, validExpectedAppointmentString + INVALID_DOCTOR_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DOCTOR_NAME));

        // invalid date
        assertParseFailure(parser, validExpectedAppointmentString + INVALID_DATE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // invalid time
        assertParseFailure(parser, validExpectedAppointmentString + INVALID_TIME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TIME));
    }

    @Test
    public void parse_noArguments_throwsParseException() {
        String emptyInput = ""; // No input provided

        // Expect failure when no arguments are provided
        assertParseFailure(parser, emptyInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE);

        // missing patient name prefix
        assertParseFailure(parser, VALID_PATIENT_NAME_JOHN + DOCTOR_NAME_DESC_JANE + DATE_DESC + TIME_DESC,
                expectedMessage);

        // missing doctor name prefix
        assertParseFailure(parser, PATIENT_NAME_DESC_JOHN + VALID_DOCTOR_NAME_JANE + DATE_DESC + TIME_DESC,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, PATIENT_NAME_DESC_JOHN + DOCTOR_NAME_DESC_JANE + VALID_DATE + TIME_DESC,
                expectedMessage);

        // missing time prefix
        assertParseFailure(parser, PATIENT_NAME_DESC_JOHN + DOCTOR_NAME_DESC_JANE + DATE_DESC + VALID_TIME,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_PATIENT_NAME_JOHN + VALID_DOCTOR_NAME_JANE + VALID_DATE + VALID_TIME,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid patient name
        assertParseFailure(parser, INVALID_PATIENT_NAME_DESC + DOCTOR_NAME_DESC_JANE + DATE_DESC + TIME_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // invalid doctor name
        assertParseFailure(parser, PATIENT_NAME_DESC_JOHN + INVALID_DOCTOR_NAME_DESC + DATE_DESC + TIME_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, PATIENT_NAME_DESC_JOHN + DOCTOR_NAME_DESC_JANE + INVALID_DATE_DESC
                + TIME_DESC, Date.MESSAGE_CONSTRAINTS);

        // invalid time
        assertParseFailure(parser, PATIENT_NAME_DESC_JOHN + DOCTOR_NAME_DESC_JANE + DATE_DESC
                + INVALID_TIME_DESC, Time.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_PATIENT_NAME_DESC + DOCTOR_NAME_DESC_JANE + DATE_DESC
                + INVALID_TIME_DESC, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PATIENT_NAME_DESC_JOHN + DOCTOR_NAME_DESC_JANE
                + DATE_DESC + TIME_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE));
    }
}
