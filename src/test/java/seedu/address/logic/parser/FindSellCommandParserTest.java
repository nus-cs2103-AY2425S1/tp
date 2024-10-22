package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindSellCommand;
import seedu.address.model.person.SellPropertyContainsKeywordsPredicate;

public class FindSellCommandParserTest {

    private FindSellCommandParser parser = new FindSellCommandParser();

    @Test
    public void parse_emptyString_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSellCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_onlySpaces_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSellCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleKeywords_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindSellCommand expectedFindSellProperty =
                new FindSellCommand(new SellPropertyContainsKeywordsPredicate(
                        Arrays.asList("522522", "spacious")));
        assertParseSuccess(parser, "522522 spacious", expectedFindSellProperty);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 522522 \n \t spacious  \t", expectedFindSellProperty);
    }

    @Test
    public void parse_tagWithMultipleWords_returnsFindCommand() {
        FindSellCommand expectedFindBuyProperty =
                new FindSellCommand(new SellPropertyContainsKeywordsPredicate(
                        Arrays.asList("near", "mrt")));
        assertParseSuccess(parser, "near mrt", expectedFindBuyProperty);
    }

    @Test
    public void parse_multipleValidArgs_returnsFindCommand() {
        FindSellCommand expectedFindBuyProperty =
                new FindSellCommand(new SellPropertyContainsKeywordsPredicate(
                        Arrays.asList("condo", "522522", "10-01")));
        assertParseSuccess(parser, "condo 522522 10-01", expectedFindBuyProperty);
    }
}
