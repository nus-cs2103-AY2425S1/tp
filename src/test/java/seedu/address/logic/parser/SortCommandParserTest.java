package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.SortCommand.ASCENDING;
import static seedu.address.logic.commands.SortCommand.DESCENDING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "abc", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "ABC", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_wrongNumberOfArg_throwsParseException() {
        assertParseFailure(parser, "ASC ASC",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "descending ASC",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSortCommand() {
        //checks if no input given will be taken as sort by ascending order by default
        SortCommand expectedSortAscCommand =
                new SortCommand(ASCENDING);
        assertParseSuccess(parser, "  ", expectedSortAscCommand);
        // checks handling of arguments of asc, desc, ascending, descending
        assertParseSuccess(parser, "asc", expectedSortAscCommand);
        SortCommand expectedSortDescCommand =
                new SortCommand(DESCENDING);
        assertParseSuccess(parser, "desc", expectedSortDescCommand);
        assertParseSuccess(parser, "ascending", expectedSortAscCommand);
        assertParseSuccess(parser, "descending", expectedSortDescCommand);

        // checks the above cases with upper-casing
        assertParseSuccess(parser, "ASC", expectedSortAscCommand);
        assertParseSuccess(parser, "DESC", expectedSortDescCommand);
        assertParseSuccess(parser, "ASCENDING", expectedSortAscCommand);
        assertParseSuccess(parser, "DESCENDING", expectedSortDescCommand);


    }
}
