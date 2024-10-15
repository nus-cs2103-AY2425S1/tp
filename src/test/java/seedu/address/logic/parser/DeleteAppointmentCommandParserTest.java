package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.model.person.Name;

public class DeleteAppointmentCommandParserTest {
    private DeleteAppointmentCommandParser parser = new DeleteAppointmentCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "John", new DeleteAppointmentCommand(new Name("John")));
    }
}
