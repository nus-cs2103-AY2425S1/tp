package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.model.person.DateDistantToRecentComparator;
import seedu.address.model.person.DateRecentToDistantComparator;
import seedu.address.model.person.PriorityHighToLowComparator;
import seedu.address.model.person.PriorityLowToHighComparator;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_distantToRecent_returnsCommand() {
        assertParseSuccess(parser, "distant", new SortCommand(new DateDistantToRecentComparator()));
    }

    @Test
    public void parse_recentToDistant_returnsCommand() {
        assertParseSuccess(parser, "recent", new SortCommand(new DateRecentToDistantComparator()));
    }

    @Test
    public void parse_highToLow_returnsCommand() {
        assertParseSuccess(parser, "high", new SortCommand(new PriorityHighToLowComparator()));
    }

    @Test
    public void parse_lowToHigh_returnsCommand() {
        assertParseSuccess(parser, "low", new SortCommand(new PriorityLowToHighComparator()));
    }

    @Test
    public void parse_invalidArgs_throwsParseExcpetion() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
