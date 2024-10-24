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
        assertParseFailure(parser, " abc", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " ABC", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " n/abc", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " sch/abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_wrongNumberOfArg_throwsParseException() {
        assertParseFailure(parser, " n/ASC n/ASC",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " n/ASC sch/ASC",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " descending ASC",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSortCommand() {
        //checks if no ORDER is given command will sort by ascending order by default
        SortCommand expectedSortAscNameCommand =
                new SortCommand(ASCENDING, false);
        assertParseSuccess(parser, " n/", expectedSortAscNameCommand);
        SortCommand expectedSortAscScheduleCommand =
                new SortCommand(ASCENDING, true);
        assertParseSuccess(parser, " sch/", expectedSortAscScheduleCommand);
        // checks handling of arguments of asc and ascending
        assertParseSuccess(parser, " n/asc", expectedSortAscNameCommand);
        assertParseSuccess(parser, " sch/asc", expectedSortAscScheduleCommand);
        assertParseSuccess(parser, " n/ascending", expectedSortAscNameCommand);
        assertParseSuccess(parser, " sch/ascending", expectedSortAscScheduleCommand);
        // checks handling of arguments of desc and descending
        SortCommand expectedSortDescNameCommand =
                new SortCommand(DESCENDING, false);
        SortCommand expectedSortDescScheduleCommand =
                new SortCommand(DESCENDING, true);
        assertParseSuccess(parser, " n/desc", expectedSortDescNameCommand);
        assertParseSuccess(parser, " n/descending", expectedSortDescNameCommand);
        assertParseSuccess(parser, " sch/desc", expectedSortDescScheduleCommand);
        assertParseSuccess(parser, " sch/descending", expectedSortDescScheduleCommand);

        // checks the above cases with upper-casing
        assertParseSuccess(parser, " n/ASC", expectedSortAscNameCommand);
        assertParseSuccess(parser, " n/DESC", expectedSortDescNameCommand);
        assertParseSuccess(parser, " n/ASCENDING", expectedSortAscNameCommand);
        assertParseSuccess(parser, " n/DESCENDING", expectedSortDescNameCommand);
        assertParseSuccess(parser, " sch/ASC", expectedSortAscScheduleCommand);
        assertParseSuccess(parser, " sch/DESC", expectedSortDescScheduleCommand);
        assertParseSuccess(parser, " sch/ASCENDING", expectedSortAscScheduleCommand);
        assertParseSuccess(parser, " sch/DESCENDING", expectedSortDescScheduleCommand);
    }
}
