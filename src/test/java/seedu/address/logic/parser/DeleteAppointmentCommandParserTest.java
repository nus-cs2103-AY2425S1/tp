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
 * Unit tests for {@code DeleteAppointmentCommandParser}.
 */
public class DeleteAppointmentCommandParserTest {

    private final DeleteAppointmentCommandParser parser = new DeleteAppointmentCommandParser();

    /**
     * Verifies that parsing a valid appointment ID string returns a valid {@code DeleteAppointmentCommand}.
     */
    @Test
    public void parse_validArgs_returnsDeleteAppointmentCommand() {
        // Prepare valid input using appointment ID from TypicalAppointments
        String validInput = String.valueOf(APPOINTMENT_1.getId());
        Appointment appointment = APPOINTMENT_1;

        // Expect success when valid input is provided
        assertParseSuccess(parser, validInput, new DeleteAppointmentCommand(appointment));
    }

    /**
     * Verifies that parsing an invalid (non-integer) string throws a {@code ParseException}.
     */
    @Test
    public void parse_invalidArgs_throwsParseException() {
        String invalidInput = "a"; // Non-integer argument

        // Expect failure when invalid input is provided
        assertParseFailure(parser, invalidInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    /**
     * Verifies that parsing repeated arguments (more than one appointment ID) throws a {@code ParseException}.
     */
    @Test
    public void parse_repeatedArgs_throwsParseException() {
        // Prepare repeated input
        String repeatedInput = String.valueOf(APPOINTMENT_1.getId()) + " " + String.valueOf(APPOINTMENT_2.getId());

        // Expect failure when multiple arguments are provided
        assertParseFailure(parser, repeatedInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    /**
     * Verifies that parsing an empty string throws a {@code ParseException}.
     */
    @Test
    public void parse_emptyArgs_throwsParseException() {
        String emptyInput = ""; // No input provided

        // Expect failure when no arguments are provided
        assertParseFailure(parser, emptyInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }
}
