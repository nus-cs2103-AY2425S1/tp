package seedu.hireme.logic.parser;

import static seedu.hireme.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hireme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.hireme.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.hireme.logic.commands.SortCommand;
import seedu.hireme.model.internshipapplication.DateComparator;

public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgs_returnsSortCommand() {
        // no leading and trailing whitespaces
        SortCommand expectedSortCommand1 = new SortCommand(new DateComparator(true));
        assertParseSuccess(parser, "earliest", expectedSortCommand1);

        // Mixed-case order word
        SortCommand expectedSortCommand2 = new SortCommand(new DateComparator(true));
        assertParseSuccess(parser, "eaRliEsT", expectedSortCommand2);

        // no leading and trailing whitespaces
        SortCommand expectedSortCommand3 = new SortCommand(new DateComparator(false));
        assertParseSuccess(parser, "latest", expectedSortCommand3);

        // Mixed-case order word
        SortCommand expectedSortCommand4 = new SortCommand(new DateComparator(false));
        assertParseSuccess(parser, "lATEst", expectedSortCommand4);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
