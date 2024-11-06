package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.eventcommands.ClearEventCommand;
import seedu.address.logic.commands.personcommands.ClearPersonCommand;

public class ClearCommandParserTest {

    private ClearCommandParser parser = new ClearCommandParser();

    @Test
    public void parse_validArgs_returnsClearCommand() {
        assertParseSuccess(parser, "", new ClearCommand());
    }

    @Test
    public void parse_validArgs_returnsClearPersonCommand() {
        assertParseSuccess(parser, "p", new ClearPersonCommand());
    }

    @Test
    public void parse_validArgs_returnsClearEventCommand() {
        assertParseSuccess(parser, "e", new ClearEventCommand());
    }

}
