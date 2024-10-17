package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
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
    public void parse_missingParts_throwsParseException() {
        //no index specified
        String userInputWithNoIndex = "keyword1 keyword2";
        assertParseFailure(parser, userInputWithNoIndex,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTransactionCommand.MESSAGE_USAGE));
        //no description specified
        String userInputWithNoDescription = "1";
        assertParseFailure(parser, userInputWithNoDescription,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTransactionCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_validArgs_returnsFindTransactionCommand() {
        // no leading and trailing whitespaces
        FindTransactionCommand expectedFindTransactionCommand =
                new FindTransactionCommand(Index.fromOneBased(1),
                        new TransactionContainsKeywordsPredicate(Arrays.asList("invest", "materials")));
        assertParseSuccess(parser, "1 invest materials", expectedFindTransactionCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " 1\n invest \n \t materials  \t", expectedFindTransactionCommand);
    }

}


