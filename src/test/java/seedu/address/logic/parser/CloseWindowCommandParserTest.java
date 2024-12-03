package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CloseWindowCommand;

public class CloseWindowCommandParserTest {

    private final CloseWindowCommandParser parser = new CloseWindowCommandParser();

    @Test
    public void parse_validArgs_returnsCloseWindowCommand() {
        assertParseSuccess(parser, "", new CloseWindowCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "invalidCommand",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CloseWindowCommand.MESSAGE_USAGE));
    }
}
