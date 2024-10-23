package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SuperFindCommand;
import seedu.address.model.person.CombinedContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SuperFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsAbstractFindCommandWithNames() {
        NameContainsKeywordsPredicate namePredicate =
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        CombinedContainsKeywordsPredicate expectedPredicate =
                new CombinedContainsKeywordsPredicate(List.of(namePredicate));

        SuperFindCommand expectedFindCommand = new SuperFindCommand(expectedPredicate);

        // no leading and trailing whitespaces
        assertParseSuccess(parser, " n/Alice n/Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ \n Alice \n n/ \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgs_returnsAbstractFindCommandWithPhones() {
        PhoneContainsKeywordsPredicate phonePredicate =
                new PhoneContainsKeywordsPredicate(Arrays.asList("91234567", "995"));
        CombinedContainsKeywordsPredicate expectedPredicate =
                new CombinedContainsKeywordsPredicate(List.of(phonePredicate));

        SuperFindCommand expectedFindCommand = new SuperFindCommand(expectedPredicate);

        // no leading and trailing whitespaces
        assertParseSuccess(parser, " p/ 91234567 p/ 995", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " p/ \n 91234567 \n p/\t 995  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgs_returnsAbstractFindCommandWithEmails() {
        EmailContainsKeywordsPredicate emailPredicate =
                new EmailContainsKeywordsPredicate(Arrays.asList("ryan@gmail.com", "tasha@gmail.com"));

        CombinedContainsKeywordsPredicate expectedPredicate =
                new CombinedContainsKeywordsPredicate(List.of(emailPredicate));

        SuperFindCommand expectedFindCommand = new SuperFindCommand(expectedPredicate);

        // no leading and trailing whitespaces
        assertParseSuccess(parser, " e/ ryan@gmail.com e/tasha@gmail.com", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " e/ \n ryan@gmail.com \n e/\t tasha@gmail.com  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgs_returnsAbstractFindCommandWithTags() {
        TagContainsKeywordsPredicate tagPredicate =
                new TagContainsKeywordsPredicate(Arrays.asList("PC2174ALecturer", "PC2032classmate"));

        CombinedContainsKeywordsPredicate expectedPredicate =
                new CombinedContainsKeywordsPredicate(List.of(tagPredicate));

        SuperFindCommand expectedFindCommand = new SuperFindCommand(expectedPredicate);

        // no leading and trailing whitespaces
        assertParseSuccess(parser, " t/PC2174ALecturer t/PC2032classmate", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " t/ \n PC2174ALecturer \n t/\t PC2032classmate  \t", expectedFindCommand);
    }
}
