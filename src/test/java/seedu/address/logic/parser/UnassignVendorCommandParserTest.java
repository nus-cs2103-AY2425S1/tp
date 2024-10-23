package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnassignVendorCommand;

public class UnassignVendorCommandParserTest {

    private UnassignVendorCommandParser parser = new UnassignVendorCommandParser();

    @Test
    public void parse_validArgs_returnsUnassignVendorCommand() {
        assertParseSuccess(parser, "1", new UnassignVendorCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_validArgsWithTrailingSpace_returnsUnassignVendorCommand() {
        assertParseSuccess(parser, "1 \n", new UnassignVendorCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnassignVendorCommand.MESSAGE_USAGE));
    }
}
