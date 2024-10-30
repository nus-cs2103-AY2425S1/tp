package seedu.eventtory.logic.parser;

import static seedu.eventtory.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eventtory.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.eventtory.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.eventtory.testutil.TypicalIndexes.INDEX_FIRST_VENDOR;

import org.junit.jupiter.api.Test;

import seedu.eventtory.logic.commands.DeleteCommand;
import seedu.eventtory.logic.commands.DeleteEventCommand;
import seedu.eventtory.logic.commands.DeleteVendorCommand;

public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, " v/1", new DeleteVendorCommand(INDEX_FIRST_VENDOR));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // invalid index - vendor
        assertParseFailure(parser, " v/1abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteVendorCommand.MESSAGE_USAGE));

        // invalid index - event
        assertParseFailure(parser, " e/1abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE));

        // multiple prefixes
        assertParseFailure(parser, " v/1 e/1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        // no prefix
        assertParseFailure(parser, " a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
