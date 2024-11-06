package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.filename.Filename;
import seedu.address.logic.commands.LoadArchiveCommand;

public class LoadArchiveCommandParserTest {

    private final LoadArchiveCommandParser parser = new LoadArchiveCommandParser();

    @Test
    public void parse_validArgs_returnLoadArchiveCommand() {
        assertParseSuccess(parser, "Test", new LoadArchiveCommand(new Filename("Test")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                Filename.MESSAGE_CONSTRAINTS + "\n\n" + LoadArchiveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_blankArgs_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                Filename.MESSAGE_CONSTRAINTS_BLANK + "\n\n" + LoadArchiveCommand.MESSAGE_USAGE));
    }
}
