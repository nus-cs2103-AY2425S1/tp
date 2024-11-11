package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindOwnerCommand;
import seedu.address.model.owner.OwnerNameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            FindOwnerCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_tooFewArg_throwsParseException() {
        // Test with only the entity type but no keywords
        assertParseFailure(parser, "owner",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // Test with only one keyword and no entity type
        assertParseFailure(parser, "Alice",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // Test with an empty input string
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommandForOwner() {
        // no leading and trailing whitespaces for owner search
        FindOwnerCommand expectedFindCommand =
                new FindOwnerCommand(new OwnerNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "owner Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords for owner search
        assertParseSuccess(parser, "owner \n Alice \n \t Bob  \t", expectedFindCommand);
    }
}
