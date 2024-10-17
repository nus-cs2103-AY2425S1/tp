package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_NEW_TAG;
import static seedu.address.logic.commands.CommandTestUtil.TAG_NEW_TAG;
import static seedu.address.logic.commands.CommandTestUtil.TAG_OLD_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RenameTagCommand;
import seedu.address.model.tag.Tag;

public class RenameTagCommandParserTest {

    private RenameTagParser parser = new RenameTagParser();

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = TAG_OLD_TAG + TAG_NEW_TAG;

        RenameTagCommand expectedCommand = new RenameTagCommand("friend", "husband");

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameTagCommand.MESSAGE_USAGE);

        // missing new tag
        assertParseFailure(parser, TAG_OLD_TAG, expectedMessage);

        // missing old tag
        assertParseFailure(parser, TAG_NEW_TAG, expectedMessage);

        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid tag
        assertParseFailure(parser, TAG_OLD_TAG + INVALID_TAG_NEW_TAG, Tag.MESSAGE_CONSTRAINTS);
    }
}
