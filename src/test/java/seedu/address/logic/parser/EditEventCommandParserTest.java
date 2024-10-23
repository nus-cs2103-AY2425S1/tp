package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.EditEventDescriptorBuilder;

public class EditEventCommandParserTest {
    @Test
    void parse_allFieldsPresent_success() throws Exception {
        EditEventCommandParser parser = new EditEventCommandParser();
        EditEventCommand expectedCommand = new EditEventCommand(Index.fromOneBased(1),
                new EditEventDescriptorBuilder().withName("New Event").build());
        assertEquals(expectedCommand, parser.parse("1 n/New Event"));
    }

    @Test
    void parse_noFieldsPresent_failure() {
        EditEventCommandParser parser = new EditEventCommandParser();
        assertThrows(ParseException.class, () -> parser.parse("1"));
    }

    @Test
    void parse_invalidIndex_failure() {
        EditEventCommandParser parser = new EditEventCommandParser();
        assertThrows(ParseException.class, () -> parser.parse("a n/New Event"));
    }

    @Test
    void parse_duplicatePrefixes_failure() {
        EditEventCommandParser parser = new EditEventCommandParser();
        assertThrows(ParseException.class, () -> parser.parse("1 n/New Event n/Another Event"));
    }

    @Test
    void parse_emptyArgs_failure() {
        EditEventCommandParser parser = new EditEventCommandParser();
        assertThrows(ParseException.class, () -> parser.parse(""));
    }
}
