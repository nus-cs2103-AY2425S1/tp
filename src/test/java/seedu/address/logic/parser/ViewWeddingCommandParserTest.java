package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewWeddingCommand;
import seedu.address.model.person.TagContainsKeywordsPredicate;

public class ViewWeddingCommandParserTest {

    private final ViewWeddingCommandParser parser = new ViewWeddingCommandParser();

    @Test
    public void parse_validArgs_returnsViewWeddingCommand() {
        String input = "viAlice&Bob";
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Arrays.asList("Alice&Bob"));
        ViewWeddingCommand expectedCommand = new ViewWeddingCommand(predicate);
        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        // Testing for empty arguments where the command should fail
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewWeddingCommand.MESSAGE_USAGE));
    }
}
