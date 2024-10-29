package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.criteria.EmailSearchCriteria;
import seedu.address.logic.parser.criteria.NameSearchCriteria;
import seedu.address.logic.parser.criteria.PhoneSearchCriteria;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ContainsKeywordsPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                "Please enter at least one keyword!\n" + FindCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        List<String> userInput = Arrays.asList("Alice", "Bob");
        // ArgumentMultimap mapForUserInput = new ArgumentMultimap();
        // mapForUserInput.put(new Prefix(""), "");
        // userInput.stream().forEach(input -> mapForUserInput.put(PREFIX_NAME, input));
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new ContainsKeywordsPredicate(List.of(new NameSearchCriteria(userInput))));
        assertParseSuccess(parser, " n/Alice n/Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/Alice \n \t n/Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_inValidArgs_throwsParseException() {
        assertParseFailure(parser, " 1 n/Alex",
                "Please do not enter anything before the keywords!\n"
                + "Please remove this from your input: 1");
    }

    @Test
    public void parse_argsWithInvalidTag_throwsParseException() {
        // Tags should be Alphanumeric
        assertParseFailure(parser, " t/Alex Tang",
                "You have entered an invalid Tag!\n"
                        + Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_argsWithInvalidRole_throwsParseException() {
        // Roles should only be "patient" or "caregiver"
        assertParseFailure(parser, " role/random",
                "You have entered an invalid Role!\n"
                        + Role.MESSAGE_CONSTRAINTS);
    }
    @Test
    public void parse_argsWithInvalidNric_throwsParseException() {
        // Nrics should pass the checksum validation
        assertParseFailure(parser, " nric/s1234567e",
                "You have entered an invalid Nric!\n"
                        + Nric.MESSAGE_CONSTRAINTS);
    }
    @Test
    public void parse_argsWithInvalidName_throwsParseException() {
        // Names should only have alphanumeric characters and spaces
        assertParseFailure(parser, " n/bob /",
                "You have entered an invalid name!\n"
                        + Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_singleEmailSearchCriteria_success() throws Exception {
        // Test with single email criteria
        FindCommand command = parser.parse(" e/alice@example.com ");
        ContainsKeywordsPredicate expectedPredicate = new ContainsKeywordsPredicate(
                List.of(new EmailSearchCriteria(List.of("alice@example.com")))
        );
        assertEquals(new FindCommand(expectedPredicate), command);
    }

    @Test
    public void parse_singlePhoneSearchCriteria_success() throws Exception {
        // Test with single phone criteria
        FindCommand command = parser.parse(" p/12345678 ");
        ContainsKeywordsPredicate expectedPredicate = new ContainsKeywordsPredicate(
                List.of(new PhoneSearchCriteria(List.of("12345678")))
        );
        assertEquals(new FindCommand(expectedPredicate), command);
    }

    @Test
    public void parse_combinedEmailAndPhoneSearchCriteria_success() throws Exception {
        // Test with combined email and phone criteria
        FindCommand command = parser.parse(" e/alice@example.com p/12345678 ");
        ContainsKeywordsPredicate expectedPredicate = new ContainsKeywordsPredicate(
                List.of(
                        new PhoneSearchCriteria(List.of("12345678")),
                        new EmailSearchCriteria(List.of("alice@example.com"))
                )
        );
        assertEquals(new FindCommand(expectedPredicate), command);
    }

    @Test
    public void parse_multipleEmailsAndPhones_success() throws Exception {
        // Test with multiple emails and multiple phones
        FindCommand command = parser.parse(" e/alice@example.com e/bob@example.com p/12345678 p/87654321 ");
        ContainsKeywordsPredicate expectedPredicate = new ContainsKeywordsPredicate(
                List.of(
                        new PhoneSearchCriteria(List.of("12345678", "87654321")),
                        new EmailSearchCriteria(List.of("alice@example.com", "bob@example.com"))
                )
        );
        assertEquals(new FindCommand(expectedPredicate), command);
    }

    @Test
    public void parse_phoneSearchCriteria_success() throws Exception {
        // Test case with hyphenated or formatted phone numbers if supported
        FindCommand command = parser.parse(" p/1234567890 ");
        ContainsKeywordsPredicate expectedPredicate = new ContainsKeywordsPredicate(
                List.of(new PhoneSearchCriteria(List.of("1234567890")))
        );
        assertEquals(new FindCommand(expectedPredicate), command);
    }

    @Test
    public void parse_invalidEmailFormat_failure() {
        // Test invalid email format
        assertThrows(ParseException.class, () -> parser.parse(" e/alice(at)example(dot)com "));
    }

    @Test
    public void parse_emptyEmailAndPhone_failure() {
        // Test empty input for email and phone prefixes
        assertThrows(ParseException.class, () -> parser.parse(" e/ p/ "));
    }

}
