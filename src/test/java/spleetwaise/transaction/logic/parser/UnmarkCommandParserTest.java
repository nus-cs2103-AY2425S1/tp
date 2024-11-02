package spleetwaise.transaction.logic.parser;

import org.junit.jupiter.api.Test;

import spleetwaise.transaction.logic.Messages;
import spleetwaise.transaction.logic.commands.UnmarkCommand;
import spleetwaise.transaction.testutil.TypicalIndexes;

public class UnmarkCommandParserTest {
    private final UnmarkCommandParser parser = new UnmarkCommandParser();

    @Test
    public void parse_validArgs_returnsUnmarkCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, "1",
                new UnmarkCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION)
        );
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "a",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE)
        );
    }
}
