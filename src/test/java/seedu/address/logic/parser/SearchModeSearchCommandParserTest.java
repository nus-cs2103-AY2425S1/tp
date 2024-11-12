package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_ATTENDEE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.searchmode.SearchModeSearchCommand;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.AddressContainsKeywordsPredicate;
import seedu.address.model.person.predicates.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.PersonIsRolePredicate;
import seedu.address.model.person.predicates.PhoneNumberContainsKeywordPredicate;
import seedu.address.model.person.predicates.TelegramContainsKeywordsPredicate;
import seedu.address.model.role.RoleHandler;
import seedu.address.model.role.exceptions.InvalidRoleException;


public class SearchModeSearchCommandParserTest {

    private SearchModeSearchCommandParser parser = new SearchModeSearchCommandParser();

    @Test
    public void parse_emptyInput_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SearchModeSearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validInput_success() {
        // whitespace only
        SearchModeSearchCommand expectedCommand = new SearchModeSearchCommand(
                new NameContainsKeywordsPredicate(Collections.singletonList("Amy")));

        assertParseSuccess(parser, " n/Amy", expectedCommand);

        // multiple whitespaces
        assertParseSuccess(parser, " n/  Amy  ", expectedCommand);

        // multiple keywords
        expectedCommand = new SearchModeSearchCommand(new NameContainsKeywordsPredicate(Arrays.asList("Amy", "Bob")));
        assertParseSuccess(parser, " n/Amy Bob", expectedCommand);

        // multiple keywords with leading and trailing whitespaces
        assertParseSuccess(parser, " n/  Amy Bob  ", expectedCommand);
    }

    @Test
    public void parse_allFieldsSpecified_success() {

        SearchModeSearchCommand expectedCommand = null;
        try {
            NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(
                    Collections.singletonList("Amy"));
            PhoneNumberContainsKeywordPredicate phonePredicate = new PhoneNumberContainsKeywordPredicate(
                    Collections.singletonList("1234567"));
            EmailContainsKeywordsPredicate emailPredicate = new EmailContainsKeywordsPredicate(
                    Collections.singletonList("test@gmail.com"));
            AddressContainsKeywordsPredicate addressPredicate = new AddressContainsKeywordsPredicate(
                    new ArrayList<>(Arrays.asList("123", "Road")));
            PersonIsRolePredicate rolePredicate = new PersonIsRolePredicate(
                    Collections.singletonList(RoleHandler.getRole(VALID_ROLE_ATTENDEE)));
            Set<Predicate<Person>> predicates = new HashSet<>(Arrays.asList(namePredicate, phonePredicate,
                    emailPredicate, addressPredicate, rolePredicate));
            expectedCommand = new SearchModeSearchCommand(predicates);
        } catch (InvalidRoleException e) {
            assert(false);
        }

        assertParseSuccess(parser, " n/Amy p/1234567 e/test@gmail.com"
                + " r/attendee a/123 Road", expectedCommand);
    }

    @Test
    public void parse_emptyTeleFlagField_throwsParseException() {
        assertParseFailure(parser, " t/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SearchModeSearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyRoleFlagField_throwsParseException() {
        assertParseFailure(parser, " r/", String.format(RoleHandler.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_emptyNameFlagField_throwsParseException() {
        assertParseFailure(parser, " n/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SearchModeSearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyPhoneFlagField_throwsParseException() {
        assertParseFailure(parser, " p/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SearchModeSearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyEmailFlagField_throwsParseException() {
        assertParseFailure(parser, " e/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SearchModeSearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyAddressFlagField_throwsParseException() {
        assertParseFailure(parser, " a/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SearchModeSearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_telegFlagField_success() {
        SearchModeSearchCommand expectedCommand = new SearchModeSearchCommand(
                new TelegramContainsKeywordsPredicate(Collections.singletonList("Amy123")));
        assertParseSuccess(parser, " t/Amy123", expectedCommand);
    }
}
