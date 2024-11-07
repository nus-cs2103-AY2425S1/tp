package seedu.address.logic.parser.eventcommandparser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.eventcommands.EventDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class EventDeleteCommandParserTest {

    private final EventDeleteCommandParser parser = new EventDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsEventDeleteCommand() throws ParseException {
        String input = "1";
        EventDeleteCommand expectedCommand = new EventDeleteCommand(Index.fromOneBased(1));
        EventDeleteCommand command = parser.parse(input);

        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String invalidInput = "abc";
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse(invalidInput));

        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventDeleteCommand.MESSAGE_USAGE),
                thrown.getMessage());
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        String emptyInput = "";
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse(emptyInput));

        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventDeleteCommand.MESSAGE_USAGE),
                thrown.getMessage());
    }
}
