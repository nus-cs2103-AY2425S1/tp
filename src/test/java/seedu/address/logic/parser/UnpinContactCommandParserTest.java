package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnpinContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class UnpinContactCommandParserTest {

    private final UnpinContactCommandParser parser = new UnpinContactCommandParser();

    @Test
    public void parse_validArgs_returnsUnpinContactCommand() throws Exception {
        UnpinContactCommand command = parser.parse("1");
        assertEquals(new UnpinContactCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("a"));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
    }
}
