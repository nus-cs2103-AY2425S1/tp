package seedu.eventfulnus.logic.parser;

import static seedu.eventfulnus.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eventfulnus.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.eventfulnus.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.eventfulnus.logic.commands.FindEventCommand;
import seedu.eventfulnus.model.event.EventContainsKeywordsPredicate;

public class FindEventCommandParserTest {

    private FindEventCommandParser parser = new FindEventCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindEventCommand() {
        // no leading and trailing whitespaces
        FindEventCommand expectedFindEventCommand =
                new FindEventCommand(new EventContainsKeywordsPredicate(Arrays.asList("ifg", "ivp")));
        assertParseSuccess(parser, "ifg ivp", expectedFindEventCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n ifg \n \t ivp  \t", expectedFindEventCommand);
    }

}
