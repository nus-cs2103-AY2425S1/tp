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
        assertParseFailure(parser, "    ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "name and number", MESSAGE_INVALID_FORMAT);
    }
    @Test
    public void parse_validArgs_returnsSortCommand() {
        SortCommand expectedSortCommand = new SortCommand(ParserUtil.SortAttribute.REGISTERNUMBER);

        // multiple white spaces
        assertParseSuccess(parser, " register number ", expectedSortCommand);

        // no white spaces
        expectedSortCommand = new SortCommand(ParserUtil.SortAttribute.ADDRESS);
        assertParseSuccess(parser, "address", expectedSortCommand);
    }
}
