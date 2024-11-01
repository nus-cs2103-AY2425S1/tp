package seedu.address.logic.parser.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.task.UnassignTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class UnassignTaskCommandParserTest {

    private UnassignTaskCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new UnassignTaskCommandParser();
    }

    @Test
    public void parse_validArgs_returnsUnassignTaskCommand() throws Exception {
        String args = "1 2";

        UnassignTaskCommand result = parser.parse(args);

        UnassignTaskCommand expectedCommand = new UnassignTaskCommand(INDEX_FIRST, Set.of(INDEX_SECOND));
        assertEquals(expectedCommand, result);
    }

    @Test
    public void parse_multipleTaskIndexes_returnsUnassignTaskCommand() throws Exception {
        String args = "1 2 3";

        UnassignTaskCommand result = parser.parse(args);

        UnassignTaskCommand expectedCommand = new UnassignTaskCommand(INDEX_FIRST, Set.of(INDEX_SECOND, INDEX_THIRD));
        assertEquals(expectedCommand, result);
    }

    @Test
    public void parse_missingPersonIndex_throwsParseException() {
        String args = "2";

        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignTaskCommand.MESSAGE_USAGE), ()
                        -> parser.parse(args));
    }

    @Test
    public void parse_missingTaskIndexes_throwsParseException() {
        String args = "1";

        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignTaskCommand.MESSAGE_USAGE), ()
                        -> parser.parse(args));
    }

    @Test
    public void parse_invalidPersonIndex_throwsParseException() {
        String args = "a 2";

        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignTaskCommand.MESSAGE_USAGE), ()
                        -> parser.parse(args));
    }

    @Test
    public void parse_invalidTaskIndex_throwsParseException() {
        String args = "1 a";

        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignTaskCommand.MESSAGE_USAGE), ()
                        -> parser.parse(args));
    }
}
