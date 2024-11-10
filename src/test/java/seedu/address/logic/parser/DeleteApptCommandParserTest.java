package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPatients.DANIEL;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteApptCommand;
import seedu.address.model.patient.Appt;
import seedu.address.model.patient.Nric;

public class DeleteApptCommandParserTest {
    private DeleteApptCommandParser parser = new DeleteApptCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteApptCommand() {
        LocalDateTime apptDateTime = LocalDateTime.parse("2030-06-06 22:00", Appt.FORMATTER);
        String userInput = DANIEL.getNric() + " dt|2030-06-06 22:00";
        DeleteApptCommand expectedCommand = new DeleteApptCommand(DANIEL.getNric(), apptDateTime);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidNric_throwsParseException() {
        assertParseFailure(parser, "T1234578a" + " dt|2030-06-06 22:00", Nric.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidDateTime_throwsParseException() {
        assertParseFailure(parser, DANIEL.getNric() + " dt|2025-02-30 22:00:00",
            Appt.DATETIME_MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_missingDateTimePrefix_throwsParseException() {
        assertParseFailure(parser, DANIEL.getNric() + " ", "No valid prefixes found \n"
            + DeleteApptCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_missingNric_throwsParseException() {
        assertParseFailure(parser, "dt|2030-06-06 22:00",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteApptCommand.MESSAGE_USAGE));
    }

}
