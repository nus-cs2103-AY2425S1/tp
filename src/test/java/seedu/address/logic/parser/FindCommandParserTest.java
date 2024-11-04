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
    public void test_nonEmptyPreambleWithValidArgsWithEmail_failure() {
        assertParseFailure(parser, "asdf e/Test",
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
    public void test_parseWithNameAndClassIdAndTags_success() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonPredicateBuilder().withNameKeywords(Arrays.asList("Alice", "Ben"))
                        .withClassIdKeywords(Arrays.asList("1", "2"))
                        .withTagsKeywords(List.of("tag1", "tag2")));
        assertParseSuccess(parser, " n/Alice Ben c/1 2 t/tag1 tag2", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ \n Alice \n \t Ben c/ \n 1 \n \t 2  \t t/ \n tag1 \n \t tag2  \t",
                expectedFindCommand);
    }

    @Test
    public void test_nonEmptyPreambleWithValidArgsWithNameAndClassIdAndTags_failure() {
        assertParseFailure(parser, "asdf n/Test c/Test t/Test",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void test_nonEmptyPreambleWithValidArgsWithClassIdAndTags_failure() {
        assertParseFailure(parser, "asdf c/Test t/Test",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

    }

    @Test
    public void test_nonEmptyPreambleWithValidArgsWithAddress_failure() {
        assertParseFailure(parser, "asdf a/Test",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

    }

    @Test
    public void test_nonEmptyPreambleWithValidArgsWithPhone_failure() {
        assertParseFailure(parser, "asdf p/Test",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

    }

    @Test
    public void test_nonEmptyPreambleWithValidArgsWithFees_failure() {
        assertParseFailure(parser, "asdf f/Test",
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
    public void test_parseWithValidArgsWithEmail_success() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonPredicateBuilder()
                        .withEmailKeywords(List.of("alice@example.com", "bob@example.com")));
        assertParseSuccess(parser, " e/alice@example.com bob@example.com", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " e/ \n alice@example.com \n \t bob@example.com  \t", expectedFindCommand);
    }

    @Test
    public void test_parseWithValidArgsWithAddress_success() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonPredicateBuilder().withAddressKeywords(List.of("Greenwood", "Str")));
        assertParseSuccess(parser, " a/Greenwood Str", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " a/ \n Greenwood Str  \t", expectedFindCommand);
    }

    @Test
    public void test_parseWithValidArgsWithPhone_success() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonPredicateBuilder().withPhoneKeywords(List.of("12345678", "87654321")));
        assertParseSuccess(parser, " p/12345678 87654321", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " p/ \n 12345678 \n \t 87654321  \t", expectedFindCommand);
    }

    @Test
    public void test_parseWithValidArgsWithFees_success() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonPredicateBuilder().withFeesKeywords(List.of("100", "200")));
        assertParseSuccess(parser, " f/100 200", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " f/ \n 100 \n \t 200  \t", expectedFindCommand);
    }

    @Test
    public void test_parseWithNameAndEmail_success() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonPredicateBuilder().withNameKeywords(Arrays.asList("Alice", "Bob"))
                        .withEmailKeywords(Arrays.asList("alice@example.com", "bob@example.com")));
        assertParseSuccess(parser, " n/Alice Bob e/alice@example.com bob@example.com", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ \n Alice \n \t Bob  \t "
                + "e/ \n alice@example.com \n \t bob@example.com  \t", expectedFindCommand);
    }

    @Test
    public void test_parseWithClassIdAndPhone_success() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonPredicateBuilder().withClassIdKeywords(Arrays.asList("1", "2"))
                        .withPhoneKeywords(Arrays.asList("12345678", "87654321")));
        assertParseSuccess(parser, " c/1 2 p/12345678 87654321", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " c/ \n 1 \n \t 2  \t p/ \n 12345678 \n \t 87654321  \t", expectedFindCommand);
    }

    @Test
    public void test_parseWithMonthPaidAndFees_success() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonPredicateBuilder().withMonthPaidKeywords(List.of("2022-12"))
                        .withFeesKeywords(Arrays.asList("100", "200")));
        assertParseSuccess(parser, " m/2022-12 f/100 200", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " m/ \n 2022-12  \t f/ \n 100 \n \t 200  \t", expectedFindCommand);
    }

    @Test
    public void test_parseWithAllPrefixes_success() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonPredicateBuilder()
                        .withNameKeywords(Arrays.asList("Alice", "Bob"))
                        .withClassIdKeywords(Arrays.asList("1", "2"))
                        .withMonthPaidKeywords(List.of("2022-12"))
                        .withNotMonthPaidKeywords(List.of("2022-11"))
                        .withTagsKeywords(List.of("tag1", "tag2"))
                        .withEmailKeywords(Arrays.asList("alice@example.com", "bob@example.com"))
                        .withPhoneKeywords(Arrays.asList("12345678", "87654321"))
                        .withFeesKeywords(List.of("100", "200")));
        assertParseSuccess(parser, " n/Alice Bob c/1 2 m/2022-12 !m/2022-11 t/tag1 tag2 "
                + "e/alice@example.com bob@example.com p/12345678 87654321 "
                + "f/100 200", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ \n Alice \n \t Bob c/ \n 1 \n \t "
                + "2  \t m/ \n 2022-12  \t !m/ \n 2022-11  \t t/ \n tag1 \n \t tag2  \t e/ \n "
                + "alice@example.com \n \t bob@example.com  \t p/ \n "
                + "12345678 \n \t 87654321  \t f/ \n 100 \n \t 200  \t", expectedFindCommand);
    }

    @Test
    public void test_parseWithValidPreambleEmptyKeywords_failure() {
        assertParseFailure(parser, " n/ c/ m/ !m/ t/ e/ p/ f/",
                FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
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
        assertParseFailure(parser, " e/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertParseFailure(parser, " p/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertParseFailure(parser, " f/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);


    }



}
