package tutorease.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tutorease.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import tutorease.address.commons.core.index.Index;
import tutorease.address.logic.commands.DeleteLessonCommand;
import tutorease.address.logic.parser.exceptions.ParseException;


public class DeleteLessonCommandParserTest {

    private final DeleteLessonCommandParser parser = new DeleteLessonCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteLessonCommand() throws Exception {
        // Test for valid argument "1"
        DeleteLessonCommand command = parser.parse("1");
        assertEquals(new DeleteLessonCommand(Index.fromOneBased(1)), command);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Test for invalid argument (non-numeric)
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse("a"));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE),
                exception.getMessage());
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        // Test for empty argument
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(" "));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE),
                exception.getMessage());
    }

    @Test
    public void parse_extraWhitespace_returnsDeleteLessonCommand() throws Exception {
        // Test for valid argument with extra spaces
        DeleteLessonCommand command = parser.parse("   1   ");
        assertEquals(new DeleteLessonCommand(Index.fromOneBased(1)), command);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // Test for invalid index (negative or zero)
        ParseException negativeIndexException = assertThrows(ParseException.class, () -> parser.parse("-1"));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE),
                negativeIndexException.getMessage());

        ParseException zeroIndexException = assertThrows(ParseException.class, () -> parser.parse("0"));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE),
                zeroIndexException.getMessage());
    }
}
