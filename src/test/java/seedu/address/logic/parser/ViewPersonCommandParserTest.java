package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewPersonCommand;
import seedu.address.model.person.PersonFulfilsPredicate;

public class ViewPersonCommandParserTest {

    private ViewPersonCommandParser parser = new ViewPersonCommandParser();

    @Test
    public void parse_validArgs_returnsViewCommand() {
        ViewPersonCommand expectedViewCommand1 = new ViewPersonCommand(new PersonFulfilsPredicate("buyer"));
        ViewPersonCommand expectedViewCommand2 = new ViewPersonCommand(new PersonFulfilsPredicate("seller"));

        assertParseSuccess(parser, "buyer", expectedViewCommand1);
        assertParseSuccess(parser, "seller", expectedViewCommand2);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "hello",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArg_returnsViewCommand() {
        ViewPersonCommand expectedViewCommand = new ViewPersonCommand(new PersonFulfilsPredicate(""));
        assertParseSuccess(parser, "", expectedViewCommand);
    }
}
