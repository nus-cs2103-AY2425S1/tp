package seedu.address.logic.parser.tag;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FLORIST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FLORIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.tag.CreateTagCommand;
import seedu.address.logic.parser.CreateTagCommandParser;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagName;

public class CreateTagCommandParserTest {
    private CreateTagCommandParser parser = new CreateTagCommandParser();

    @Test
    public void parse_validTagName_success() {
        Tag expectedTag = new Tag(new TagName(VALID_TAG_FLORIST));
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TAG_DESC_FLORIST, new CreateTagCommand(expectedTag));
    }

    @Test
    public void parse_invalidTagName_failure() {
        assertParseFailure(parser, PREAMBLE_WHITESPACE + INVALID_TAG_DESC, TagName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicateTagName_failure() {
        String validExpectedTagString = TAG_DESC_FLORIST;
        assertParseFailure(parser, TAG_DESC_FLORIST + validExpectedTagString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TAG));
    }

    @Test
    public void parse_emptyTagName_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, CreateTagCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_TAG_FLORIST, expectedMessage);
    }
}
