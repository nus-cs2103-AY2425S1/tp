package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortByDateCommand;
import seedu.address.model.person.DateDistantToRecentComparator;
import seedu.address.model.person.DateRecentToDistantComparator;

public class SortByDateCommandParserTest {

    private SortByDateCommandParser parser = new SortByDateCommandParser();

    @Test
    public void parse_distantToRecent_returnsCommand() {
        assertParseSuccess(parser, "distant", new SortByDateCommand(new DateDistantToRecentComparator()));
    }

    @Test
    public void parse_recentToDistant_returnsCommand() {
        assertParseSuccess(parser, "recent", new SortByDateCommand(new DateRecentToDistantComparator()));
    }

    @Test
    public void parse_invalidArgs_throwsParseExcpetion() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortByDateCommand.MESSAGE_USAGE));
    }
}
