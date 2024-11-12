package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FinddelCommand;
import seedu.address.model.delivery.ItemNameContainsKeywordPredicate;

public class FinddelCommandParserTest {
    private FinddelCommandParser parser = new FinddelCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FinddelCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FinddelCommand expectedFinddelCommand =
                new FinddelCommand(new ItemNameContainsKeywordPredicate(Arrays.asList("monitor", "mouse")));
        assertParseSuccess(parser, "monitor mouse", expectedFinddelCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n monitor \n \t mouse  \t", expectedFinddelCommand);
    }
}
