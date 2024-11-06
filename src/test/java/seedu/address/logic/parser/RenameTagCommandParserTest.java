package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BRIDES_SIDE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.RenameTagCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.tag.Tag.MAX_CHARACTER_LENGTH;
import static seedu.address.testutil.TypicalTags.BRIDES_SIDE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RenameTagCommand;
import seedu.address.model.tag.Tag;

public class RenameTagCommandParserTest {
    private static final String MAX_CHARACTERS_NAME = "a".repeat(MAX_CHARACTER_LENGTH);
    private static final String EXCEEDS_MAX_CHARACTERS_NAME = "a".repeat(MAX_CHARACTER_LENGTH + 1);
    private static final String NEW_NAME = VALID_TAG_FRIEND;
    private RenameTagCommandParser parser = new RenameTagCommandParser();

    /**
     * EP: Both valid tag names.
     */
    @Test
    public void parse_validNewTagName_returnsRenameTagCommand() {
        Tag expectedTag = BRIDES_SIDE;
        assertParseSuccess(parser, " t/" + VALID_TAG_BRIDES_SIDE + " t/" + NEW_NAME,
                new RenameTagCommand(expectedTag, NEW_NAME));
    }

    /**
     * EP: New tag name with maximum allowable length.
     */
    @Test
    public void parse_maxLengthNewTagName_returnsRenameTagCommand() {
        Tag expectedTag = BRIDES_SIDE;
        assertParseSuccess(parser, " t/" + VALID_TAG_BRIDES_SIDE + " t/" + MAX_CHARACTERS_NAME,
                new RenameTagCommand(expectedTag, MAX_CHARACTERS_NAME));
    }

    /**
     * EP: New tag name provided is too long.
     */
    @Test
    public void parse_tooLongNewTagName_throwsParseException() {
        assertParseFailure(parser, " t/" + VALID_TAG_BRIDES_SIDE + " t/" + EXCEEDS_MAX_CHARACTERS_NAME,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    /**
     * EP: Existing tag name provided is too long.
     */
    @Test
    public void parse_tooLongExistingName_throwsParseException() {
        assertParseFailure(parser, "t/" + EXCEEDS_MAX_CHARACTERS_NAME + "t/" + VALID_TAG_BRIDES_SIDE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    /**
     * EP: New tag name with invalid characters.
     */
    @Test
    public void parse_newNameInvalidCharacters_throwsParseException() {
        assertParseFailure(parser, " t/" + VALID_TAG_BRIDES_SIDE + " t/;%<>}{",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, " t/" + VALID_TAG_BRIDES_SIDE + "t/¡£™",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, " t/" + VALID_TAG_BRIDES_SIDE + " t/¶¢",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    /**
     * EP: Existing tag name with invalid characters.
     */
    @Test
    public void parse_existingTagInvalidCharacters_throwsParseException() {
        assertParseFailure(parser, " t/;%<>}{ t/" + VALID_TAG_BRIDES_SIDE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, " t/¡£™ t/" + VALID_TAG_BRIDES_SIDE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, " t/¶¢ t/" + VALID_TAG_BRIDES_SIDE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    /**
     * EP: No new tag name is provided.
     */
    @Test
    public void parse_emptyNewTagName_throwsParseException() {
        assertParseFailure(parser, " t/" + VALID_TAG_BRIDES_SIDE + "t/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    /**
     * EP: No existing tag name is provided.
     */
    @Test
    public void parse_emptyExistingTagName_throwsParseException() {
        assertParseFailure(parser, " t/ t/" + VALID_TAG_BRIDES_SIDE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    /**
     * EP: Whitespaces are given as an input.
     */
    @Test
    public void parse_spaceAsArgs_throwsParseException() {
        assertParseFailure(parser, " t/   t/" + VALID_TAG_BRIDES_SIDE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    /**
     * EP: Empty String is given as an input.
     */
    @Test
    public void parse_emptyInput_throwsParseException() {
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    /**
     * EP: Too many inputs are given.
     */
    @Test
    public void parse_tooManyArguments_throwsParseException() {
        assertParseFailure(parser, " t/" + VALID_TAG_BRIDES_SIDE + " t/" + NEW_NAME + " t/" + VALID_TAG_BRIDES_SIDE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    /**
     * EP: Only one input is given.
     */
    @Test
    public void parse_tooFewArguments_throwsParseException() {
        assertParseFailure(parser, " t/" + VALID_TAG_BRIDES_SIDE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
}
