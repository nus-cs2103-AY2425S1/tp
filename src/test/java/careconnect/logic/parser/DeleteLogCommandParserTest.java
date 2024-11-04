package careconnect.logic.parser;

import static careconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static careconnect.logic.parser.CommandParserTestUtil.assertParseFailure;
import static careconnect.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static careconnect.testutil.TypicalIndexes.INDEX_FIRST_LOG;
import static careconnect.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import careconnect.logic.commands.CommandTestUtil;
import careconnect.logic.commands.DeleteLogCommand;

public class DeleteLogCommandParserTest {
    private DeleteLogCommandParser parser = new DeleteLogCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteLogCommand() {
        assertParseSuccess(parser, CommandTestUtil.INDEX_FIRST_PERSON
                        + CommandTestUtil.LOG_DELETE_INDEX,
                new DeleteLogCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_LOG));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, CommandTestUtil.INVALID_INDEX_DESC
                + CommandTestUtil.LOG_DELETE_INDEX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLogCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                CommandTestUtil.INDEX_FIRST_PERSON + CommandTestUtil.INVALID_INDEX_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLogCommand.MESSAGE_USAGE));
    }
}
