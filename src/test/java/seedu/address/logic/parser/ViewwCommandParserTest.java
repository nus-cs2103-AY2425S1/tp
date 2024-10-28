package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_WEDDING;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewwCommand;
import seedu.address.model.wedding.NameMatchesWeddingPredicate;

public class ViewwCommandParserTest {

    private ViewwCommandParser parser = new ViewwCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewwCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validIndex_returnsViewwCommand() {
        // Test with index "1"
        ViewwCommand expectedCommand = new ViewwCommand(INDEX_FIRST_WEDDING, null);
        assertParseSuccess(parser, "1", expectedCommand);

        // Test with leading/trailing spaces
        assertParseSuccess(parser, "  1  ", expectedCommand);
    }

    @Test
    public void parse_validKeywords_returnsViewwCommand() {
        // Test with normal keywords
        ViewwCommand expectedCommand = new ViewwCommand(null,
                new NameMatchesWeddingPredicate(Arrays.asList("Alice", "Adam", "Wedding")));
        assertParseSuccess(parser, "Alice Adam Wedding", expectedCommand);

        // Test with leading/trailing spaces
        assertParseSuccess(parser, "   Alice Adam Wedding   ", expectedCommand);

        // Test with multiple spaces between keywords
        assertParseSuccess(parser, "Alice    Adam    Wedding", expectedCommand);

        // Test with newlines and tabs
        assertParseSuccess(parser, " \n Alice \t Adam  \n Wedding ", expectedCommand);
    }

    @Test
    public void parse_invalidInput_throwsParseException() {
        // Test with invalid index (negative)
        assertParseFailure(parser, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewwCommand.MESSAGE_USAGE));

        // Test with invalid index (zero)
        assertParseFailure(parser, "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewwCommand.MESSAGE_USAGE));

        // Test with invalid index (non-numeric)
        assertParseFailure(parser, "abc1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewwCommand.MESSAGE_USAGE));
    }
}
