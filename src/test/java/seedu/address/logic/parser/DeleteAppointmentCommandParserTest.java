package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalAppointments.APPOINTMENT_1;
import static seedu.address.testutil.TypicalAppointments.APPOINTMENT_2;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.model.appointment.Appointment;

/**
 * Unit tests for DeleteAppointmentCommandParser.
 */
public class DeleteAppointmentCommandParserTest {

    private final DeleteAppointmentCommandParser parser = new DeleteAppointmentCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteAppointmentCommand() {
        String validInput = String.valueOf(APPOINTMENT_1.getId());
        Appointment appointment = APPOINTMENT_1;
        assertParseSuccess(parser, validInput, new DeleteAppointmentCommand(appointment));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String invalidInput = "a"; // Invalid argument, expecting an integer.
        assertParseFailure(parser, invalidInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_repeatedArgs_throwsParseException() {
        String repeatedInput = String.valueOf(APPOINTMENT_1.getId()) + " " + String.valueOf(APPOINTMENT_2.getId());
        assertParseFailure(parser, repeatedInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        String emptyInput = ""; // Empty input
        assertParseFailure(parser, emptyInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }
}
