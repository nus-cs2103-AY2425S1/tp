package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_FILTER_CRITERIA;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FilterTagCommand;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

public class FilterCommandParserTest {
    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_validargs_returnsFilterCommand() {
        Tag tag = new Tag("black");
        assertParseSuccess(parser, "t/black", new FilterTagCommand(new TagContainsKeywordsPredicate(tag)));
    }

    @Test
    public void parse_invalidFilterField_throwsParseException() {
        assertParseFailure(parser, "name", MESSAGE_INVALID_FILTER_CRITERIA);
    }

    @Test
    public void parse_noFilterField_throwsParseException() {
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }
}
