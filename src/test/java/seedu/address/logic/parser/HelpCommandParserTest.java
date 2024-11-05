package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
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
