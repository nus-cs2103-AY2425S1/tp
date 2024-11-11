package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_EMAIL_PREDICATE;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_NAME_PREDICATE;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_PHONE_PREDICATE;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_RENTAL_PREDICATE;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_TAGS_PREDICATE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_KEYWORD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.KEYWORD_ADDRESS_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.KEYWORD_EMPTY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_KEYWORD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_KEYWORD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORD;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.client.EmailContainsKeywordsPredicate;
import seedu.address.model.client.NameContainsKeywordsPredicate;
import seedu.address.model.client.PhoneContainsKeywordsPredicate;
import seedu.address.model.client.RentalInformationContainsKeywordsPredicate;
import seedu.address.model.tag.TagsContainsKeywordsPredicate;

public class FindCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validName_returnsFindCommand() {
        List<String> names = List.of(VALID_NAME_AMY);
        FindCommand expectedFindCommand = new FindCommand(
                new NameContainsKeywordsPredicate(names),
                EMPTY_PHONE_PREDICATE,
                EMPTY_EMAIL_PREDICATE,
                EMPTY_TAGS_PREDICATE,
                EMPTY_RENTAL_PREDICATE);

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_AMY, expectedFindCommand);
    }

    @Test
    public void parse_validPhone_returnsFindCommand() {
        List<String> phones = List.of(VALID_PHONE_AMY);
        FindCommand expectedFindCommand = new FindCommand(
                EMPTY_NAME_PREDICATE,
                new PhoneContainsKeywordsPredicate(phones),
                EMPTY_EMAIL_PREDICATE,
                EMPTY_TAGS_PREDICATE,
                EMPTY_RENTAL_PREDICATE);

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PHONE_DESC_AMY, expectedFindCommand);
    }

    @Test
    public void parse_validEmail_returnsFindCommand() {
        List<String> emails = List.of(VALID_EMAIL_AMY);
        FindCommand expectedFindCommand = new FindCommand(
                EMPTY_NAME_PREDICATE,
                EMPTY_PHONE_PREDICATE,
                new EmailContainsKeywordsPredicate(emails),
                EMPTY_TAGS_PREDICATE,
                EMPTY_RENTAL_PREDICATE);

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EMAIL_DESC_AMY, expectedFindCommand);
    }

    @Test
    public void parse_validTags_returnsFindCommand() {
        List<String> tags = List.of(VALID_TAG_FRIEND, VALID_TAG_HUSBAND);
        FindCommand expectedFindCommand = new FindCommand(
                EMPTY_NAME_PREDICATE,
                EMPTY_PHONE_PREDICATE,
                EMPTY_EMAIL_PREDICATE,
                new TagsContainsKeywordsPredicate(tags),
                EMPTY_RENTAL_PREDICATE);

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, expectedFindCommand);
    }

    @Test
    public void parse_multipleFields_returnsFindCommand() {
        List<String> names = List.of(VALID_NAME_AMY);
        List<String> phones = List.of(VALID_PHONE_AMY);
        List<String> emails = List.of(VALID_EMAIL_AMY);
        List<String> tags = List.of(VALID_TAG_FRIEND, VALID_TAG_HUSBAND);
        FindCommand expectedFindCommand = new FindCommand(
                new NameContainsKeywordsPredicate(names),
                new PhoneContainsKeywordsPredicate(phones),
                new EmailContainsKeywordsPredicate(emails),
                new TagsContainsKeywordsPredicate(tags),
                EMPTY_RENTAL_PREDICATE);
        String userInput = PREAMBLE_WHITESPACE + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
                + TAG_DESC_HUSBAND;

        assertParseSuccess(parser, userInput, expectedFindCommand);
    }

    @Test
    public void parse_validKeyword_returnsFindCommand() {
        List<String> keywords = List.of(VALID_KEYWORD);
        FindCommand expectedFindCommand = new FindCommand(
                new NameContainsKeywordsPredicate(keywords),
                new PhoneContainsKeywordsPredicate(keywords),
                new EmailContainsKeywordsPredicate(keywords),
                new TagsContainsKeywordsPredicate(keywords),
                new RentalInformationContainsKeywordsPredicate(keywords));

        assertParseSuccess(parser, VALID_KEYWORD_DESC, expectedFindCommand);
    }

    @Test
    public void parse_validRentalInformation_returnsFindCommand() {
        List<String> keywords = List.of("Ave 2 Yishun");
        FindCommand expectedFindCommand = new FindCommand(
                new NameContainsKeywordsPredicate(keywords),
                new PhoneContainsKeywordsPredicate(keywords),
                new EmailContainsKeywordsPredicate(keywords),
                new TagsContainsKeywordsPredicate(keywords),
                new RentalInformationContainsKeywordsPredicate(keywords));

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + KEYWORD_ADDRESS_DESC_ONE, expectedFindCommand);
    }

    @Test
    public void parse_invalidKeyword_throwsParseException() {
        assertParseFailure(parser, INVALID_KEYWORD_DESC, String.format(FindCommandParser.MESSAGE_INVALID_KEYWORD));
    }

    @Test
    public void parse_emptyKeyword_throwsParseException() {
        assertParseFailure(parser, KEYWORD_EMPTY_DESC, String.format(FindCommandParser.MESSAGE_INVALID_KEYWORD));
    }

    @Test
    public void parse_missingParts_failure() {
        // no prefix
        assertParseFailure(parser, VALID_NAME_ALICE, MESSAGE_INVALID_FORMAT);

        // no field
        assertParseFailure(parser, String.valueOf(PREFIX_KEYWORD), MESSAGE_INVALID_FORMAT);
    }
}
