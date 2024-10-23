package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AbstractFindCommand;
import seedu.address.logic.commands.FindByEmailCommand;
import seedu.address.logic.commands.FindByNameCommand;
import seedu.address.logic.commands.FindByPhoneCommand;
import seedu.address.logic.commands.FindByTagCommand;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AbstractFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindByNameCommand() {
        FindByNameCommand expectedFindCommand =
                new FindByNameCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));

        // no leading and trailing whitespaces
        assertParseSuccess(parser, " n/Alice n/Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ \n Alice \n n/ \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgs_returnsFindByPhoneCommand() {
        FindByPhoneCommand expectedFindCommand =
                new FindByPhoneCommand(new PhoneContainsKeywordsPredicate(
                        Arrays.asList("91234567", "995")));

        // no leading and trailing whitespaces
        assertParseSuccess(parser, " p/ 91234567 p/ 995", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " p/ \n 91234567 \n p/\t 995  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgs_returnsFindByEmailCommand() {
        FindByEmailCommand expectedFindCommand =
                new FindByEmailCommand(new EmailContainsKeywordsPredicate(
                        Arrays.asList("ryan@gmail.com", "tasha@gmail.com")));

        // no leading and trailing whitespaces
        assertParseSuccess(parser, " e/ ryan@gmail.com e/tasha@gmail.com", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " e/ \n ryan@gmail.com \n e/\t tasha@gmail.com  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgs_returnsFindByTagCommand() {
        FindByTagCommand expectedFindCommand =
                new FindByTagCommand(new TagContainsKeywordsPredicate(
                        Arrays.asList("PC2174ALecturer", "PC2032classmate")));

        // no leading and trailing whitespaces
        assertParseSuccess(parser, " t/PC2174ALecturer t/PC2032classmate", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " t/ \n PC2174ALecturer \n t/\t PC2032classmate  \t", expectedFindCommand);
    }

}
