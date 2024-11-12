package seedu.address.logic.parser.event.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.event.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;

public class NewEventCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT = Event.MESSAGE_CONSTRAINTS;

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

    @Test
    public void parse_nonEmptyString_failure() {
        String longEventName = "The Graduation Ceremony for "
                + "Computer Science and Business Analytics"; // longer than 60 characters

        NewEventCommandParser parser = new NewEventCommandParser();

        assertParseFailure(parser, longEventName, MESSAGE_INVALID_FORMAT);
    }
}
