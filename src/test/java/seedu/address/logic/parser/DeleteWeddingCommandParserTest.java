package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_WEDDING;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteWeddingCommand;

public class DeleteWeddingCommandParserTest {
    private DeleteWeddingCommandParser parser = new DeleteWeddingCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteWeddingCommand() {
        assertParseSuccess(parser, "1", new DeleteWeddingCommand(INDEX_FIRST_WEDDING));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteWeddingCommand.MESSAGE_USAGE));
    }

}
