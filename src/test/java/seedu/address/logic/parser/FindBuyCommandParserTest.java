package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindBuyCommand;
import seedu.address.model.person.BuyPropertyContainsKeywordsPredicate;

public class FindBuyCommandParserTest {

    private FindBuyCommandParser parser = new FindBuyCommandParser();

    @Test
    public void parse_emptyString_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindBuyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_onlySpaces_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindBuyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleKeywords_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindBuyCommand expectedFindBuyProperty =
                new FindBuyCommand(new BuyPropertyContainsKeywordsPredicate(
                        Arrays.asList("522522", "spacious")));
        assertParseSuccess(parser, "522522 spacious", expectedFindBuyProperty);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 522522 \n \t spacious  \t", expectedFindBuyProperty);
    }

    @Test
    public void parse_tagWithMultipleWords_returnsFindCommand() {
        FindBuyCommand expectedFindBuyProperty =
                new FindBuyCommand(new BuyPropertyContainsKeywordsPredicate(
                        Arrays.asList("near", "mrt")));
        assertParseSuccess(parser, "near mrt", expectedFindBuyProperty);
    }

    @Test
    public void parse_multipleValidArgs_returnsFindCommand() {
        FindBuyCommand expectedFindBuyProperty =
                new FindBuyCommand(new BuyPropertyContainsKeywordsPredicate(
                        Arrays.asList("condo", "522522", "10-01")));
        assertParseSuccess(parser, "condo 522522 10-01", expectedFindBuyProperty);
    }
}
