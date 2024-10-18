package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.CompoundedPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.OrgContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new CompoundedPredicate(
                        new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")),
                        new OrgContainsKeywordsPredicate(Arrays.asList("NUS"))));
        assertParseSuccess(parser, " n/ Alice Bob o/NUS", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/ Alice \n \t Bob  \t o/ NUS", expectedFindCommand);
    }

}
