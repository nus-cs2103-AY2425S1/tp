package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.KEYWORD_ADDRESS_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.KEYWORD_EMAIL_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.KEYWORD_INVALID_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.KEYWORD_NAME_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.KEYWORD_PHONE_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORD;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.client.EmailContainsKeywordsPredicate;
import seedu.address.model.client.NameContainsKeywordsPredicate;
import seedu.address.model.client.PhoneContainsKeywordsPredicate;
import seedu.address.model.client.RentalInformationContainsKeywordsPredicate;

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
        List<String> keywords = new ArrayList<>();
        keywords.add(("Alice"));
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(keywords),
                        new PhoneContainsKeywordsPredicate(keywords),
                        new EmailContainsKeywordsPredicate(keywords),
                        new RentalInformationContainsKeywordsPredicate(keywords));

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + KEYWORD_NAME_DESC_ALICE, expectedFindCommand);
    }

    @Test
    public void parse_validPhone_returnsFindCommand() {
        List<String> keywords = new ArrayList<>();
        keywords.add(("98761111"));
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(keywords),
                        new PhoneContainsKeywordsPredicate(keywords),
                        new EmailContainsKeywordsPredicate(keywords),
                        new RentalInformationContainsKeywordsPredicate(keywords));
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + KEYWORD_PHONE_DESC_ALICE, expectedFindCommand);
    }

    @Test
    public void parse_validEmail_returnsFindCommand() {
        List<String> keywords = new ArrayList<>();
        keywords.add(("alice@example.com"));
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(keywords),
                        new PhoneContainsKeywordsPredicate(keywords),
                        new EmailContainsKeywordsPredicate(keywords),
                        new RentalInformationContainsKeywordsPredicate(keywords));
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + KEYWORD_EMAIL_DESC_ALICE, expectedFindCommand);
    }

    @Test
    public void parse_validRentalInformation_returnsFindCommand() {
        List<String> keywords = new ArrayList<>();
        keywords.add(("Ave 2 Yishun"));
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(keywords),
                        new PhoneContainsKeywordsPredicate(keywords),
                        new EmailContainsKeywordsPredicate(keywords),
                        new RentalInformationContainsKeywordsPredicate(keywords));
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + KEYWORD_ADDRESS_DESC_ONE, expectedFindCommand);
    }

    @Test
    public void parse_invalidKeyword_throwsParseException() {
        assertParseFailure(parser, PREAMBLE_WHITESPACE + KEYWORD_INVALID_EMPTY,
                String.format(FindCommandParser.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_missingParts_failure() {
        // no prefix
        assertParseFailure(parser, VALID_NAME_ALICE, MESSAGE_INVALID_FORMAT);

        // no field
        assertParseFailure(parser, String.valueOf(PREFIX_KEYWORD), MESSAGE_INVALID_FORMAT);
    }
}
