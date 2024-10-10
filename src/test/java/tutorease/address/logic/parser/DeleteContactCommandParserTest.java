package tutorease.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tutorease.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorease.address.logic.commands.DeleteContactCommand.MESSAGE_USAGE;

import org.junit.jupiter.api.Test;

import tutorease.address.commons.core.index.Index;
import tutorease.address.logic.commands.DeleteContactCommand;
import tutorease.address.logic.parser.exceptions.ParseException;


public class DeleteContactCommandParserTest {

    private final DeleteContactCommandParser parser = new DeleteContactCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteContactCommand() throws Exception {
        // Test for valid argument "1"
        DeleteContactCommand command = parser.parse("1");
        assertEquals(new DeleteContactCommand(Index.fromOneBased(1)), command);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Test for invalid argument (non-numeric)
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse("a"));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), exception.getMessage());
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        // Test for empty argument
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(" "));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), exception.getMessage());
    }

    @Test
    public void parse_extraWhitespace_returnsDeleteContactCommand() throws Exception {
        // Test for valid argument with extra spaces
        DeleteContactCommand command = parser.parse("   1   ");
        assertEquals(new DeleteContactCommand(Index.fromOneBased(1)), command);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // Test for invalid index (negative or zero)
        ParseException negativeIndexException = assertThrows(ParseException.class, () -> parser.parse("-1"));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), negativeIndexException.getMessage());

        ParseException zeroIndexException = assertThrows(ParseException.class, () -> parser.parse("0"));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), zeroIndexException.getMessage());
    }
}
