package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.RenameTagCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.tag.Tag.MAX_CHARACTER_LENGTH;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RenameTagCommand;
import seedu.address.model.tag.Tag;

public class RenameTagCommandParserTest {
    private static final String MAX_CHARACTERS_NAME = "a".repeat(MAX_CHARACTER_LENGTH);
    private static final String EXCEEDS_MAX_CHARACTERS_NAME = "a".repeat(MAX_CHARACTER_LENGTH + 1);
    private static final String NEW_NAME = "new name";
    private RenameTagCommandParser parser = new RenameTagCommandParser();

    @Test
    public void parse_typicalNewTagName_returnsRenameTagCommand() {
        Tag expectedTag = new Tag("bride's friend");
        assertParseSuccess(parser, " t/bride's friend t/" + NEW_NAME,
                new RenameTagCommand(expectedTag, NEW_NAME));
    }

    @Test
    public void parse_maxLengthNewTagName_returnsRenameTagCommand() {
        Tag expectedTag = new Tag("bride's friend");
        assertParseSuccess(parser, " t/bride's friend t/" + MAX_CHARACTERS_NAME,
                new RenameTagCommand(expectedTag, MAX_CHARACTERS_NAME));
    }

    @Test
    public void parse_tooLongNewTagName_throwsParseException() {
        assertParseFailure(parser, " t/bride's friend t/" + EXCEEDS_MAX_CHARACTERS_NAME,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyNewTagName_throwsParseException() {
        assertParseFailure(parser, " t/bride's friend t/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyExistingTagName_throwsParseException() {
        assertParseFailure(parser, " t/ t/bride's friend", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }


    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_tooManyArguments_throwsParseException() {
        assertParseFailure(parser, " t/bride's friend t/groom's friend t/colleagues",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_tooFewArguments_throwsParseException() {
        assertParseFailure(parser, " t/bride's friend",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
}
