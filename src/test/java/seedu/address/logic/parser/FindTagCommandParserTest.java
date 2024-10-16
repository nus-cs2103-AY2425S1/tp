package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.findcommand.FindTagCommand;
import seedu.address.model.person.TagContainsKeywordsPredicate;

public class FindTagCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // no leading and trailing whitespaces
        FindTagCommand expectedFindTagCommand =
                new FindTagCommand(new TagContainsKeywordsPredicate(List.of("friends")));
        assertParseSuccess(parser, "friends", expectedFindTagCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "friends", expectedFindTagCommand);
    }

}
