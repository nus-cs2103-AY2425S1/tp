package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;

public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    void parse_validArgs_returnsAddEventCommand() throws Exception {
        Event event = new Event(new EventName("IFG"));
        AddEventCommand expectedCommand = new AddEventCommand(event);
        assertEquals(expectedCommand, parser.parse(" n/IFG"));
    }

    @Test
    void parse_missingName_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" "));
    }

    @Test
    void parse_invalidArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" n/"));
    }

    @Test
    void parse_duplicatePrefixes_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" n/IFG n/SUNIG"));
    }

    @Test
    void parse_emptyArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
    }
}
