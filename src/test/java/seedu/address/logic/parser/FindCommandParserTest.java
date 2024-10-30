package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDY_GROUP_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.predicates.AgeContainsKeywordsPredicate;
import seedu.address.model.person.predicates.GenderMatchesKeywordsPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.testutil.FindUtil;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(
                FindUtil.getPredicateGroup(new NameContainsKeywordsPredicate(Set.of("Alice", "Bob"))));
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_NAME + " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_invalidValue_failure() {
        // no criteria given
        assertParseFailure(parser, "a", FindCommand.MESSAGE_NO_CRITERIA);

        // empty criteria given
        assertParseFailure(parser, " " + PREFIX_NAME,
                String.format(FindCommand.MESSAGE_EMPTY_CRITERIA, PREFIX_NAME));
        assertParseFailure(parser, " " + PREFIX_EMAIL,
                String.format(FindCommand.MESSAGE_EMPTY_CRITERIA, PREFIX_EMAIL));
        assertParseFailure(parser, " " + PREFIX_GENDER,
                String.format(FindCommand.MESSAGE_EMPTY_CRITERIA, PREFIX_GENDER));
        assertParseFailure(parser, " " + PREFIX_AGE,
                String.format(FindCommand.MESSAGE_EMPTY_CRITERIA, PREFIX_AGE));
        assertParseFailure(parser, " " + PREFIX_DETAIL,
                String.format(FindCommand.MESSAGE_EMPTY_CRITERIA, PREFIX_DETAIL));
        assertParseFailure(parser, " " + PREFIX_STUDY_GROUP_TAG,
                String.format(FindCommand.MESSAGE_EMPTY_CRITERIA, PREFIX_STUDY_GROUP_TAG));

        // invalid age criteria
        assertParseFailure(parser, " " + PREFIX_AGE + "a", AgeContainsKeywordsPredicate.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " " + PREFIX_AGE + "11 - 12", AgeContainsKeywordsPredicate.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " " + PREFIX_AGE + "11 -12", AgeContainsKeywordsPredicate.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " " + PREFIX_AGE + "-12", AgeContainsKeywordsPredicate.MESSAGE_CONSTRAINTS);

        // invalid gender criteria
        assertParseFailure(parser, " " + PREFIX_GENDER + "a", GenderMatchesKeywordsPredicate.MESSAGE_CONSTRAINTS);
    }
}
