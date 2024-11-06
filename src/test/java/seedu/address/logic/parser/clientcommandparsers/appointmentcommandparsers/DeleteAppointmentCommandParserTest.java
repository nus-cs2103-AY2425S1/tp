package seedu.address.logic.parser.clientcommandparsers.appointmentcommandparsers;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.clientcommands.appointmentcommands.DeleteAppointmentCommand;

public class DeleteAppointmentCommandParserTest {
    private DeleteAppointmentCommandParser parser = new DeleteAppointmentCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "Alice Pauline", new DeleteAppointmentCommand(ALICE.getName()));
    }

    @Test
    public void parse_missingField_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "$$", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }
}
