package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.parser.exceptions.ParseException;

class AssignCommandParserTest {

    private final AssignCommandParser parser = new AssignCommandParser();

    @Test
    public void parse_validArgs_returnsAssignCommand() throws Exception {
        // Valid input: "v/1 e/2"
        AssignCommand expectedCommand = new AssignCommand(Index.fromOneBased(1), Index.fromOneBased(2));
        AssignCommand resultCommand = parser.parse(" v/1 e/2");
        
        assertEquals(expectedCommand, resultCommand);
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        // Invalid format: missing both prefixes
        assertThrows(ParseException.class, () -> parser.parse(" 1 2"));
    }

    @Test
    public void parse_missingVendorPrefix_throwsParseException() {
        // Missing vendor prefix: only event prefix provided
        assertThrows(ParseException.class, () -> parser.parse(" e/2"));
    }

    @Test
    public void parse_missingEventPrefix_throwsParseException() {
        // Missing event prefix: only vendor prefix provided
        assertThrows(ParseException.class, () -> parser.parse(" v/1"));
    }

    @Test
    public void parse_nonNumericVendorIndex_throwsParseException() {
        // Non-numeric vendor index
        assertThrows(ParseException.class, () -> parser.parse(" v/a e/1"));
    }

    @Test
    public void parse_nonNumericEventIndex_throwsParseException() {
        // Non-numeric event index
        assertThrows(ParseException.class, () -> parser.parse(" v/1 e/b"));
    }

    @Test
    public void parse_extraArguments_throwsParseException() {
        // Extra arguments in input
        assertThrows(ParseException.class, () -> parser.parse(" v/1 e/2 some_extra_arg"));
    }

    @Test
    public void parse_missingIndexes_throwsParseException() {
        // Missing index values for vendor and event
        assertThrows(ParseException.class, () -> parser.parse(" v/ e/"));
    }
}
