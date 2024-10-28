package seedu.address.logic.parser.event.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.event.commands.AddEventCommand;
import seedu.address.logic.parser.event.parser.NewEventCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;

public class NewEventCommandParserTest {
    @Test
    public void parse_emptyString_exceptionThrown() {
        assertThrows(ParseException.class, () -> new NewEventCommandParser().parse(""));
    }

    @Test
    public void parse_nonEmptyString_success() throws ParseException {
        AddEventCommand actual = new NewEventCommandParser().parse("eventA");
        AddEventCommand expected = new AddEventCommand(new Event("eventA"));
        assertEquals(actual, expected);
    }
}
