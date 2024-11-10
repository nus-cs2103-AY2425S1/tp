package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.filename.Filename;
import seedu.address.logic.commands.ArchiveCommand;

public class ArchiveCommandParserTest {

    private final ArchiveCommandParser parser = new ArchiveCommandParser();

    @Test
    public void parse_validArgs_returnArchiveCommand() {
        assertParseSuccess(parser, "Test", new ArchiveCommand(new Filename("Test")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "/", String.format(Filename.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_blankArgs_throwsParseException() {
        assertParseSuccess(parser, " ", new ArchiveCommand());
    }
}
