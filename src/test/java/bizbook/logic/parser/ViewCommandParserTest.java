package bizbook.logic.parser;

import static bizbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static bizbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static bizbook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static bizbook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import bizbook.logic.commands.ViewCommand;

public class ViewCommandParserTest {

    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_validArgs_returnsViewCommand() {
        assertParseSuccess(parser, " 1", new ViewCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "Alex ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "-1 ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

}
