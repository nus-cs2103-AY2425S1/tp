package seedu.address.logic.parser.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.task.MarkTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class MarkTaskCommandParserTest {

    private final MarkTaskCommandParser parser = new MarkTaskCommandParser();

    @Test
    public void parse_validArgs_returnsMarkTaskCommand() throws Exception {
        // Single index
        MarkTaskCommand expectedCommand = new MarkTaskCommand(Set.of(INDEX_FIRST));
        assertEquals(expectedCommand, parser.parse("1"));

        // Multiple indexes
        expectedCommand = new MarkTaskCommand(Set.of(INDEX_FIRST, INDEX_SECOND));
        assertEquals(expectedCommand, parser.parse("1 2"));
    }


    @Test
    public void parse_invalidArgs_throwsParseException() {
        // No arguments
        assertThrows(ParseException.class, () -> parser.parse(""),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkTaskCommand.MESSAGE_USAGE));

        // Non-integer input
        assertThrows(ParseException.class, () -> parser.parse("a"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        // Incorrect format (spaces only)
        assertThrows(ParseException.class, () -> parser.parse("   "),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkTaskCommand.MESSAGE_USAGE));

        // Additional non-integer characters
        assertThrows(ParseException.class, () -> parser.parse("1 a 3"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkTaskCommand.MESSAGE_USAGE));
    }
}
