package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.BatchDeleteCommand;
import seedu.address.model.person.predicates.PersonContainsTagsPredicate;
import seedu.address.model.tag.Tag;


class BatchDeleteCommandParserTest {
    private BatchDeleteCommandParser parser = new BatchDeleteCommandParser();
    @Test
    public void parse_validArgs_returnsBatchDeleteCommand() {
        Set<Tag> tagSet = Set.of(new Tag(VALID_TAG_FRIEND), new Tag(VALID_TAG_HUSBAND));
        Set<Tag> tagSetV2 = Set.of(new Tag(VALID_TAG_FRIEND));
        Set<Tag> tagSetV3 = Set.of();

        BatchDeleteCommand batchDeleteCommand = new BatchDeleteCommand(tagSet,
                new PersonContainsTagsPredicate(tagSet));
        BatchDeleteCommand batchDeleteCommandV2 = new BatchDeleteCommand(tagSetV2,
                new PersonContainsTagsPredicate(tagSetV2));
        BatchDeleteCommand batchDeleteCommandV3 = new BatchDeleteCommand(tagSetV3,
                new PersonContainsTagsPredicate(tagSetV3));

        // Multiple tags
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, batchDeleteCommand);
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TAG_DESC_FRIEND, batchDeleteCommandV2);

        // Duplicate tags ignored
        assertParseSuccess(parser, PREAMBLE_WHITESPACE
                + TAG_DESC_FRIEND
                + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND,
                batchDeleteCommand
        );
    }

    @Test
    public void parse_invalidArgs_returnsParseException() {
        String failureMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, BatchDeleteCommand.MESSAGE_USAGE);
        // Invalid prefix values
        assertParseFailure(parser, PREAMBLE_WHITESPACE
                + NAME_DESC_BOB
                + TAG_DESC_FRIEND
                + TAG_DESC_HUSBAND
                + PHONE_DESC_AMY
                + NAME_DESC_AMY,
                failureMessage
        );
        // Empty
        assertParseFailure(parser, PREAMBLE_WHITESPACE, failureMessage);
        assertParseFailure(parser, "", failureMessage);
    }
}
