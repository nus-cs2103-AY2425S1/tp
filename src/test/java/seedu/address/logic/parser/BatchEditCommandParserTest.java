package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_WIFE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_WIFE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.BatchEditCommand;
import seedu.address.model.person.PersonContainsTagsPredicate;
import seedu.address.model.tag.Tag;

class BatchEditCommandParserTest {
    private BatchEditCommandParser parser = new BatchEditCommandParser();

    @Test
    public void parse_validArgs_returnsBatchEditCommand() {
        Tag friendTag = new Tag(VALID_TAG_FRIEND);
        Tag husbandTag = new Tag(VALID_TAG_HUSBAND);
        Tag wifeTag = new Tag(VALID_TAG_WIFE);


        BatchEditCommand friendHusbandCommand = new BatchEditCommand(friendTag, husbandTag,
                new PersonContainsTagsPredicate(Set.of(friendTag)));
        BatchEditCommand friendWifeCommand = new BatchEditCommand(friendTag, wifeTag,
                new PersonContainsTagsPredicate(Set.of(friendTag)));

        // Multiple tags
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, friendHusbandCommand);
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TAG_DESC_FRIEND + TAG_DESC_WIFE, friendWifeCommand);
    }

    @Test
    public void parse_invalidArgs_returnsParseException() {
        String failureMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, BatchEditCommand.MESSAGE_USAGE);
        // Invalid prefix values
        assertParseFailure(parser, PREAMBLE_WHITESPACE
                        + NAME_DESC_BOB
                        + TAG_DESC_FRIEND,
                failureMessage
        );
        // Not two tag
        assertParseFailure(parser, PREAMBLE_WHITESPACE
                        + TAG_DESC_FRIEND,
                failureMessage
        );
        assertParseFailure(parser, PREAMBLE_WHITESPACE
                        + TAG_DESC_FRIEND
                        + TAG_DESC_HUSBAND
                        + TAG_DESC_WIFE,
                failureMessage
        );
        // Empty
        assertParseFailure(parser, PREAMBLE_WHITESPACE, failureMessage);
        assertParseFailure(parser, "", failureMessage);
    }
}
