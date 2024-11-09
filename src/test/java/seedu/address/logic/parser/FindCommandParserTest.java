package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.FindCommandParser.MESSAGE_NO_KEYWORD;
import static seedu.address.logic.parser.FindCommandParser.MESSAGE_NO_PREFIX;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.DepartmentContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.PredicateContainer;
import seedu.address.model.person.RoleContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a n/john",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNameArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        NameContainsKeywordsPredicate nameContainsKeywordsPredicate =
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        PredicateContainer predicateContainer = new PredicateContainer()
                .addNameContainsKeywordsPredicate(nameContainsKeywordsPredicate);
        FindCommand expectedFindCommand =
                new FindCommand(predicateContainer);
        assertParseSuccess(parser, "all n/Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "all n/\n Alice \n \t Bob  \t", expectedFindCommand);
    }
    @Test
    public void parse_validPhoneArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        PhoneContainsKeywordsPredicate phoneContainsKeywordsPredicate =
                new PhoneContainsKeywordsPredicate(Arrays.asList("12345678", "87654321"));
        PredicateContainer predicateContainer = new PredicateContainer()
                .addPhoneContainsKeywordsPredicate(phoneContainsKeywordsPredicate);
        FindCommand expectedFindCommand =
                new FindCommand(predicateContainer);
        assertParseSuccess(parser, "all p/12345678 87654321", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "all p/\n 12345678 \n \t 87654321  \t", expectedFindCommand);
    }
    @Test
    public void parse_validEmailArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        EmailContainsKeywordsPredicate emailContainsKeywordsPredicate =
                new EmailContainsKeywordsPredicate(Arrays.asList("alice@gmail.com", "bob@gmail.com"));
        PredicateContainer predicateContainer = new PredicateContainer()
                .addEmailContainsKeywordsPredicate(emailContainsKeywordsPredicate);
        FindCommand expectedFindCommand =
                new FindCommand(predicateContainer);
        assertParseSuccess(parser, "all e/alice@gmail.com bob@gmail.com", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "all e/\n alice@gmail.com \n \t bob@gmail.com  \t", expectedFindCommand);
    }

    @Test
    public void parse_validDepartmentArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        DepartmentContainsKeywordsPredicate departmentContainsKeywordsPredicate =
                new DepartmentContainsKeywordsPredicate(Arrays.asList("IT", "Management"));
        PredicateContainer predicateContainer = new PredicateContainer()
                .addDepartmentContainsKeywordsPredicate(departmentContainsKeywordsPredicate);
        FindCommand expectedFindCommand =
                new FindCommand(predicateContainer);
        assertParseSuccess(parser, "all d/IT Management", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "all d/\n IT \n \t Management  \t", expectedFindCommand);
    }
    @Test
    public void parse_validRoleArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        RoleContainsKeywordsPredicate roleContainsKeywordsPredicate =
                new RoleContainsKeywordsPredicate(Arrays.asList("Manager", "President"));
        PredicateContainer predicateContainer = new PredicateContainer()
                .addRoleContainsKeywordsPredicate(roleContainsKeywordsPredicate);
        FindCommand expectedFindCommand =
                new FindCommand(predicateContainer);
        assertParseSuccess(parser, "all r/Manager President", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "all r/\n Manager \n \t President  \t", expectedFindCommand);
    }

    @Test
    public void parse_no_prefix() {
        assertParseFailure(parser, "all", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_NO_PREFIX));

        assertParseFailure(parser, "ph", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_NO_PREFIX));

        assertParseFailure(parser, "e", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_NO_PREFIX));
    }

    @Test
    public void parse_no_keywords() {
        assertParseFailure(parser, "all n/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_NO_KEYWORD));

        assertParseFailure(parser, "ph p/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_NO_KEYWORD));

        assertParseFailure(parser, "e e/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_NO_KEYWORD));
    }

}
