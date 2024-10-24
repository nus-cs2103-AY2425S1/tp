package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindTimeCommand;
import seedu.address.model.preferredtime.PreferredTimeOverlapsRangesPredicate;


public class FindTimeCommandParserTest {
    private FindTimeCommandParser parser = new FindTimeCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTimeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindTimeCommand() {
        // no leading and trailing whitespaces
        FindTimeCommand expectedFindTimeCommand =
                new FindTimeCommand(new PreferredTimeOverlapsRangesPredicate(Arrays.asList("1200-1330", "1830-1930")));
        assertParseSuccess(parser, "1200-1330 1830-1930", expectedFindTimeCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 1200-1330 \n \t 1830-1930  \t", expectedFindTimeCommand);
    }

}
