package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;

public class ListCommandParserTest {

    private final ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_emptyArgs_returnsListCommand() {
        // No arguments provided
        assertParseSuccess(parser, "", new ListCommand());
    }

    @Test
    public void parse_whitespaceArg_returnsListCommand() {
        // Whitespace arguments should be ignored
        assertParseSuccess(parser, "   ", new ListCommand());
    }

    @Test
    public void parse_nonEmptyArgs_returnsListCommand() {
        // Any non-empty arguments are ignored in current implementation
        assertParseSuccess(parser, "some random arguments", new ListCommand());
    }
}
