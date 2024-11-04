package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CategorizeTagCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagCategory;

public class CategorizeTagCommandParserTest {
    private CategorizeTagCommandParser parser = new CategorizeTagCommandParser();

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CategorizeTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingCategoryInArgs_throwsParseException() {
        assertParseFailure(parser, " t/test", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CategorizeTagCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " t/test  ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CategorizeTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidCategoryArg_throwsParseException() {
        assertParseFailure(parser, "cattag t/test invalid ",
                String.format(CategorizeTagCommand.MESSAGE_INVALID_CATEGORY, "invalid"));
    }

    @Test
    public void parse_validCategoryArg_success() {
        CategorizeTagCommand expectedCatTagCommand =
                new CategorizeTagCommand(new Tag("test"), TagCategory.ACTIVITIES);
        assertParseSuccess(parser, "cattag t/test activity", expectedCatTagCommand);
    }
}
