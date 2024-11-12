package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.DeleteTagCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.tag.Tag.MAX_CHARACTER_LENGTH;
import static seedu.address.testutil.TypicalTags.BRIDES_SIDE;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TypicalTags;

public class DeleteTagCommandParserTest {

    private DeleteTagCommandParser parser = new DeleteTagCommandParser();

    /**
     * EP: Single valid lowercase argument.
     */
    @Test
    public void parse_validArgs_returnsDeleteTagCommand() {
        Tag expectedTag = BRIDES_SIDE;
        List<Tag> expectedTags = new ArrayList<>();
        expectedTags.add(expectedTag);
        assertParseSuccess(parser, " t/bride's side", new DeleteTagCommand(expectedTags, false));
    }

    /**
     * EP: Single valid argument of the maximum allowable length.
     */
    @Test
    public void parse_maximumLengthArgs_returnsDeleteTagCommand() {
        String maxLengthTagName = "a".repeat(MAX_CHARACTER_LENGTH);
        Tag expectedTag = new Tag(maxLengthTagName);
        List<Tag> expectedTags = new ArrayList<>();
        expectedTags.add(expectedTag);

        assertParseSuccess(parser, " t/" + maxLengthTagName, new DeleteTagCommand(expectedTags, false));
    }

    /**
     * EP: Single valid argument with leading and trailing spaces.
     */
    @Test
    public void parse_leadingAndTrailingSpaces_returnsDeleteTagCommand() {
        Tag expectedTag = BRIDES_SIDE;

        List<Tag> expectedTags = new ArrayList<>();
        expectedTags.add(expectedTag);
        assertParseSuccess(parser, "  t/ bride's side   ", new DeleteTagCommand(expectedTags, false));
    }

    /**
     * EP: Multiple valid arguments.
     */
    @Test
    public void parse_multipleValidArgs_returnsDeleteTagCommand() {
        Tag expectedTagBride = BRIDES_SIDE;
        Tag expectedTagGroom = TypicalTags.GROOMS_SIDE;
        List<Tag> expectedTags = new ArrayList<>();
        expectedTags.add(expectedTagBride);
        expectedTags.add(expectedTagGroom);
        assertParseSuccess(parser, " t/bride's side t/groom's side", new DeleteTagCommand(expectedTags, false));
    }

    /**
     * EP: Multiple valid arguments, each of the maximum allowable length.
     */
    @Test
    public void parse_multipleMaximumLengthArgs_returnsDeleteTagCommand() {
        String maxNameA = "a".repeat(MAX_CHARACTER_LENGTH);
        String maxNameB = "b".repeat(MAX_CHARACTER_LENGTH);
        List<Tag> expectedTags = new ArrayList<>();
        expectedTags.add(new Tag(maxNameA));
        expectedTags.add(new Tag(maxNameB));
        assertParseSuccess(parser, " t/" + maxNameA + " t/" + maxNameB, new DeleteTagCommand(expectedTags, false));
    }

    /**
     * EP: Single tag name exceeding maximum length.
     */
    @Test
    public void parse_exceedsMaxLength_throwsParseException() {
        String longTagName = "a".repeat(MAX_CHARACTER_LENGTH + 1);
        assertParseFailure(parser, " t/" + longTagName, String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    /**
     * EP: Single tag name with invalid characters.
     */
    @Test
    public void parse_invalidCharacters_throwsParseException() {
        assertParseFailure(parser, "t/;%<>}{", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, "t/¡£™", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, "t/¶¢", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    /**
     * EP: Mix of valid and invalid arguments.
     */
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

    /**
     * EP: Single valid uppercase argument (should be case-insensitive).
     */
    @Test
    public void parse_caseInsensitiveTag_returnsNewtagCommand() {
        Tag expectedTag = new Tag("friend");
        List<Tag> expectedTags = new ArrayList<>();
        expectedTags.add(expectedTag);
        assertParseSuccess(parser, " t/FRIEND", new DeleteTagCommand(expectedTags, false));
    }

    /**
     * EP: Formatting errors.
     */
    @Test
    public void parse_emptyString_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, " t/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_spacesAsArgs_throwsParseException() {
        assertParseFailure(parser, " t/    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsNoLeadingSpace_throwsParseException() {
        assertParseFailure(parser, "t/bride's side", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertParseFailure(parser, " bride's side", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

}
