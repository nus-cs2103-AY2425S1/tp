package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.ViewClientCommand;


public class ViewClientCommandParserTest {

    private ViewClientCommandParser parser = new ViewClientCommandParser();

    @Test
    public void parse_validArgs_returnsViewClientCommand() {
        ViewClientCommand expectedCommand = new ViewClientCommand(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, "1", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                                                        ViewClientCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                                                        ViewClientCommand.MESSAGE_USAGE));
    }
}
