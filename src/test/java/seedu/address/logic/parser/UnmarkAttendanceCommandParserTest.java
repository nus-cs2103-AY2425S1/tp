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
        Index[] expectedIndexes = {Index.fromOneBased(1), Index.fromOneBased(2)};
        UnmarkAttendanceCommand command = parser.parse("1 2");
        assertEquals(new UnmarkAttendanceCommand(expectedIndexes), command);
    }

    @Test
    public void parse_singleValidArg_returnsUnmarkAttendanceCommand() throws Exception {
        Index[] expectedIndexes = {Index.fromOneBased(1)};
        UnmarkAttendanceCommand command = parser.parse("1");
        assertEquals(new UnmarkAttendanceCommand(expectedIndexes), command);
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
        // Test input with extra spaces and multiple indices
        Index[] expectedIndexes = {Index.fromOneBased(2), Index.fromOneBased(3)};
        UnmarkAttendanceCommand command = parser.parse("  2   3  ");
        assertEquals(new UnmarkAttendanceCommand(expectedIndexes), command);
    }

    @Test
    public void parse_mixedValidAndInvalidArgs_throwsParseException() {
        // Test input with a mix of valid and invalid args
        assertThrows(ParseException.class, () -> parser.parse("1 a"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
}
