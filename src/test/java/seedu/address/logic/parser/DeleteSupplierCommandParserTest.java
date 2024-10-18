package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteSupplierCommand;
public class DeleteSupplierCommandParserTest {
    private DeleteSupplierCommandParser parser = new DeleteSupplierCommandParser();
    @Test
    public void parse_validArgs_returnsDeleteSupplierCommand() {
        // Test valid index input for DeleteSupplierCommand
        assertParseSuccess(parser, "-s " + INDEX_FIRST_PERSON.getOneBased(),
                new DeleteSupplierCommand(INDEX_FIRST_PERSON));
    }
    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Test invalid index input (non-numeric)
        assertParseFailure(parser, "-s a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteSupplierCommand.MESSAGE_USAGE));

        // Test missing index after "-s"
        assertParseFailure(parser, "-s", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteSupplierCommand.MESSAGE_USAGE));

        // Test command with wrong prefix
        assertParseFailure(parser, "-x " + INDEX_FIRST_PERSON.getOneBased(),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSupplierCommand.MESSAGE_USAGE));

        // Test with empty input
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteSupplierCommand.MESSAGE_USAGE));

        // Test with only spaces
        assertParseFailure(parser, "    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteSupplierCommand.MESSAGE_USAGE));
    }
}
