package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortByPriorityCommand;
import seedu.address.model.person.PriorityHighToLowComparator;
import seedu.address.model.person.PriorityLowToHighComparator;

public class SortByPriorityCommandParserTest {

    private SortByPriorityCommandParser parser = new SortByPriorityCommandParser();

    @Test
    public void parse_highToLow_returnsCommand() {
        assertParseSuccess(parser, "high", new SortByPriorityCommand(new PriorityHighToLowComparator()));
    }

    @Test
    public void parse_lowToHigh_returnsCommand() {
        assertParseSuccess(parser, "low", new SortByPriorityCommand(new PriorityLowToHighComparator()));
    }

    @Test
    public void parse_invalidArgs_throwsParseExcpetion() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortByPriorityCommand.MESSAGE_USAGE));
    }

}
