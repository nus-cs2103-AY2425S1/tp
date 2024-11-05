package seedu.edulog.logic.parser;

import static seedu.edulog.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edulog.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edulog.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.edulog.logic.commands.PaidRevenueCommand;
import seedu.edulog.logic.commands.RevenueCommand;
import seedu.edulog.logic.commands.UnpaidRevenueCommand;


public class RevenueCommandParserTest {
    private RevenueCommandParser parser = new RevenueCommandParser();

    @Test
    public void parse_validArgs_successful() {
        assertParseSuccess(parser, "paid", new PaidRevenueCommand());
        assertParseSuccess(parser, "unpaid", new UnpaidRevenueCommand());
    }

    @Test
    public void parse_invalidArgs() {
        assertParseFailure(parser, "paid unpaid",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RevenueCommand.COMMAND_USAGE));
        assertParseFailure(parser, "12345",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RevenueCommand.COMMAND_USAGE));
        assertParseFailure(parser, "paid 123",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RevenueCommand.COMMAND_USAGE));
    }
}
