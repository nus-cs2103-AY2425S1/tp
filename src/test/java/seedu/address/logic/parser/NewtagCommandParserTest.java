package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.NewtagCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.NewtagCommand;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TypicalTags;

public class NewtagCommandParserTest {

    private NewtagCommandParser parser = new NewtagCommandParser();

    @Test
    public void parse_validArgs_returnsNewtagCommand() {
        Tag expectedTag = TypicalTags.VALID_TAG_BRIDES_FRIEND;
        List<Tag> expectedTags = new ArrayList<>();
        expectedTags.add(expectedTag);
        assertParseSuccess(parser, " t/bride's friend", new NewtagCommand(expectedTags));
    }

    @Test
    public void parse_leadingAndTrailingSpaces_returnsNewtagCommand() {
        Tag expectedTag = TypicalTags.VALID_TAG_BRIDES_FRIEND;
        List<Tag> expectedTags = new ArrayList<>();
        expectedTags.add(expectedTag);
        assertParseSuccess(parser, " t/   bride's friend   ", new NewtagCommand(expectedTags));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, " t/    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertParseFailure(parser, "bride's friend", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_exceedsMaxLength_throwsParseException() {
        String longTag = "a".repeat(51); // 51 characters, exceeding the 50-character limit.
        assertParseFailure(parser, longTag, String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidCharacters_throwsParseException() {
        assertParseFailure(parser, ";%<>}{", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, "¡£™", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, "¶¢", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_caseInsensitiveTag_returnsNewtagCommand() {
        Tag expectedTag = new Tag("friend");
        List<Tag> expectedTags = new ArrayList<>();
        expectedTags.add(expectedTag);
        assertParseSuccess(parser, " t/FRIEND", new NewtagCommand(expectedTags));
    }

    @Test
    public void parse_multipleValidArgs_returnsNewtagCommand() {
        Tag tagBridesFriend = TypicalTags.VALID_TAG_BRIDES_FRIEND;
        Tag tagColleagues = TypicalTags.COLLEAGUES;
        List<Tag> expectedTags = new ArrayList<>();
        expectedTags.add(tagBridesFriend);
        expectedTags.add(tagColleagues);
        assertParseSuccess(parser, " t/bride's friend t/colleagues", new NewtagCommand(expectedTags));
    }

    @Test
    public void parse_multipleValidArgsWithoutSpace_throwsParseException() {
        assertParseFailure(parser, " t/bride's sidet/groom's side",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewtagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleArgsOneInvalid_throwsParseException() {
        assertParseFailure(parser, " t/bride's side t/^@%",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewtagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleArgsOneExceedsMaxLength_throwsParseException() {
        assertParseFailure(parser, " t/bride's side t/" + "a".repeat(51),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewtagCommand.MESSAGE_USAGE));
    }
}
