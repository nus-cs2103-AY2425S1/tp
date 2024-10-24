package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // Expected search criteria with "Alice" as the name and "Bob" as the address
        Map<String, String> searchCriteria = Map.of(
                "name", "Alice",
                "address", "Serangoon"
        );

        FindCommand expectedFindCommand = new FindCommand(new PersonContainsKeywordsPredicate(searchCriteria));

        // Test with no leading and trailing whitespaces
        assertParseSuccess(parser, " n/ Alice a/ Serangoon", expectedFindCommand);

        // Test with multiple whitespaces between keywords and flags
        assertParseSuccess(parser, " \n n/ Alice \n \t a/ Serangoon  \t", expectedFindCommand);
    }
}
