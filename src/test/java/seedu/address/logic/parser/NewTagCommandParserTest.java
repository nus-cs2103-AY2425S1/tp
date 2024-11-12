package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.NewTagCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.tag.Tag.MAX_CHARACTER_LENGTH;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.NewTagCommand;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TypicalTags;

public class NewTagCommandParserTest {

    private NewTagCommandParser parser = new NewTagCommandParser();

    /**
     * EP: Single valid lowercase argument.
     */
    @Test
    public void parse_validArgs_returnsNewtagCommand() {
        Tag expectedTag = TypicalTags.BRIDES_SIDE;
        List<Tag> expectedTags = List.of(expectedTag);
        assertParseSuccess(parser, " t/bride's side", new NewTagCommand(expectedTags));
    }

    /**
     * EP: Single valid argument of the maximum allowable length.
     */
    @Test
    public void parse_maximumLengthArgs_returnsNewtagCommand() {
        String maxName = "a".repeat(MAX_CHARACTER_LENGTH);
        Tag maxTag = new Tag(maxName);
        List<Tag> expectedTags = List.of(maxTag);
        assertParseSuccess(parser, " t/" + maxName, new NewTagCommand(expectedTags));
    }

    /**
     * EP: Single valid argument with leading and trailing spaces.
     */
    @Test
    public void parse_leadingAndTrailingSpaces_returnsNewtagCommand() {
        Tag expectedTag = TypicalTags.BRIDES_SIDE;
        List<Tag> expectedTags = List.of(expectedTag);
        assertParseSuccess(parser, " t/   bride's side   ", new NewTagCommand(expectedTags));
    }

    /**
     * EP: Multiple valid arguments.
     */
    @Test
    public void parse_multipleValidArgs_returnsNewtagCommand() {
        Tag tagBridesFriend = TypicalTags.BRIDES_SIDE;
        Tag tagColleagues = TypicalTags.COLLEAGUES;
        List<Tag> expectedTags = new ArrayList<>();
        expectedTags.add(tagBridesFriend);
        expectedTags.add(tagColleagues);
        assertParseSuccess(parser, " t/bride's side t/colleagues", new NewTagCommand(expectedTags));
    }

    /**
     * EP: Multiple valid arguments, each of the maximum allowable length.
     */
    @Test
    public void parse_multipleMaximumLengthArgs_returnsNewtagCommand() {
        String maxNameA = "a".repeat(MAX_CHARACTER_LENGTH);
        String maxNameB = "b".repeat(MAX_CHARACTER_LENGTH);
        Tag maxTagA = new Tag(maxNameA);
        Tag maxTagB = new Tag(maxNameB);
        List<Tag> expectedTags = List.of(maxTagA, maxTagB);
        assertParseSuccess(parser, " t/" + maxNameA + " t/" + maxNameB,
                new NewTagCommand(expectedTags));
    }

    /**
     * EP: Single tag name exceeding maximum length.
     */
    @Test
    public void parse_exceedsMaxLength_throwsParseException() {
        String longTag = " t/" + "a".repeat(MAX_CHARACTER_LENGTH + 1);
        assertParseFailure(parser, longTag, String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    /**
     * EP: Single tag name with invalid characters.
     */
    @Test
    public void parse_invalidCharacters_throwsParseException() {
        assertParseFailure(parser, " t/;%<>}{", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, " t/¡£™", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, " t/¶¢", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    /**
     * EP: Mix of valid and invalid arguments.
     */
    @Test
    public void parse_multipleArgsOneInvalidChars_throwsParseException() {
        assertParseFailure(parser, " t/bride's side t/^@%",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleArgsOneExceedsMaxLength_throwsParseException() {
        assertParseFailure(parser, " t/bride's side t/" + "a".repeat(MAX_CHARACTER_LENGTH + 1),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewTagCommand.MESSAGE_USAGE));
    }

    /**
     * EP: Single valid uppercase argument (should be case-insensitive).
     */
    @Test
    public void parse_caseInsensitiveTag_returnsNewtagCommand() {
        Tag expectedTag = new Tag("friend");
        List<Tag> expectedTags = new ArrayList<>();
        expectedTags.add(expectedTag);
        assertParseSuccess(parser, " t/FRIEND", new NewTagCommand(expectedTags));
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
        assertParseFailure(parser, "bride's side", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
}
