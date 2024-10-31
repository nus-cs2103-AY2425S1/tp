package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.ClassIdContainsKeywordsPredicate;
import seedu.address.model.person.NameAndClassIdContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void test_parseWithEmptyArg_failure() {
        assertParseFailure(parser, "     ", String.format(FindCommand.NO_SEARCH_FIELDS_PROVIDED));
    }

    @Test
    public void test_parseWithValidArgsWithName_success() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, " n/Alice Bob", expectedFindCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ \n Alice \n \t Bob  \t", expectedFindCommand);
    }


    @Test
    public void test_parseWithInvalidArgsWithName_failure() {
        // missing n/ prefix
        assertParseFailure(parser, " Alice Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void test_parseWithValidArgsWithClassId_success() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new ClassIdContainsKeywordsPredicate(Arrays.asList("1", "2")));
        assertParseSuccess(parser, " c/1 2", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " c/ \n 1 \n \t 2  \t", expectedFindCommand);
    }

    @Test
    public void test_parseValidArgsWithNameAndClassId_success() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameAndClassIdContainsKeywordsPredicate(Arrays.asList("Bob", "Alice"),
                        Arrays.asList("1", "2")));
        assertParseSuccess(parser, " n/Bob Alice c/1 2", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ Bob \n Alice c/ \n 1 \n \t 2  \t", expectedFindCommand);
    }

    @Test
    public void test_nonEmptyPreambleWithValidArgsWithClassId_failure() {
        assertParseFailure(parser, "asdf n/Test",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

    }

    @Test
    public void test_parseWithValidPreambleEmptyKeywords_failure() {
        assertParseFailure(parser, " n/ c/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertParseFailure(parser, " n/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertParseFailure(parser, " c/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertParseFailure(parser, " m/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertParseFailure(parser, " !m/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
    }



}
