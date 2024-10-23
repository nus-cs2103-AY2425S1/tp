package spleetwaise.transaction.logic.parser;

import org.junit.jupiter.api.Test;

import spleetwaise.transaction.logic.Messages;
import spleetwaise.transaction.logic.commands.DeleteCommand;
import spleetwaise.transaction.testutil.TypicalIndexes;

public class DeleteCommandParserTest {

    private final DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, "1",
                new DeleteCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION)
        );
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "a",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE)
        );
    }
}
