package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.SortAttribute.MESSAGE_CONSTRAINTS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = MESSAGE_CONSTRAINTS + "\n" + SortCommand.MESSAGE_USAGE;

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // EP: empty argument
        assertParseFailure(parser, "    ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // EP: invalid argument
        assertParseFailure(parser, "name and number", MESSAGE_INVALID_FORMAT);
    }
    @Test
    public void parse_validArgs_returnsSortCommand() {
        SortCommand expectedSortCommand = new SortCommand(ParserUtil.SortAttribute.REGISTERNUMBER);

        // EP: valid argument, boundary value: extra whitespace
        assertParseSuccess(parser, " register number ", expectedSortCommand);

        // EP: valid argument, boundary value: no extra whitespace
        expectedSortCommand = new SortCommand(ParserUtil.SortAttribute.ADDRESS);
        assertParseSuccess(parser, "address", expectedSortCommand);
    }
}
