package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SwitchThemeCommand;

public class SwitchThemeCommandParserTest {

    private SwitchThemeCommandParser parser = new SwitchThemeCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            SwitchThemeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        SwitchThemeCommand expectedSwitchThemeCommand = new SwitchThemeCommand("LIGHT");
        assertParseSuccess(parser, "LIGHT", expectedSwitchThemeCommand);
    }
}
