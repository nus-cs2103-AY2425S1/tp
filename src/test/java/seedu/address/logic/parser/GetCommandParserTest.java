package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalParams.PARAMS_ARRAY_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.GetCommand;

public class GetCommandParserTest {
    private GetCommandParser parser = new GetCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsGetCommand() {
        // multiple leading and trailing whitespaces
        GetCommand expectedGetCommand =
                new GetCommand(PARAMS_ARRAY_FIRST);
        assertParseSuccess(parser, " phone email  ", expectedGetCommand);
    }

}
