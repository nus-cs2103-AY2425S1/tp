package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {

    private final SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validAscInput_success() throws Exception {
        // Test parsing "asc"
        SortCommand command = parser.parse("asc");

        // Verify that the parsed command has the correct order
        assertEquals(new SortCommand("asc"), command);
    }

    @Test
    public void parse_validDescInput_success() throws Exception {
        // Test parsing "desc"
        SortCommand command = parser.parse("desc");

        // Verify that the parsed command has the correct order
        assertEquals(new SortCommand("desc"), command);
    }
}
