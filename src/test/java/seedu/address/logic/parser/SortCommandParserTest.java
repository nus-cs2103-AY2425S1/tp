package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.model.person.PersonComparator;


public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_noParameters_throwsParseException() {
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " n/ascenf",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " sort n/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_twoParameters_throwsParseException() {
        assertParseFailure(parser, " n/ d/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " a/ascending d/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " n/ p/ascending",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidParameter_throwsParseException() {
        assertParseFailure(parser, " a/ascending",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " p/descending",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " t/asc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_success() {
        SortCommand ascendingNameSortCommand = new SortCommand(PersonComparator.NAME, true);
        SortCommand descendingNameSortCommand = new SortCommand(PersonComparator.NAME, false);
        SortCommand ascendingDateOfLastVisitSortCommand =
                new SortCommand(PersonComparator.DATE_OF_LAST_VISIT, true);
        SortCommand descendingDateOfLastVisitSortCommand =
                new SortCommand(PersonComparator.DATE_OF_LAST_VISIT, false);

        assertParseSuccess(parser, " n/ ", ascendingNameSortCommand);
        assertParseSuccess(parser, " n/ascending   ", ascendingNameSortCommand);
        assertParseSuccess(parser, " n/asc", ascendingNameSortCommand);

        assertParseSuccess(parser, " d/", ascendingDateOfLastVisitSortCommand);
        assertParseSuccess(parser, " d/ascending   ", ascendingDateOfLastVisitSortCommand);
        assertParseSuccess(parser, " d/asc", ascendingDateOfLastVisitSortCommand);

        assertParseSuccess(parser, " n/descending", descendingNameSortCommand);
        assertParseSuccess(parser, " n/desc", descendingNameSortCommand);

        assertParseSuccess(parser, " d/descending", descendingDateOfLastVisitSortCommand);
        assertParseSuccess(parser, " d/desc", descendingDateOfLastVisitSortCommand);
    }

}
