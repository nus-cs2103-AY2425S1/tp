package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.GetAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

public class GetAttendanceCommandParserTest {

    private final GetAttendanceCommandParser parser = new GetAttendanceCommandParser();

    @Test
    public void parse_validArgs_returnsGetAttendanceCommand() throws Exception {
        String userInput = " n/John Doe d/2023-10-09";
        GetAttendanceCommand expectedCommand = new GetAttendanceCommand(new Name("John Doe"),
                LocalDate.parse("2023-10-09"));
        GetAttendanceCommand command = parser.parse(userInput);
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_missingName_throwsParseException() {
        String userInput = " d/2023-10-09";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingDate_throwsParseException() {
        String userInput = " n/John Doe";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidDate_throwsParseException() {
        String userInput = " n/John Doe d/invalid-date";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}
