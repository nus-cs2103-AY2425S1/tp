package careconnect.logic.parser;

import static careconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static careconnect.logic.Messages.MESSAGE_TOO_SHORT_SEARCH;
import static careconnect.logic.parser.CommandParserTestUtil.assertParseFailure;
import static careconnect.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import careconnect.logic.commands.FindCommand;
import careconnect.model.person.NameAndAddressAndTagsContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(
                parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_tooShortSearchString_throwsParseException() {
        assertParseFailure(
                parser, "a",
                String.format(MESSAGE_TOO_SHORT_SEARCH, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameAndAddressAndTagsContainsKeywordsPredicate(
                        Arrays.asList("Alice", "Bob"),
                        Arrays.asList("serangoon"),
                        Arrays.asList(new String[] {})
                ));
        assertParseSuccess(parser, " n/Alice Bob a/ serangoon", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ \n Alice \n \t Bob  \t a/    \n serangoon", expectedFindCommand);
    }
}
