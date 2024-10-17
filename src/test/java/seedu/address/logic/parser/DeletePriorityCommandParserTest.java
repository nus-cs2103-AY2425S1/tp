package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeletePriorityCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeletePriorityCommandParserTest {

    private final DeletePriorityCommandParser parser = new DeletePriorityCommandParser();

    @Test
    public void parse_validIndex_returnsDeletePriorityCommand() throws Exception {
        // Test with a valid index
        DeletePriorityCommand command = parser.parse("1");
        assertEquals(new DeletePriorityCommand(INDEX_FIRST_PERSON.getOneBased()), command);
    }

    @Test
    public void parse_validIndexWithWhitespace_returnsDeletePriorityCommand() throws Exception {
        // Test with leading and trailing whitespace
        DeletePriorityCommand command = parser.parse("   2   ");
        assertEquals(new DeletePriorityCommand(INDEX_SECOND_PERSON.getOneBased()), command);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // Test with an invalid index (non-integer)
        assertThrows(ParseException.class, () -> parser.parse("a"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePriorityCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        // Test with a negative index
        assertThrows(ParseException.class, () -> parser.parse("-1"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePriorityCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_zeroIndex_throwsParseException() {
        // Test with zero index
        assertThrows(ParseException.class, () -> parser.parse("0"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePriorityCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        // Test with an empty input
        assertThrows(ParseException.class, () -> parser.parse(""),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePriorityCommand.MESSAGE_USAGE));
    }

}
