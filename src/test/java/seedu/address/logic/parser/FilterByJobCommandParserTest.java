package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterByJobCommand;
import seedu.address.model.person.JobContainsKeywordsPredicate;

public class FilterByJobCommandParserTest {

    private final FilterByJobCommandParser parser = new FilterByJobCommandParser();

    @Test
    public void parse_validArgs_returnsFilterByJobCommand() {
        FilterByJobCommand expectedCommand =
                new FilterByJobCommand(new JobContainsKeywordsPredicate(Arrays.asList("Engineer", "Doctor")));
        assertParseSuccess(parser, "Engineer Doctor", expectedCommand);
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterByJobCommand.MESSAGE_USAGE));
    }
}
