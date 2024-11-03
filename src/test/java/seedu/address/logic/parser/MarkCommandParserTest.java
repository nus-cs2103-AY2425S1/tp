package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

public class MarkCommandParserTest {

    private final MarkCommandParser parser = new MarkCommandParser();

    @Test
    public void parse_validArgs_returnsMarkCommand() throws ParseException {
        String userInput = " n/Benson Meier w/4";
        MarkCommand expectedCommand = new MarkCommand(new Name("Benson Meier"), 4);

        MarkCommand actualCommand = parser.parse(userInput);

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_missingName_throwsParseException() {
        String userInput = " w/1";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingWeek_throwsParseException() {
        String userInput = " n/Benson Meier";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidName_throwsParseException() {
        String userInput = " n/!invalidName w/1";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidWeek_throwsParseException() {
        String userInput = " n/Benson Meier w/invalidWeek";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}
