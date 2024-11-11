package seedu.eventtory.logic.parser;

import static seedu.eventtory.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eventtory.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.eventtory.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.eventtory.commons.core.index.Index;
import seedu.eventtory.logic.commands.UnassignCommand;

class UnassignCommandParserTest {

    private final UnassignCommandParser parser = new UnassignCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        UnassignCommand expectedCommand = new UnassignCommand(
                Index.fromOneBased(1));
        assertParseSuccess(parser, " 1", expectedCommand);

        // Whitespace handling
        assertParseSuccess(parser, "  1", expectedCommand);

        // Large index values
        UnassignCommand expectedCommandLarge = new UnassignCommand(
                Index.fromOneBased(999999));
        assertParseSuccess(parser, " 999999", expectedCommandLarge);
    }

    @Test
    public void parse_missingFields_failure() {
        // Completely empty input
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgumentValue_failure() {
        // Non-numeric index
        assertParseFailure(parser, " a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignCommand.MESSAGE_USAGE));

        // Zero and negative index values
        assertParseFailure(parser, " 0", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " -1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraArguments_failure() {
        // Extra arguments in input
        assertParseFailure(parser, " 1 extra",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 1 2",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignCommand.MESSAGE_USAGE));
    }
}
