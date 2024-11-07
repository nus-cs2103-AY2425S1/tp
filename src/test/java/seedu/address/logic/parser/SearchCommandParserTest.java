package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailureEvent;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccessEvent;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.eventcommands.SearchEventCommand;
import seedu.address.logic.commands.personcommands.SearchPersonCommand;
import seedu.address.model.types.common.EventTagContainsKeywordsPredicate;
import seedu.address.model.types.common.PersonTagContainsKeywordsPredicate;

public class SearchCommandParserTest {

    private SearchCommandParser parser = new SearchCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(
                parser,
                "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        SearchPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSearchCommand() {
        // no leading and trailing whitespaces
        SearchPersonCommand expectedSearchPersonCommand =
                new SearchPersonCommand(new PersonTagContainsKeywordsPredicate(Arrays.asList("Friend", "Family")));
        assertParseSuccess(parser, "Friend Family", expectedSearchPersonCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Friend \n \t Family  \t", expectedSearchPersonCommand);
    }

    @Test
    public void parseEvent_emptyArg_throwsParseException() {
        assertParseFailureEvent(
                parser,
                "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        SearchEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseEvent_validArgs_returnsSearchCommand() {
        // no leading and trailing whitespaces
        SearchEventCommand expectedSearchEventCommand =
                new SearchEventCommand(new EventTagContainsKeywordsPredicate(Arrays.asList("Convention", "Art")));
        assertParseSuccessEvent(parser, "Convention Art", expectedSearchEventCommand);

        // multiple whitespaces between keywords
        assertParseSuccessEvent(parser, " \n Convention \n \t Art  \t", expectedSearchEventCommand);
    }
}
