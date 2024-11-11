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
        assertValidArgsSuccess(" n/Alice Bob",
                new PersonPredicateBuilder().withNameKeywords(Arrays.asList("Alice", "Bob")));
    }

    @Test
    public void test_parseWithInvalidArgsWithName_failure() {
        assertInvalidArgsFailure(" Alice Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void test_parseWithValidArgsWithClassId_success() {
        assertValidArgsSuccess(" c/1 2",
                new PersonPredicateBuilder().withClassIdKeywords(Arrays.asList("1", "2")));
    }

    @Test
    public void test_parseValidArgsWithNameAndClassId_success() {
        assertValidArgsSuccess(" n/Bob Alice c/1 2",
                new PersonPredicateBuilder().withNameKeywords(Arrays.asList("Bob", "Alice"))
                .withClassIdKeywords(Arrays.asList("1", "2")));
    }

    @Test
    public void test_parseValidArgsWithMonthPaid_success() {
        assertValidArgsSuccess(" m/2022-12",
                new PersonPredicateBuilder().withMonthPaidKeywords(List.of("2022-12")));
    }

    @Test
    public void test_parseValidArgsWithNotMonthPaid_success() {
        assertValidArgsSuccess(" !m/2022-12",
                new PersonPredicateBuilder().withNotMonthPaidKeywords(List.of("2022-12")));
    }

    @Test
    public void test_nonEmptyPreambleWithValidArgsWithClassId_failure() {
        assertInvalidArgsFailure("asdf n/Test",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void test_parseWithTags_success() {
        assertValidArgsSuccess(" t/tag1 tag2",
                new PersonPredicateBuilder().withTagsKeywords(List.of("tag1", "tag2")));
    }

    @Test
    public void test_nonEmptyPreambleWithValidArgsWithTags_failure() {
        assertInvalidArgsFailure("asdf t/Test",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void test_nonEmptyPreambleWithValidArgsWithEmail_failure() {
        assertInvalidArgsFailure("asdf e/Test",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void test_parseWithNameAndTags_success() {
        assertValidArgsSuccess(" n/Alice Bob t/tag1 tag2",
                new PersonPredicateBuilder().withNameKeywords(Arrays.asList("Alice", "Bob"))
                .withTagsKeywords(List.of("tag1", "tag2")));
    }

    @Test
    public void test_nonEmptyPreambleWithValidArgsWithNameAndTags_failure() {
        assertInvalidArgsFailure("asdf n/Test t/Test",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void test_parseWithClassIdAndTags_success() {
        assertValidArgsSuccess(" c/1 2 t/tag1 tag2",
                new PersonPredicateBuilder().withClassIdKeywords(Arrays.asList("1", "2"))
                .withTagsKeywords(List.of("tag1", "tag2")));
    }

    @Test
    public void test_parseWithNameAndClassIdAndTags_success() {
        assertValidArgsSuccess(" n/Alice Ben c/1 2 t/tag1 tag2",
                new PersonPredicateBuilder().withNameKeywords(Arrays.asList("Alice", "Ben"))
                .withClassIdKeywords(Arrays.asList("1", "2"))
                .withTagsKeywords(List.of("tag1", "tag2")));
    }

    @Test
    public void test_nonEmptyPreambleWithValidArgsWithNameAndClassIdAndTags_failure() {
        assertInvalidArgsFailure("asdf n/Test c/Test t/Test",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void test_nonEmptyPreambleWithValidArgsWithClassIdAndTags_failure() {
        assertInvalidArgsFailure("asdf c/Test t/Test",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void test_nonEmptyPreambleWithValidArgsWithAddress_failure() {
        assertInvalidArgsFailure("asdf a/Test",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void test_nonEmptyPreambleWithValidArgsWithPhone_failure() {
        assertInvalidArgsFailure("asdf p/Test",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void test_nonEmptyPreambleWithValidArgsWithFees_failure() {
        assertInvalidArgsFailure("asdf f/Test",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void test_parseWithMonthPaidAndTags_success() {
        assertValidArgsSuccess(" m/2022-12 t/tag1 tag2",
                new PersonPredicateBuilder().withMonthPaidKeywords(List.of("2022-12"))
                .withTagsKeywords(List.of("tag1", "tag2")));
    }

    @Test
    public void test_parseWithValidArgsWithEmail_success() {
        assertValidArgsSuccess(" e/alice@example.com bob@example.com",
                new PersonPredicateBuilder()
                .withEmailKeywords(List.of("alice@example.com", "bob@example.com")));
    }

    @Test
    public void test_parseWithValidArgsWithAddress_success() {
        assertValidArgsSuccess(" a/Greenwood Str",
                new PersonPredicateBuilder().withAddressKeywords(List.of("Greenwood", "Str")));
    }

    @Test
    public void test_parseWithValidArgsWithPhone_success() {
        assertValidArgsSuccess(" p/12345678 87654321",
                new PersonPredicateBuilder().withPhoneKeywords(List.of("12345678", "87654321")));
    }

    @Test
    public void test_parseWithValidArgsWithFees_success() {
        assertValidArgsSuccess(" f/100 200",
                new PersonPredicateBuilder().withFeesKeywords(List.of("100", "200")));
    }

    @Test
    public void test_parseWithNameAndEmail_success() {
        assertValidArgsSuccess(" n/Alice Bob e/alice@example.com bob@example.com",
                new PersonPredicateBuilder().withNameKeywords(Arrays.asList("Alice", "Bob"))
                .withEmailKeywords(Arrays.asList("alice@example.com", "bob@example.com")));
    }

    @Test
    public void test_parseWithClassIdAndPhone_success() {
        assertValidArgsSuccess(" c/1 2 p/12345678 87654321",
                new PersonPredicateBuilder().withClassIdKeywords(Arrays.asList("1", "2"))
                .withPhoneKeywords(Arrays.asList("12345678", "87654321")));
    }

    @Test
    public void test_parseWithMonthPaidAndFees_success() {
        assertValidArgsSuccess(" m/2022-12 f/100 200",
                new PersonPredicateBuilder().withMonthPaidKeywords(List.of("2022-12"))
                .withFeesKeywords(Arrays.asList("100", "200")));
    }

    @Test
    public void test_parseWithAllPrefixes_success() {
        assertValidArgsSuccess(" n/Alice Bob c/1 2 m/2022-12 !m/2022-11 t/tag1 tag2 "
                        + "e/alice@example.com bob@example.com p/12345678 87654321 f/100 200",
                new PersonPredicateBuilder().withNameKeywords(Arrays.asList("Alice", "Bob"))
                        .withClassIdKeywords(Arrays.asList("1", "2"))
                        .withMonthPaidKeywords(List.of("2022-12"))
                        .withNotMonthPaidKeywords(List.of("2022-11"))
                        .withTagsKeywords(List.of("tag1", "tag2"))
                        .withEmailKeywords(Arrays.asList("alice@example.com", "bob@example.com"))
                        .withPhoneKeywords(Arrays.asList("12345678", "87654321"))
                        .withFeesKeywords(List.of("100", "200")));
    }

    @Test
    public void test_parseWithValidPreambleEmptyKeywords_failure() {
        assertInvalidArgsFailure(" n/ c/ m/ !m/ t/ e/ p/ f/",
                FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertInvalidArgsFailure(" n/ c/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertInvalidArgsFailure(" n/ m/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertInvalidArgsFailure(" n/ !m/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertInvalidArgsFailure(" n/ t/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertInvalidArgsFailure(" c/ m/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertInvalidArgsFailure(" c/ !m/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertInvalidArgsFailure(" c/ t/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertInvalidArgsFailure(" n/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertInvalidArgsFailure(" c/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertInvalidArgsFailure(" m/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertInvalidArgsFailure(" !m/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertInvalidArgsFailure(" t/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertInvalidArgsFailure(" e/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertInvalidArgsFailure(" p/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        assertInvalidArgsFailure(" f/", FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
    }

    private void assertValidArgsSuccess(String input, PersonPredicateBuilder predicateBuilder) {
        FindCommand expectedFindCommand = new FindCommand(predicateBuilder);
        assertParseSuccess(parser, input, expectedFindCommand);
        assertParseSuccess(parser, " " + input.replace(" ", " \n \t ") + " ", expectedFindCommand);
    }

    private void assertInvalidArgsFailure(String input, String expectedMessage) {
        assertParseFailure(parser, input, expectedMessage);
    }
}
