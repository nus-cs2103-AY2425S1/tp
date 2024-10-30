package seedu.eventtory.logic.parser;

import static seedu.eventtory.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_VENDOR;
import static seedu.eventtory.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.eventtory.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.eventtory.commons.core.index.Index;
import seedu.eventtory.logic.Messages;
import seedu.eventtory.logic.commands.UnassignCommand;

class UnassignCommandParserTest {

    private final UnassignCommandParser parser = new UnassignCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        UnassignCommand expectedCommand = new UnassignCommand(
                Index.fromOneBased(1), Index.fromOneBased(2));
        assertParseSuccess(parser, " v/1 e/2", expectedCommand);

        // Whitespace handling
        assertParseSuccess(parser, "  v/1    e/2  ", expectedCommand);

        // Large index values
        UnassignCommand expectedCommandLarge = new UnassignCommand(
                Index.fromOneBased(999999), Index.fromOneBased(999999));
        assertParseSuccess(parser, " v/999999 e/999999", expectedCommandLarge);
    }

    @Test
    public void parse_missingFields_failure() {
        // Missing vendor prefix: only event prefix provided
        assertParseFailure(parser, " e/2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignCommand.MESSAGE_USAGE));

        // Missing event prefix: only vendor prefix provided
        assertParseFailure(parser, " v/1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignCommand.MESSAGE_USAGE));

        // Completely empty input
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgumentValue_failure() {
        // Non-numeric vendor index
        assertParseFailure(parser, " v/a e/1", ParserUtil.MESSAGE_INVALID_INDEX);

        // Non-numeric event index
        assertParseFailure(parser, " v/1 e/b", ParserUtil.MESSAGE_INVALID_INDEX);

        // Missing vendor or event indexes
        assertParseFailure(parser, " v/ e/", ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, " v/1 e/", ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, " v/ e/1", ParserUtil.MESSAGE_INVALID_INDEX);

        // Zero and negative index values
        assertParseFailure(parser, " v/0 e/1", ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, " v/1 e/0", ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, " v/-1 e/1", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidPrefixes_failure() {
        // Unrecognized prefix
        assertParseFailure(parser, " x/1 e/2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraArguments_failure() {
        // Extra arguments in input
        assertParseFailure(parser, " v/1 e/2 extra", ParserUtil.MESSAGE_INVALID_INDEX);

        // Repeated arguments
        assertParseFailure(parser, " v/1 e/2 v/2", Messages.MESSAGE_DUPLICATE_FIELDS + PREFIX_VENDOR);
        assertParseFailure(parser, " v/1 e/2 e/2", Messages.MESSAGE_DUPLICATE_FIELDS + PREFIX_EVENT);
    }
}
