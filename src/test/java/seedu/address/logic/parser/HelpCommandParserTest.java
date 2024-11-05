package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

public class HelpCommandParserTest {

    private final HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parse_validHelpCommand_returnsHelpCommand() {
        try {
            Command result = parser.parse("help");
            assertNotNull(result);
        } catch (ParseException e) {
            fail("ParseException should not have been thrown: " + e.getMessage());
        } catch (AssertionError er) {
            fail("Result should not have returned as a null" + er.getMessage());
        }
    }

    @Test
    public void parse_validExtraArgs_returnsHelpCommand() {
        try {
            Command result = parser.parse("help 1");
            assertNotNull(result);
        } catch (ParseException e) {
            fail("ParseException should not have been thrown: " + e.getMessage());
        } catch (AssertionError er) {
            fail("Result should not have returned as a null" + er.getMessage());
        }
    }

    @Test
    public void parse_validSpaces_returnsHelpCommand() {
        try {
            Command result = parser.parse("    help");
            assertNotNull(result);
        } catch (ParseException e) {
            fail("ParseException should not have been thrown: " + e.getMessage());
        } catch (AssertionError er) {
            fail("Result should not have returned as a null" + er.getMessage());
        }
    }
}
