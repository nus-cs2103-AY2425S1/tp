package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DELIVERY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MarkDeliveryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.Status;

public class MarkDeliveryCommandParserTest {

    private final MarkDeliveryCommandParser parser = new MarkDeliveryCommandParser();

    @Test
    public void parse_validArgs_returnsMarkDeliveryCommand() throws Exception {
        MarkDeliveryCommand expectedCommand = new MarkDeliveryCommand(INDEX_FIRST_DELIVERY, Status.DELIVERED);
        // Test for valid input
        assertEquals(expectedCommand, parser.parse("-s 1 DELIVERED"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Test for invalid command format (no status provided)
        assertThrows(ParseException.class, () -> parser.parse("-s 1"));

        // Test for invalid command format (no index provided)
        assertThrows(ParseException.class, () -> parser.parse("DELIVERED"));

        // Test for invalid command format (missing -s)
        assertThrows(ParseException.class, () -> parser.parse("1 DELIVERED"));

        // Test for invalid status
        assertThrows(ParseException.class, () -> parser.parse("-s 1 INVALID_STATUS"));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // Test for invalid index (non-integer)
        assertThrows(ParseException.class, () -> parser.parse("-s abc DELIVERED"));

        // Test for invalid index (negative integer)
        assertThrows(ParseException.class, () -> parser.parse("-s -1 DELIVERED"));
    }

    @Test
    public void parse_caseInsensitiveStatus_returnsCorrectCommand() throws Exception {
        // Test for status with different casing
        MarkDeliveryCommand expectedCommand = new MarkDeliveryCommand(INDEX_FIRST_DELIVERY, Status.DELIVERED);
        assertEquals(expectedCommand, parser.parse("-s 1 delivered"));
        assertEquals(expectedCommand, parser.parse("-s 1 DeLiVeReD"));
    }
}

