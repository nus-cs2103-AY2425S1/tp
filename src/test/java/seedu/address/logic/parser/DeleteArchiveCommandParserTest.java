package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.filename.Filename;
import seedu.address.logic.commands.DeleteArchiveCommand;

public class DeleteArchiveCommandParserTest {

    private final DeleteArchiveCommandParser parser = new DeleteArchiveCommandParser();

    @Test
    public void parse_validArgs_returnDeleteArchiveCommand() {
        assertParseSuccess(parser, "Test", new DeleteArchiveCommand(new Filename("Test")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                Filename.MESSAGE_CONSTRAINTS + "\n\n" + DeleteArchiveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_blankArgs_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                Filename.MESSAGE_CONSTRAINTS_BLANK + "\n\n" + DeleteArchiveCommand.MESSAGE_USAGE));
    }
}
