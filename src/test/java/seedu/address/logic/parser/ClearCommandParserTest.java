package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.ClearCommand.MESSAGE_CONFIRMATION_REQUIRED;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;

public class ClearCommandParserTest {

    private final ClearCommandParser parser = new ClearCommandParser();

    @Test
    public void parse_withoutConfirmKeyword_throwsParseException() {
        assertParseFailure(parser, "", MESSAGE_CONFIRMATION_REQUIRED);
        assertParseFailure(parser, " ", MESSAGE_CONFIRMATION_REQUIRED);
        assertParseFailure(parser, "clear", MESSAGE_CONFIRMATION_REQUIRED);
    }

    @Test
    public void parse_withConfirmKeyword_returnsClearCommand() throws Exception {
        // Test with the "confirm" keyword provided
        assertTrue(parser.parse("confirm") instanceof ClearCommand);
        assertTrue(parser.parse(" confirm ") instanceof ClearCommand);
        assertTrue(parser.parse("CONFIRM") instanceof ClearCommand);
    }

    @Test
    public void parse_withInvalidInput_throwsParseException() {
        // Test with invalid input other than "confirm"
        assertParseFailure(parser, "yes", MESSAGE_CONFIRMATION_REQUIRED);
        assertParseFailure(parser, "no", MESSAGE_CONFIRMATION_REQUIRED);
        assertParseFailure(parser, "something else", MESSAGE_CONFIRMATION_REQUIRED);
    }
}

