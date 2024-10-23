package seedu.address.logic.parser.meetup;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FIND_MEETUP_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MEETUP_FIND_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.meetup.FindCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.meetup.FindCommand;
import seedu.address.model.meetup.MeetUpContainsKeywordsPredicate;


public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new MeetUpContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));

        assertParseSuccess(parser, MEETUP_FIND_DESC, expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MEETUP_FIND_DESC, expectedFindCommand);
    }

    @Test
    public void parse_validArgsWrongTag_failParse() {
        assertParseFailure(parser, INVALID_FIND_MEETUP_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

}
