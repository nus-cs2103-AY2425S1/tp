package seedu.edulog.logic.parser;

import static seedu.edulog.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edulog.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.edulog.logic.commands.RevenueCommand;


public class RevenueCommandParserTest {
    private RevenueCommandParser parser = new RevenueCommandParser();

    @Test
    public void parse_validArgs_successful() {
        assertParseSuccess(parser, "paid", new RevenueCommand("paid"));
        assertParseSuccess(parser, "unpaid", new RevenueCommand("unpaid"));
    }

    @Test
    public void parse_invalidArgs() {
        assertParseFailure(parser, "paid unpaid", RevenueCommand.COMMAND_USAGE);
        assertParseFailure(parser, "12345", RevenueCommand.COMMAND_USAGE);
        assertParseFailure(parser, "paid 123", RevenueCommand.COMMAND_USAGE);
    }
}
