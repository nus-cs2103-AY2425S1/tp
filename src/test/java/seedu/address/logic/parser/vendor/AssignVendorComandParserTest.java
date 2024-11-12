package seedu.address.logic.parser.vendor;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.vendor.AssignVendorCommand;

public class AssignVendorComandParserTest {

    private AssignVendorCommandParser parser = new AssignVendorCommandParser();

    @Test
    public void parse_validArgs_returnsAssignVendorCommand() {
        assertParseSuccess(parser, "1", new AssignVendorCommand(INDEX_FIRST));
    }

    @Test
    public void parse_validArgsWithTrailingSpace_returnsAssignVendorCommand() {
        assertParseSuccess(parser, "1 \t", new AssignVendorCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AssignVendorCommand.MESSAGE_USAGE));
    }

}
