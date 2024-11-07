package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailureEvent;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccessEvent;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.eventcommands.FindEventCommand;
import seedu.address.logic.commands.personcommands.FindPersonCommand;
import seedu.address.model.types.common.EventNameContainsKeywordsPredicate;
import seedu.address.model.types.common.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(
                parser,
                "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindPersonCommand expectedFindPersonCommand =
                new FindPersonCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindPersonCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindPersonCommand);
    }

    @Test
    public void parseEvent_emptyArg_throwsParseException() {
        assertParseFailureEvent(
                parser,
                "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        FindEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseEvent_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindEventCommand expectedFindEventCommand =
                new FindEventCommand(new EventNameContainsKeywordsPredicate(Arrays.asList("Convention", "Art")));
        assertParseSuccessEvent(parser, "Convention Art", expectedFindEventCommand);

        // multiple whitespaces between keywords
        assertParseSuccessEvent(parser, " \n Convention \n \t Art  \t", expectedFindEventCommand);
    }
}
