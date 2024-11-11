package seedu.sellsavvy.logic.parser;

import static seedu.sellsavvy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sellsavvy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.sellsavvy.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.logic.commands.customercommands.FindCustomerCommand;
import seedu.sellsavvy.model.customer.NameContainsKeywordsPredicate;

public class FindCustomerCommandParserTest {

    private FindCustomerCommandParser parser = new FindCustomerCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCustomerCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCustomerCommand() {
        // no leading and trailing whitespaces
        FindCustomerCommand expectedFindCustomerCommand =
                new FindCustomerCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCustomerCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCustomerCommand);
    }

}
