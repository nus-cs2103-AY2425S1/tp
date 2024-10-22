package seedu.address.logic.parser;


import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewCommand;
import seedu.address.model.person.Name;

class ViewCommandParserTest {
    private final ViewCommandParser viewCommandParser = new ViewCommandParser();

    @Test
    public void handlesEmptyInput() {
        assertParseSuccess(viewCommandParser, "", ViewCommand.closeView());
    }

    @Test
    public void parseSuccessfulTest() {
        assertParseSuccess(viewCommandParser, " n/Amy", new ViewCommand(new Name("Amy")));
    }

    @Test
    public void handleInvalidInput() {
        assertParseFailure(
                viewCommandParser,
                "n/Amy",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

}
