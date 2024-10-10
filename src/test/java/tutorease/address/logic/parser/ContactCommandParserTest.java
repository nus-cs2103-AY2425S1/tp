package tutorease.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorease.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import tutorease.address.logic.commands.Command;
import tutorease.address.logic.commands.DeleteContactCommand;
import tutorease.address.logic.commands.ListContactCommand;
import tutorease.address.logic.parser.exceptions.ParseException;

public class ContactCommandParserTest {

    private final ContactCommandParser parser = new ContactCommandParser();

    @Test
    public void parse_validDeleteCommand_success() throws Exception {
        // Test for valid delete command parsing
        String validDeleteCommand = "delete 1";
        Command result = parser.parse(validDeleteCommand);
        assertTrue(result instanceof DeleteContactCommand);
    }

    @Test
    public void parse_validListCommand_success() throws Exception {
        String validListCommand = "list";
        Command result = parser.parse(validListCommand);
        assertTrue(result instanceof ListContactCommand);
    }

    @Test
    public void parse_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "unknownCommand";
        assertThrows(ParseException.class, () -> parser.parse(invalidCommand));
    }

    @Test
    public void parse_unknownSubCommand_throwsParseException() {
        String unknownSubCommand = "eat John Doe";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(unknownSubCommand));
        assertEquals("Unknown contact sub-command: eat", exception.getMessage());
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        String emptyArgs = " ";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(emptyArgs));
        assertEquals(MESSAGE_INVALID_COMMAND_FORMAT, exception.getMessage());
    }
}

