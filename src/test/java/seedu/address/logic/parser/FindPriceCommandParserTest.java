package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindPriceCommand;
import seedu.address.model.restaurant.PriceContainsKeywordsPredicate;

public class FindPriceCommandParserTest {

    private FindPriceCommandParser parser = new FindPriceCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindPriceCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindPriceCommand() {
        // no leading and trailing whitespaces
        FindPriceCommand expectedFindPriceCommand =
                new FindPriceCommand(new PriceContainsKeywordsPredicate(Arrays.asList("$", "$$")));
        assertParseSuccess(parser, "$ $$", expectedFindPriceCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n $ \n \t $$  \t", expectedFindPriceCommand);
    }

}
