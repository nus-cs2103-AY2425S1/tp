package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Appointment;

public class DeleteAppointmentCommandParserTest {

    private DeleteAppointmentCommandParser parser = new DeleteAppointmentCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteAppointmentCommand() throws ParseException {
        // Valid input case: index + date time
        String userInput = "1 01-01-2024 12:30";
        Appointment expectedAppointment = new Appointment("01-01-2024 12:30");
        Index expectedIndex = Index.fromOneBased(1);

        DeleteAppointmentCommand expectedCommand = new DeleteAppointmentCommand(expectedIndex, expectedAppointment);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidDateFormat_throwsParseException() {
        String invalidInput = "1 2024/01/01 12:30";
        assertParseFailure(parser, invalidInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingTime_throwsParseException() {
        // Missing time: Should fail
        String invalidInput = "1 01-01-2024";
        assertParseFailure(parser, invalidInput, String.format(DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // Invalid index (non-numeric): Should fail
        String invalidInput = "a 01-01-2024 12:30";
        assertParseFailure(parser, invalidInput, String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingArguments_throwsParseException() {
        // Missing both index and appointment details
        String invalidInput = "";
        assertParseFailure(parser, invalidInput, String.format(DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraArguments_throwsParseException() {
        // Extra arguments: Should fail
        String invalidInput = "1 01-01-2024 12:30 extra_argument";
        assertParseFailure(parser, invalidInput, String.format(DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsDifferentDateFormats_success() throws ParseException {
        // Valid input with different date format
        String userInput = "1 01-01-2024 12:30";
        Appointment expectedAppointment = new Appointment("01-01-2024 12:30");
        Index expectedIndex = Index.fromOneBased(1);

        DeleteAppointmentCommand expectedCommand = new DeleteAppointmentCommand(expectedIndex, expectedAppointment);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
