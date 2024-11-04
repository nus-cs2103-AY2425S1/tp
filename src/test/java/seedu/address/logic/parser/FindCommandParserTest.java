package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.PersonPredicateBuilder;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void test_parseWithEmptyArg_failure() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.NO_SEARCH_FIELDS_PROVIDED));
    }

    @Test
    public void test_parseWithValidArgsWithName_success() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new PersonPredicateBuilder().withNameKeywords(Arrays.asList("Alice", "Bob")));
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
                new FindCommand(new PersonPredicateBuilder().withClassIdKeywords(Arrays.asList("1", "2")));
        assertParseSuccess(parser, " c/1 2", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " c/ \n 1 \n \t 2  \t", expectedFindCommand);
    }

    @Test
    public void test_parseValidArgsWithNameAndClassId_success() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new PersonPredicateBuilder().withNameKeywords(Arrays.asList("Bob", "Alice"))
                        .withClassIdKeywords(Arrays.asList("1", "2")));
        assertParseSuccess(parser, " n/Bob Alice c/1 2", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ Bob \n Alice c/ \n 1 \n \t 2  \t", expectedFindCommand);
    }

    @Test
    public void test_parseValidArgsWithMonthPaid_success() {

        FindCommand expectedFindCommand =
                new FindCommand(new PersonPredicateBuilder().withMonthPaidKeywords(List.of("2022-12")));
        assertParseSuccess(parser, " m/2022-12", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " m/ \n 2022-12  \t", expectedFindCommand);
    }

    @Test
    public void test_parseValidArgsWithNotMonthPaid_success() {

        FindCommand expectedFindCommand =
                new FindCommand(new PersonPredicateBuilder().withNotMonthPaidKeywords(List.of("2022-12")));
        assertParseSuccess(parser, " !m/2022-12", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " !m/ \n 2022-12  \t", expectedFindCommand);
    }

    @Test
    public void test_nonEmptyPreambleWithValidArgsWithClassId_failure() {
        assertParseFailure(parser, "asdf n/Test",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

    }

    @Test
    public void test_parseWithTags_success() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonPredicateBuilder().withTagsKeywords(List.of("tag1", "tag2")));
        assertParseSuccess(parser, " t/tag1 tag2", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " t/ \n tag1 \n \t tag2  \t", expectedFindCommand);
    }

    @Test
    public void test_nonEmptyPreambleWithValidArgsWithTags_failure() {
        assertParseFailure(parser, "asdf t/Test",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

    }

    @Test
    public void test_parseWithNameAndTags_success() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonPredicateBuilder().withNameKeywords(Arrays.asList("Alice", "Bob"))
                        .withTagsKeywords(List.of("tag1", "tag2")));
        assertParseSuccess(parser, " n/Alice Bob t/tag1 tag2", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ \n Alice \n \t Bob  \t t/ \n tag1 \n \t tag2  \t", expectedFindCommand);
    }

    @Test
    public void test_nonEmptyPreambleWithValidArgsWithNameAndTags_failure() {
        assertParseFailure(parser, "asdf n/Test t/Test",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

    }

    @Test
    public void test_parseWithClassIdAndTags_success() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonPredicateBuilder().withClassIdKeywords(Arrays.asList("1", "2"))
                        .withTagsKeywords(List.of("tag1", "tag2")));
        assertParseSuccess(parser, " c/1 2 t/tag1 tag2", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " c/ \n 1 \n \t 2  \t t/ \n tag1 \n \t tag2  \t", expectedFindCommand);
    }

    @Test
    public void test_nonEmptyPreambleWithValidArgsWithClassIdAndTags_failure() {
        assertParseFailure(parser, "asdf c/Test t/Test",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

    }

    @Test
    public void test_parseWithMonthPaidAndTags_success() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonPredicateBuilder().withMonthPaidKeywords(List.of("2022-12"))
                        .withTagsKeywords(List.of("tag1", "tag2")));
        assertParseSuccess(parser, " m/2022-12 t/tag1 tag2", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " m/ \n 2022-12  \t t/ \n tag1 \n \t tag2  \t", expectedFindCommand);
    }

    @Test
    public void test_parseWithValidPreambleEmptyKeywords_failure() {
        assertParseFailure(parser, " n/ c/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertParseFailure(parser, " n/ m/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertParseFailure(parser, " n/ !m/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertParseFailure(parser, " n/ t/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertParseFailure(parser, " c/ m/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertParseFailure(parser, " c/ !m/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertParseFailure(parser, " c/ t/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertParseFailure(parser, " n/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertParseFailure(parser, " c/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertParseFailure(parser, " m/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertParseFailure(parser, " !m/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertParseFailure(parser, " t/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);

    }



}
