package seedu.sellsavvy.logic.parser;

import static seedu.sellsavvy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sellsavvy.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.logic.commands.ordercommands.AddOrderCommand;

public class AddOrderCommandParserTest {

    private AddOrderCommandParser parser = new AddOrderCommandParser();

    // TODO: Update tests when implementation is done
    @Test
    public void parseAllFieldsPresent_success() { // dummy test
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));
    }
}
