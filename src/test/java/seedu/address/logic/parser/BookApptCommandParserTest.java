package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.BookApptCommand;
import seedu.address.model.healthservice.HealthService;
import seedu.address.model.patient.Appt;
import seedu.address.model.patient.Nric;

public class BookApptCommandParserTest {
    private BookApptCommandParser parser = new BookApptCommandParser();

    @Test
    public void parse_validArgs_returnsBookApptCommand() {
        LocalDateTime dateTime = LocalDateTime.parse("2024-12-12 14:00", Appt.STRICT_FORMATTER);
        Appt appt = new Appt(dateTime, new HealthService("CONSULT"));
        String userInput = VALID_NRIC_AMY + " dt|2024-12-12 14:00" + " " + "h|CONSULT";
        BookApptCommand expectedCommand = new BookApptCommand(new Nric(VALID_NRIC_AMY), appt);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidNric_throwsParseException() {
        assertParseFailure(parser, "T1234578a" + " dt|2024-12-12 14:00" + " h|CONSULT", Nric.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidDateTime_throwsParseException() {
        assertParseFailure(parser, VALID_NRIC_AMY + " dt|2024-02-30 14:00:00" + " h|CONSULT",
            Appt.DATETIME_MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidHealthService_throwsParseException() {
        assertParseFailure(parser, VALID_NRIC_AMY + " dt|2024-12-12 14:00" + " h|BLOOD",
            HealthService.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_missingDateTimePrefix_throwsParseException() {
        assertParseFailure(parser, VALID_NRIC_AMY + " h|CONSULT",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, BookApptCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingNric_throwsParseException() {
        assertParseFailure(parser, "dt|2024-12-12 14:00" + " h|CONSULT",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, BookApptCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingHealthServicePrefix_throwsParseException() {
        assertParseFailure(parser, VALID_NRIC_AMY + " dt|2024-12-12 14:00",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, BookApptCommand.MESSAGE_USAGE));
    }
}
