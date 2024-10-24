package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.model.person.Name;
import seedu.address.model.person.Schedule;

public class DeleteAppointmentCommandParserTest {
    private DeleteAppointmentCommandParser parser = new DeleteAppointmentCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteAppointmentCommand() {
        assertParseSuccess(parser, VALID_NAME_AMY + " d/ " + VALID_SCHEDULE_AMY,
                new DeleteAppointmentCommand(new Name(VALID_NAME_AMY), new Schedule(VALID_SCHEDULE_AMY, "")));
    }
}
