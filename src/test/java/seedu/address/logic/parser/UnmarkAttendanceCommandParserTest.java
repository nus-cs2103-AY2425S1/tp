package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.UnmarkAttendanceCommand.MESSAGE_USAGE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnmarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class UnmarkAttendanceCommandParserTest {

    private final UnmarkAttendanceCommandParser parser = new UnmarkAttendanceCommandParser();

    @Test
    public void parse_validArgs_returnsUnmarkAttendanceCommand() throws Exception {
        Index expectedIndex = Index.fromOneBased(1);
        UnmarkAttendanceCommand command = parser.parse("1");
        assertEquals(new UnmarkAttendanceCommand(expectedIndex), command);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Test non-numeric input
        assertThrows(ParseException.class, () -> parser.parse("a"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // Test empty input
        assertThrows(ParseException.class, () -> parser.parse(""),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // Test negative input
        assertThrows(ParseException.class, () -> parser.parse("-1"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // Test large invalid number
        assertThrows(ParseException.class, () -> parser.parse("9999999999"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_extraSpacesAroundArgs_returnsUnmarkAttendanceCommand() throws Exception {
        // Test input with extra spaces
        Index expectedIndex = Index.fromOneBased(2);
        UnmarkAttendanceCommand command = parser.parse("  2  ");
        assertEquals(new UnmarkAttendanceCommand(expectedIndex), command);
    }
}
