package spleetwaise.transaction.logic.parser;

import org.junit.jupiter.api.Test;

import spleetwaise.transaction.logic.Messages;
import spleetwaise.transaction.logic.commands.MarkCommand;
import spleetwaise.transaction.testutil.TypicalIndexes;

public class MarkCommandParserTest {
    private final MarkCommandParser parser = new MarkCommandParser();

    @Test
    public void parse_validArgs_returnsMarkCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, "1",
                new MarkCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION)
        );
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "a",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE)
        );
    }
}

