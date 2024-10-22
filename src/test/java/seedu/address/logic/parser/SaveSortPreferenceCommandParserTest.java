package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.SaveSortPreferenceCommand;

public class SaveSortPreferenceCommandParserTest {

    private SaveSortPreferenceCommandParser parser = new SaveSortPreferenceCommandParser();

    @Test
    public void parse_high_returnsCommand() {
        assertParseSuccess(parser, "high", new SaveSortPreferenceCommand("high"));
    }

    @Test
    public void parse_high_with_space_returnsCommand() {
        assertParseSuccess(parser, " high ", new SaveSortPreferenceCommand("high"));
    }

    @Test
    public void parse_low_returnsCommand() {
        assertParseSuccess(parser, "low", new SaveSortPreferenceCommand("low"));
    }

    @Test
    public void parse_low_with_space_returnsCommand() {
        assertParseSuccess(parser, " low ", new SaveSortPreferenceCommand("low"));
    }

    @Test
    public void parse_invalidArgs_throwsParseExcpetion() {
        assertParseFailure(parser, "CS2103T",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveSortPreferenceCommand.MESSAGE_USAGE));
    }

}
