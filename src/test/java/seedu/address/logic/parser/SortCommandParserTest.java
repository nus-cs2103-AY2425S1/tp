package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgs_returnsSortCommand() {
        // Test case for sorting by name in ascending order
        assertParseSuccess(parser, "name ascending", new SortCommand("name", true));

        // Test case for sorting by name in descending order
        assertParseSuccess(parser, "name descending", new SortCommand("name", false));

        // Test case for sorting by deadline in ascending order
        assertParseSuccess(parser, "deadline ascending", new SortCommand("deadline", true));

        // Test case for sorting by deadline in descending order
        assertParseSuccess(parser, "deadline descending", new SortCommand("deadline", false));
    }

    @Test
    public void parse_extraWhitespace_returnsSortCommand() {
        // Test with extra whitespace before and between valid inputs
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "name ascending", new SortCommand("name", true));
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "name" + PREAMBLE_WHITESPACE + "ascending",
                new SortCommand("name", true));
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "deadline descending" + PREAMBLE_WHITESPACE,
                new SortCommand("deadline", false));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Test case for invalid keyword
        assertParseFailure(parser, "invalidField ascending", SortCommand.MESSAGE_INVALID_KEYWORD);

        // Test case for missing ascending/descending keyword
        assertParseFailure(parser, "name", String.format(SortCommand.MESSAGE_USAGE));

        // Test case for invalid order
        assertParseFailure(parser, "name invalidOrder", String.format(SortCommand.MESSAGE_INVALID_ORDER));

        // Test case for empty command
        assertParseFailure(parser, "", String.format(SortCommand.MESSAGE_USAGE));
    }
}
