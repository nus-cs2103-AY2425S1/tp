package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExportCommand;

public class ExportCommandParserTest {
    private ExportCommandParser parser = new ExportCommandParser();
    @Test
    public void parse_csvFormat_success() {
        ExportCommand expectedCommand = new ExportCommand("csv"); // Pass just "csv"
        assertParseSuccess(parser, "format/csv", expectedCommand); // Pass "format/csv" as input
    }

    @Test
    public void parse_missingCompulsoryFormat_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, "  ", expectedMessage);
    }

}
