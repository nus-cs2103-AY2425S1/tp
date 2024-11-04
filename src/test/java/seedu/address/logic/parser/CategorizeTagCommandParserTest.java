package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

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
    public void parse_validSingleTagCategoryArg_success() {
        CategorizeTagCommand expectedCatTagCommand =
                new CategorizeTagCommand(Arrays.asList(new Tag("test")), TagCategory.ACTIVITIES);
        assertParseSuccess(parser, "cattag t/test activity", expectedCatTagCommand);
    }

    @Test
    public void parse_validMultipleTagsCategoryArg_success() {
        CategorizeTagCommand expectedCatTagCommand =
                new CategorizeTagCommand(Arrays.asList(new Tag("test1"), new Tag("test2")), TagCategory.ACADEMICS);
        assertParseSuccess(parser, "cattag t/test1 t/test2 acads", expectedCatTagCommand);
    }

    @Test
    public void parse_validTagsWithMixedSpacing_success() {
        CategorizeTagCommand expectedCatTagCommand =
                new CategorizeTagCommand(Arrays.asList(new Tag("test"), new Tag("test2")), TagCategory.GENERAL);
        assertParseSuccess(parser, "cattag  t/test   t/test2   general", expectedCatTagCommand);
    }

    @Test
    public void parse_categoryInDifferentFormats_throwsParseException() {
        assertParseFailure(parser, "cattag t/test 1invalid",
                String.format(CategorizeTagCommand.MESSAGE_INVALID_CATEGORY, "1invalid"));
        assertParseFailure(parser, "cattag t/test# invalidcategory",
                Tag.MESSAGE_CONSTRAINTS);
    }
}
