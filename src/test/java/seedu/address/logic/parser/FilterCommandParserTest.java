package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "       ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValues_failure() {
        // invalid tag
        assertParseFailure(parser, INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TAG_DESC_HUSBAND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));


    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // single tag
        Set<Tag> firstSet = new HashSet<>();
        firstSet.add(new Tag(VALID_TAG_HUSBAND));

        FilterCommand expectedFilterCommand = new FilterCommand(new TagContainsKeywordsPredicate(firstSet));
        assertParseSuccess(parser, TAG_DESC_HUSBAND, expectedFilterCommand);

        // multiple tags
        Set<Tag> secondSet = new HashSet<>();
        secondSet.add(new Tag(VALID_TAG_HUSBAND));
        secondSet.add(new Tag(VALID_TAG_FRIEND));

        FilterCommand secondExpectedCommand = new FilterCommand(new TagContainsKeywordsPredicate(secondSet));
        assertParseSuccess(parser, TAG_DESC_HUSBAND + TAG_DESC_FRIEND, secondExpectedCommand);
    }


}
