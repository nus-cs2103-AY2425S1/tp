package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.model.tag.Tag;

public class SortCommandParserTest {

    private final SortCommandParser parser = new SortCommandParser();

    //Parsers correctly ASC and return SortCommand
    @Test
    public void parse_validTagAndAscendingOrder_returnsSortCommand() {
        SortCommand expectedSortCommand = new SortCommand("name", "ASC");
        assertParseSuccess(parser, "t\\name ASC", expectedSortCommand);
    }
    //Parsers correctly DESC and return SortCommand
    @Test
    public void parse_validTagAndDescendingOrder_returnsSortCommand() {
        SortCommand expectedSortCommand = new SortCommand("priority", "DESC");
        assertParseSuccess(parser, "t\\priority DESC", expectedSortCommand);
    }
    //Invalid Tag
    @Test
    public void parse_invalidTagFormat_throwsParseException() {
        assertParseFailure(parser, "t\\n@me ASC",
                String.format(Tag.MESSAGE_TAG_NAMES_SHOULD_BE_ALPHANUMERIC));
    }
    //Invalid Order
    @Test
    public void parse_invalidSortOrder_throwsParseException() {
        assertParseFailure(parser, "t\\name UP",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
    //Missing Order
    @Test
    public void parse_missingSortOrder_throwsParseException() {
        assertParseFailure(parser, "t\\name",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
    //No tag
    @Test
    public void parse_missingTag_throwsParseException() {
        assertParseFailure(parser, "ASC",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
    //Test empty input
    @Test
    public void parse_emptyInput_throwsParseException() {
        assertParseFailure(parser, "    ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
