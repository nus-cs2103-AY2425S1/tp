package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_failure() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsForName_returnsSortCommand() {
        SortCommand expectedSortCommand = new SortCommand("name");

        // No leading and trailing whitespaces
        assertParseSuccess(parser, "name", expectedSortCommand);

        // Multiple whitespaces
        assertParseSuccess(parser, " \n \t name \t \t \n", expectedSortCommand);

        // Case-insensitive
        assertParseSuccess(parser, " NamE", expectedSortCommand);
    }

    @Test
    public void parse_validArgsForAddress_returnsSortCommand() {
        SortCommand expectedSortCommand = new SortCommand("address");

        // No leading and trailing whitespaces
        assertParseSuccess(parser, "address", expectedSortCommand);

        // Multiple whitespaces
        assertParseSuccess(parser, " \n \t address \t \t \n", expectedSortCommand);

        // Case-insensitive
        assertParseSuccess(parser, " ADdResS", expectedSortCommand);
    }

    @Test
    public void parse_validArgsForPriority_returnsSortCommand() {
        SortCommand expectedSortCommand = new SortCommand("priority");

        // No leading and trailing whitespaces
        assertParseSuccess(parser, "priority", expectedSortCommand);

        // Multiple whitespaces
        assertParseSuccess(parser, " \n \t priority \t \t \n", expectedSortCommand);

        // Case-insensitive
        assertParseSuccess(parser, " pRIoRITy", expectedSortCommand);
    }

    @Test
    public void parse_validArgsForIncome_returnsSortCommand() {
        SortCommand expectedSortCommand = new SortCommand("income");

        // No leading and trailing whitespaces
        assertParseSuccess(parser, "income", expectedSortCommand);

        // Multiple whitespaces
        assertParseSuccess(parser, " \n \t income \t \t \n", expectedSortCommand);

        // Case-insensitive
        assertParseSuccess(parser, " IncOME", expectedSortCommand);
    }

    @Test
    public void parse_invalidArgs_failure() {
        // Multiple args
        assertParseFailure(parser, " name address",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        // Shorthand for fields
        assertParseFailure(parser, " pri",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " n/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
