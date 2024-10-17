package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.Email;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.PersonContainsKeywordsPredicate;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

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
                new FindCommand(new PersonContainsKeywordsPredicate(null, null, null, null, "bza", null, null));
        assertParseSuccess(parser, " m/bza", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n m/bza \t", expectedFindCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " Alice Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_incompleteArgs_throwsParseException() {
        assertParseFailure(parser, " n/  ", FindCommand.MESSAGE_INCOMPLETE);
    }

    @Test
    public void parse_invalidNameWithSpecialCharacter_throwsParseException() {
        assertParseFailure(parser, " n/jo$n do3", Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validName_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate("john do3", null, null, null, null, null, null));
        assertParseSuccess(parser, " n/john do3", expectedFindCommand);
    }

    @Test
    public void parse_invalidPhoneNumberLessThanThreeDigits_throwsParseException() {
        assertParseFailure(parser, " p/12", Phone.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPhoneNumberWithAlphabets_throwsParseException() {
        assertParseFailure(parser, " p/hello", Phone.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPhoneNumberWithOtherCharacters_throwsParseException() {
        assertParseFailure(parser, " p/:", Phone.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validPhoneNumberWithThreeDigits_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(null, "123", null, null, null, null, null));
        assertParseSuccess(parser, " p/123", expectedFindCommand);
    }

    @Test
    public void parse_validPhoneNumberWithEightDigits_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(null, "12345678", null, null, null, null, null));
        assertParseSuccess(parser, " p/12345678", expectedFindCommand);
    }

    @Test
    public void parse_invalidEmailWithSpecialCharactersInLocalPart_throwsParseException() {
        assertParseFailure(parser, " e/+alice@example.com", Email.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidEmailWithSpecialCharactersInDomainName_throwsParseException() {
        assertParseFailure(parser, " e/alice@-example.com", Email.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidEmailWithSpecialCharactersInDomainLabel_throwsParseException() {
        assertParseFailure(parser, " e/alice@example.c!om", Email.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validEmail_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(null, null, "alice@example.com", null, null, null,
                        null));
        assertParseSuccess(parser, " e/alice@example.com", expectedFindCommand);
    }

    @Test
    public void parse_invalidRole_throwsParseException() {
        assertParseFailure(parser, " r/something", Role.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validRoleBrudder_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(null, null, null, "brUdder", null, null, null));
        assertParseSuccess(parser, " r/brUdder", expectedFindCommand);
    }

    @Test
    public void parse_validRoleMudder_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(null, null, null, "mUdder", null, null, null));
        assertParseSuccess(parser, " r/mUdder", expectedFindCommand);
    }

    @Test
    public void parse_invalidMajor_throwsParseException() {
        assertParseFailure(parser, " m/cegg", Major.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validMajorCs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(null, null, null, null, "cs", null, null));
        assertParseSuccess(parser, " m/cs", expectedFindCommand);
    }

    @Test
    public void parse_validMajorBza_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(null, null, null, null, "bza", null, null));
        assertParseSuccess(parser, " m/bza", expectedFindCommand);
    }

    @Test
    public void parse_validMajorCeg_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(null, null, null, null, "ceg", null, null));
        assertParseSuccess(parser, " m/ceg", expectedFindCommand);
    }

    @Test
    public void parse_validMajorIsys_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(null, null, null, null, "isys", null, null));
        assertParseSuccess(parser, " m/isys", expectedFindCommand);
    }

    @Test
    public void parse_validMajorIsec_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(null, null, null, null, "isec", null, null));
        assertParseSuccess(parser, " m/isec", expectedFindCommand);
    }

    @Test
    public void parse_validAddress_returnsFindCommand() {
        // Address cannot be blank, and this case is handled by test checking for incomplete parameters
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(null, null, null, null, null, "anything works",
                        null));
        assertParseSuccess(parser, " a/anything works", expectedFindCommand);
    }

    @Test
    public void parse_invalidTagWithSpecialCharacter_throwsParseException() {
        assertParseFailure(parser, " t/@funny", Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validTag_returnsFindCommand() {
        List<String> expectedTags = List.of("friends", "owesMoney");
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(null, null, null, null, null, null, expectedTags));
        assertParseSuccess(parser, " t/friends t/owesMoney", expectedFindCommand);
        assertParseSuccess(parser, " t/friends \n t/owesMoney", expectedFindCommand);
    }

    @Test
    public void parse_multipleValidArgs_returnsFindCommand() {
        List<String> expectedTags = List.of("friends", "owesMoney");
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate("Alice", "12345678", "alice@example.com", "brUdder",
                        "cs", "NUS COM2",
                        expectedTags));
        assertParseSuccess(parser, " n/Alice"
                + " p/12345678"
                + " e/alice@example.com"
                + " r/brUdder"
                + " m/cs"
                + " a/NUS COM2"
                + " t/friends"
                + " t/owesMoney", expectedFindCommand);
    }
}
