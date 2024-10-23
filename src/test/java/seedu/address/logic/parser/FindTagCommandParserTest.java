package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindTagCommand;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

public class FindTagCommandParserTest {

    private FindTagCommandParser parser = new FindTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindTagCommand() {
        Set<Tag> expectedTags = new HashSet<>(Arrays.asList(new Tag("friend"), new Tag("family")));

        FindTagCommand expectedFindTagCommand = new FindTagCommand(new TagContainsKeywordsPredicate(expectedTags));

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "friend family", expectedFindTagCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n friend \n \t family  \t", expectedFindTagCommand);
    }
}
