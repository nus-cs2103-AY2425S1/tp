package seedu.eventfulnus.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.eventfulnus.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.eventfulnus.commons.core.index.Index;
import seedu.eventfulnus.logic.commands.EditEventCommand;
import seedu.eventfulnus.logic.parser.exceptions.ParseException;
import seedu.eventfulnus.testutil.EditEventDescriptorBuilder;

public class EditEventCommandParserTest {
    @Test
    void parse_allFieldsPresent_success() throws Exception {
        EditEventCommandParser parser = new EditEventCommandParser();
        EditEventCommand expectedCommand = new EditEventCommand(Index.fromOneBased(1),
                new EditEventDescriptorBuilder().withSport("Bouldering Women").build());
        assertEquals(expectedCommand, parser.parse("1 sp/Bouldering Women"));
    }

    @Test
    void parse_noFieldsPresent_failure() {
        EditEventCommandParser parser = new EditEventCommandParser();
        assertThrows(ParseException.class, () -> parser.parse("1"));
    }

    @Test
    void parse_invalidIndex_failure() {
        EditEventCommandParser parser = new EditEventCommandParser();
        assertThrows(ParseException.class, () -> parser.parse("a sp/Swimming Women"));
    }

    @Test
    void parse_duplicatePrefixes_failure() {
        EditEventCommandParser parser = new EditEventCommandParser();
        assertThrows(ParseException.class, () -> parser.parse("1 sp/Bouldering Women sp/Bouldering Men"));
    }

    @Test
    void parse_emptyArgs_failure() {
        EditEventCommandParser parser = new EditEventCommandParser();
        assertThrows(ParseException.class, () -> parser.parse(""));
    }
}
