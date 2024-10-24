package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    /*@Test - to be done in next iteration
    public void parse_validArgs_returnsFilterCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
                new FilterCommand(new PersonPredicate(Arrays.asList("Alice", "Bob"), emptyList(), emptyList(),
                        emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList()));
        String input = "n/Alice n/Bob";
        assertParseSuccess(parser, input, expectedFilterCommand);

        //multiple whitespaces between keywords
        //assertParseSuccess(parser, " \n n/Alice \n \t n/Bob  \t", expectedFilterCommand);
    }*/
}
