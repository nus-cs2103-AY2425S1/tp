package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPOINTMENT_DATE_NONEXISTANT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEL_APPT_DATE_FORMAT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEL_APPT_DATE_NONEXISTANT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEL_APPT_TIMEPERIOD_FORMAT_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEL_APPT_TIMEPERIOD_FORMAT_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEL_APPT_TIMEPERIOD_FORMAT_DESC_3;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEL_APPT_TIMEPERIOD_FORMAT_DESC_4;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEL_APPT_TIMEPERIOD_ORDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_DATE_DENTAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_TIMEPERIOD_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_TIMEPERIOD_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_TIMEPERIOD_DENTAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.DeleteApptCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMEPERIOD;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteApptCommand;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.AppointmentExistsPredicate;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricMatchesPredicate;

public class DeleteApptCommandParserTest {
    private final DeleteApptCommandParser parser = new DeleteApptCommandParser();

    @Test
    public void parse_allFieldsValid_success() {
        DeleteApptCommand expectedCommandAmy = new DeleteApptCommand(
                new NricMatchesPredicate(new Nric(VALID_NRIC_AMY)),
                new AppointmentExistsPredicate(VALID_APPOINTMENT_DATE_AMY, VALID_APPOINTMENT_TIMEPERIOD_AMY));
        assertParseSuccess(parser, APPOINTMENT_DESC_AMY + NRIC_DESC_AMY, expectedCommandAmy);

        DeleteApptCommand expectedCommandBob = new DeleteApptCommand(
                new NricMatchesPredicate(new Nric(VALID_NRIC_BOB)),
                new AppointmentExistsPredicate(VALID_APPOINTMENT_DATE_BOB, VALID_APPOINTMENT_TIMEPERIOD_BOB));
        assertParseSuccess(parser, APPOINTMENT_DESC_BOB + NRIC_DESC_BOB, expectedCommandBob);
    }

    @Test
    public void parse_allFieldsInvalid_failure() {
        assertParseFailure(parser, APPOINTMENT_DESC_BOB + INVALID_NRIC_DESC, Nric.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, INVALID_DEL_APPT_DATE_FORMAT_DESC + NRIC_DESC_AMY,
                           Appointment.MESSAGE_CONSTRAINTS_APPT_DATE_WRONG_FORMAT);;
        assertParseFailure(parser, INVALID_DEL_APPT_DATE_NONEXISTANT_DESC + NRIC_DESC_AMY,
                           String.format(Appointment.MESSAGE_CONSTRAINTS_APPT_DATE_INVALID_DATE_1S,
                                         INVALID_APPOINTMENT_DATE_NONEXISTANT));;
        assertParseFailure(parser, INVALID_DEL_APPT_TIMEPERIOD_ORDER_DESC + NRIC_DESC_AMY,
                           Appointment.MESSAGE_CONSTRAINTS_APPT_TIME_PERIOD_INVALID_ORDER);;
        assertParseFailure(parser, INVALID_DEL_APPT_TIMEPERIOD_FORMAT_DESC_1 + NRIC_DESC_AMY,
                           Appointment.MESSAGE_CONSTRAINTS_APPT_TIME_PERIOD_WRONG_FORMAT);;
        assertParseFailure(parser, INVALID_DEL_APPT_TIMEPERIOD_FORMAT_DESC_2 + NRIC_DESC_AMY,
                           Appointment.MESSAGE_CONSTRAINTS_APPT_TIME_PERIOD_WRONG_FORMAT);;
        assertParseFailure(parser, INVALID_DEL_APPT_TIMEPERIOD_FORMAT_DESC_3 + NRIC_DESC_AMY,
                           Appointment.MESSAGE_CONSTRAINTS_APPT_TIME_PERIOD_WRONG_FORMAT);;
        assertParseFailure(parser, INVALID_DEL_APPT_TIMEPERIOD_FORMAT_DESC_4 + NRIC_DESC_AMY,
                           Appointment.MESSAGE_CONSTRAINTS_APPT_TIME_PERIOD_WRONG_FORMAT);;
    }

    @Test
    public void parse_compulsoryFieldsMissing_failure() {
        assertParseFailure(parser, PREFIX_TIMEPERIOD + VALID_APPOINTMENT_TIMEPERIOD_DENTAL + " " + VALID_NRIC_AMY,
                           String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, PREFIX_DATE + VALID_APPOINTMENT_DATE_DENTAL + " " + VALID_NRIC_AMY,
                           String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, PREFIX_DATE + VALID_APPOINTMENT_DATE_DENTAL + " "
                                   + PREFIX_TIMEPERIOD + VALID_APPOINTMENT_TIMEPERIOD_DENTAL,
                           String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, VALID_NRIC_AMY, String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, PREFIX_DATE + VALID_APPOINTMENT_DATE_DENTAL,
                           String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, PREFIX_TIMEPERIOD + VALID_APPOINTMENT_TIMEPERIOD_DENTAL,
                           String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateValues_throwsParserException() {
        assertParseFailure(parser, NRIC_DESC_AMY + " " + PREFIX_DATE
                                   + VALID_APPOINTMENT_DATE_DENTAL + " " + PREFIX_TIMEPERIOD
                                   + VALID_APPOINTMENT_TIMEPERIOD_DENTAL + NRIC_DESC_AMY,
                           MESSAGE_DUPLICATE_FIELDS + PREFIX_NRIC);
        assertParseFailure(parser, " " + PREFIX_DATE
                                   + VALID_APPOINTMENT_DATE_DENTAL + " " + PREFIX_TIMEPERIOD
                                   + VALID_APPOINTMENT_TIMEPERIOD_DENTAL + " " + PREFIX_DATE
                                   + VALID_APPOINTMENT_DATE_DENTAL + " "
                                   + NRIC_DESC_AMY, MESSAGE_DUPLICATE_FIELDS + PREFIX_DATE);
        assertParseFailure(parser, " " + PREFIX_DATE
                                   + VALID_APPOINTMENT_DATE_DENTAL + " " + PREFIX_TIMEPERIOD
                                   + VALID_APPOINTMENT_TIMEPERIOD_DENTAL + " " + PREFIX_TIMEPERIOD
                                   + VALID_APPOINTMENT_TIMEPERIOD_DENTAL + " " + NRIC_DESC_AMY,
                           MESSAGE_DUPLICATE_FIELDS + PREFIX_TIMEPERIOD);
        assertParseFailure(parser, APPOINTMENT_DESC_BOB + " " + " " + PREFIX_DATE
                                   + VALID_APPOINTMENT_DATE_DENTAL + " " + PREFIX_TIMEPERIOD
                                   + VALID_APPOINTMENT_TIMEPERIOD_DENTAL + NRIC_DESC_AMY,
                           MESSAGE_DUPLICATE_FIELDS + PREFIX_DATE + " " + PREFIX_TIMEPERIOD);
    }
}
