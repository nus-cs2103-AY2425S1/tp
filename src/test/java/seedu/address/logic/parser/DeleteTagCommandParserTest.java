package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.DeleteTagCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TypicalTags;

public class DeleteTagCommandParserTest {

    private DeleteTagCommandParser parser = new DeleteTagCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteTagCommand() {
        Tag expectedTag = TypicalTags.VALID_TAG_BRIDES_FRIEND;
        List<Tag> expectedTags = new ArrayList<>();
        expectedTags.add(expectedTag);
        assertParseSuccess(parser, " t/bride's friend", new DeleteTagCommand(expectedTags));
    }

    @Test
    public void parse_validArgsNoLeadingSpace_throwsParseException() {
        assertParseFailure(parser, "t/bride's friend", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsNoPrefix_throwsParseException() {
        assertParseFailure(parser, "bride's friend", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_leadingAndTrailingSpaces_returnsDeleteTagCommand() {
        Tag expectedTag = TypicalTags.VALID_TAG_BRIDES_FRIEND;
        List<Tag> expectedTags = new ArrayList<>();
        expectedTags.add(expectedTag);
        assertParseSuccess(parser, "  t/ bride's friend   ", new DeleteTagCommand(expectedTags));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, " t/     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_maxLength_returnsDeleteTagCommand() {
        String maxLengthTagName = "a".repeat(50); // 50 characters
        Tag expectedTag = new Tag(maxLengthTagName);
        List<Tag> expectedTags = new ArrayList<>();
        expectedTags.add(expectedTag);

        assertParseSuccess(parser, " t/" + maxLengthTagName, new DeleteTagCommand(expectedTags));
    }

    @Test
    public void parse_exceedsMaxLength_throwsParseException() {
        String longTagName = "a".repeat(51); // 51 characters, exceeding the 50-character limit.
        assertParseFailure(parser, " t/" + longTagName, String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidCharacters_throwsParseException() {
        assertParseFailure(parser, "t/;%<>}{", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, "t/¡£™", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, "t/¶¢", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_caseInsensitiveTag_returnsNewtagCommand() {
        Tag expectedTag = new Tag("friend");
        List<Tag> expectedTags = new ArrayList<>();
        expectedTags.add(expectedTag);
        assertParseSuccess(parser, " t/FRIEND", new DeleteTagCommand(expectedTags));
    }

    @Test
    public void parse_multipleValidArgs_returnsDeleteTagCommand() {
        Tag expectedTagBride = TypicalTags.BRIDES_SIDE;
        Tag expectedTagGroom = TypicalTags.GROOMS_SIDE;
        List<Tag> expectedTags = new ArrayList<>();
        expectedTags.add(expectedTagBride);
        expectedTags.add(expectedTagGroom);
        assertParseSuccess(parser, " t/bride's side t/groom's side", new DeleteTagCommand(expectedTags));
    }

    @Test
    public void parse_multipleValidArgsWithoutSpace_throwsParseException() {
        assertParseFailure(parser, " t/bride's sidet/groom's side",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleArgsOneInvalid_throwsParseException() {
        assertParseFailure(parser, " t/bride's side t/^@%",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleArgsOneExceedsMaxLength_throwsParseException() {
        assertParseFailure(parser, " t/bride's side t/" + "a".repeat(51),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
}
