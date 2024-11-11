package seedu.address.logic.parser;


import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NUMBERS;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_SPECIAL_CHARACTERS;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;

/**
 * Test cases for SortCommandParser.
 */
public class SortCommandParserTest {

    private final SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgs_returnsSortCommand() {
        // Empty arguments should return a valid SortCommand.
        assertParseSuccess(parser, "", new SortCommand());

        // white spaces only
        assertParseSuccess(parser, "   ", new SortCommand());

        // white spaces with tabs, enter and  new line
        assertParseSuccess(parser, PREAMBLE_WHITESPACE, new SortCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Any non-empty input should result in a ParseException.
        assertParseFailure(parser, PREAMBLE_NON_EMPTY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        assertParseFailure(parser, PREAMBLE_SPECIAL_CHARACTERS,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        assertParseFailure(parser, PREAMBLE_NUMBERS,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "sort by name",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
