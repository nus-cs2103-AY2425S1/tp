package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DELIVERY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteDeliveryCommand;

public class DeleteDeliveryCommandParserTest {

    private final DeleteDeliveryCommandParser parser = new DeleteDeliveryCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteDeliveryCommand() {
        // Test valid index input for DeleteDeliveryCommand
        assertParseSuccess(parser, "-d " + INDEX_FIRST_DELIVERY.getOneBased(),
                new DeleteDeliveryCommand(INDEX_FIRST_DELIVERY));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Test invalid index input (non-numeric)
        assertParseFailure(parser, "-d a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteDeliveryCommand.MESSAGE_USAGE));

        // Test missing index after "-d"
        assertParseFailure(parser, "-d", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteDeliveryCommand.MESSAGE_USAGE));

        // Test command with wrong prefix
        assertParseFailure(parser, "-x " + INDEX_FIRST_DELIVERY.getOneBased(),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteDeliveryCommand.MESSAGE_USAGE));

        // Test with empty input
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteDeliveryCommand.MESSAGE_USAGE));

        // Test with only spaces
        assertParseFailure(parser, "    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteDeliveryCommand.MESSAGE_USAGE));
    }
}


