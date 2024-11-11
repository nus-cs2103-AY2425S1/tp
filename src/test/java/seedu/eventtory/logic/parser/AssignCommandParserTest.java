package seedu.eventtory.logic.parser;

import static seedu.eventtory.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eventtory.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.eventtory.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.eventtory.commons.core.index.Index;
import seedu.eventtory.logic.commands.AssignCommand;

class AssignCommandParserTest {

    private final AssignCommandParser parser = new AssignCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        AssignCommand expectedCommand = new AssignCommand(Index.fromOneBased(2));
        assertParseSuccess(parser, " 2", expectedCommand);

        // Whitespace handling
        assertParseSuccess(parser, "   2  ", expectedCommand);

        // Large index values
        AssignCommand expectedCommandLarge = new AssignCommand(Index.fromOneBased(999999));
        assertParseSuccess(parser, " 999999", expectedCommandLarge);
    }

    @Test
    public void parse_missingFields_failure() {
        // Completely empty input
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));

        // Whitespace only
        assertParseFailure(parser, "          ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgumentValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE);
        // Non-numeric index
        assertParseFailure(parser, " a", expectedMessage);

        // Zero and negative index values
        assertParseFailure(parser, " 0", expectedMessage);
        assertParseFailure(parser, " -1", expectedMessage);
    }

    @Test
    public void parse_invalidPrefixes_failure() {
        // Unrecognized prefix
        assertParseFailure(parser, " x/1 e/2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraArguments_failure() {
        // Extra arguments in input
        assertParseFailure(parser, " 1 extra",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));

        // Repeated arguments
        assertParseFailure(parser, " 1 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
    }
}
