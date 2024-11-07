package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccessEvent;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccessNeither;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.eventcommands.ClearEventCommand;
import seedu.address.logic.commands.personcommands.ClearPersonCommand;

public class ClearCommandParserTest {

    private ClearCommandParser parser = new ClearCommandParser();

    @Test
    public void parseBoth_validArgs_returnsClearCommand() {
        assertParseSuccessNeither(parser, "", new ClearCommand());

        ClearCommand commandClear = parser.parseClear();
        assertEquals(commandClear, new ClearCommand());

        ClearCommand commandAbort = parser.parseAbort();
        assertEquals(commandAbort, new ClearCommand());
    }

    @Test
    public void parsePerson_validArgs_returnsClearPersonCommand() {
        assertParseSuccess(parser, "", new ClearPersonCommand());

        ClearCommand commandClear = parser.parseClear();
        assertEquals(commandClear, new ClearPersonCommand());

        ClearCommand commandAbort = parser.parseAbort();
        assertEquals(commandAbort, new ClearPersonCommand());
    }

    @Test
    public void parseEvent_validArgs_returnsClearEventCommand() {
        assertParseSuccessEvent(parser, "", new ClearEventCommand());

        ClearCommand commandClear = parser.parseClear();
        assertEquals(commandClear, new ClearEventCommand());

        ClearCommand commandAbort = parser.parseAbort();
        assertEquals(commandAbort, new ClearEventCommand());
    }

}
