package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.DeleteTagCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TypicalTags;

public class DeleteTagCommandParserTest {

    private DeleteTagCommandParser parser = new DeleteTagCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteTagCommand() {
        Tag expectedTag = TypicalTags.VALID_TAG_BRIDES_FRIEND;
        assertParseSuccess(parser, "bride's friend", new DeleteTagCommand(expectedTag));
    }

    @Test
    public void parse_leadingAndTrailingSpaces_returnsDeleteTagCommand() {
        Tag expectedTag = TypicalTags.VALID_TAG_BRIDES_FRIEND;
        assertParseSuccess(parser, "   bride's friend   ", new DeleteTagCommand(expectedTag));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
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
        assertParseSuccess(parser, "  FRIEND  ", new DeleteTagCommand(expectedTag));
    }
}
