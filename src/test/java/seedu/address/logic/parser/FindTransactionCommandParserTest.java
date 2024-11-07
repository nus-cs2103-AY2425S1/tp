package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindTransactionCommand;
import seedu.address.model.person.TransactionContainsKeywordsPredicate;

public class FindTransactionCommandParserTest {
    private FindTransactionCommandParser parser = new FindTransactionCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTransactionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindTransactionCommand() {
        // no leading and trailing whitespaces
        FindTransactionCommand expectedFindTransactionCommand = new FindTransactionCommand(
                new TransactionContainsKeywordsPredicate(Arrays.asList("invest", "materials")));
        assertParseSuccess(parser, "invest materials", expectedFindTransactionCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n invest \n \t materials  \t", expectedFindTransactionCommand);
    }

}


