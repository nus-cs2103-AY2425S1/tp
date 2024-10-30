package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindDayCommand;
import seedu.address.model.person.DayContainsKeywordsPredicate;

public class FindDayCommandParserTest {

    private FindDayCommandParser parser = new FindDayCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDayCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindDayCommand() {
        // no leading and trailing whitespaces
        FindDayCommand expectedFindDayCommand =
                new FindDayCommand(new DayContainsKeywordsPredicate(Arrays.asList("monday", "tuesday")));
        assertParseSuccess(parser, "monday tuesday", expectedFindDayCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n monday \n \t tuesday  \t", expectedFindDayCommand);
    }

}
