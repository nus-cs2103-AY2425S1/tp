package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEDDING_NAME_ONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteWeddingCommand;

public class DeleteWeddingCommandParserTest {

    private DeleteWeddingCommandParser parser = new DeleteWeddingCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteWeddingCommand() {
        assertParseSuccess(parser, "w/" + VALID_WEDDING_NAME_ONE,
                new DeleteWeddingCommand(VALID_WEDDING_NAME_ONE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "w/", String.format(DeleteWeddingCommand.MESSAGE_MISSING_NAME));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertParseFailure(parser, VALID_WEDDING_NAME_ONE, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteWeddingCommand.MESSAGE_USAGE));
    }
}
