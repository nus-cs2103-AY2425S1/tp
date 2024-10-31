package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LogCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Log;

public class LogCommandParserTest {
    @Test
    public void parse_validArgs_returnsLogCommand() throws Exception {
        LogCommandParser parser = new LogCommandParser();
        LogCommand command = parser.parse("1 25-12-2024 14:30 Attended appointment");
        assertEquals(new LogCommand(Index.fromZeroBased(0), new Log("Attended appointment",
                LocalDateTime.of(2024, 12, 25, 14, 30))), command);
    }

    @Test
    public void parse_missingArgs_throwsParseException() {
        LogCommandParser parser = new LogCommandParser();
        assertThrows(ParseException.class, () -> parser.parse("1 25-12-2024 14:30"));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        LogCommandParser parser = new LogCommandParser();
        assertThrows(ParseException.class, () -> parser.parse("a 25-12-2024 14:30 Attended appointment"));
    }

    @Test
    public void parse_invalidDateTime_throwsParseException() {
        LogCommandParser parser = new LogCommandParser();
        assertThrows(ParseException.class, () -> parser.parse("1 25-12-2024 25:30 Attended appointment"));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        LogCommandParser parser = new LogCommandParser();
        assertThrows(ParseException.class, () -> parser.parse(""));
    }
}
