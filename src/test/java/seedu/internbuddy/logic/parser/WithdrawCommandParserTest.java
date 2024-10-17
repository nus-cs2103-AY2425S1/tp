package seedu.internbuddy.logic.parser;

import static seedu.internbuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.internbuddy.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.internbuddy.logic.commands.WithdrawCommand;

public class WithdrawCommandParserTest {

    private WithdrawCommandParser parser = new WithdrawCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "1 1 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, WithdrawCommand.MESSAGE_USAGE));
    }
}
