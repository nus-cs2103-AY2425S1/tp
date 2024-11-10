package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.ListTransactionCommand;

public class ListTransactionCommandParserTest {
    private ListTransactionCommandParser parser = new ListTransactionCommandParser();

    @Test
    public void parse_validInput_success() {
        assertParseSuccess(parser, "1", new ListTransactionCommand(INDEX_FIRST_CLIENT));
    }

    @Test
    public void parse_invalidInput_throwsParseException() {
        // user input not a number
        assertParseFailure(parser, "a",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListTransactionCommand.MESSAGE_USAGE));

        // empty user input
        assertParseFailure(parser, "",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListTransactionCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListTransactionCommand.MESSAGE_USAGE));
    }
}
