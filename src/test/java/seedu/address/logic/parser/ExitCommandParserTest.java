package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ExitCommandParserTest {

    private final ExitCommandParser parser = new ExitCommandParser();

    @Test
    public void parse_validExitCommand_returnsExitCommand() {
        try {
            Command result = parser.parse("exit");
            assertNotNull(result);
        } catch (ParseException e) {
            fail("ParseException should not have been thrown: " + e.getMessage());
        } catch (AssertionError er) {
            fail("Result should not have returned as a null" + er.getMessage());
        }
    }

    @Test
    public void parse_oneExtraArg_throwsParseException() {
        assertParseFailure(parser, "exit 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExitCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleExtraArgs_throwsParseException() {
        assertParseFailure(parser, "exit 1 w 2 ewww",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExitCommand.MESSAGE_USAGE));
    }
}