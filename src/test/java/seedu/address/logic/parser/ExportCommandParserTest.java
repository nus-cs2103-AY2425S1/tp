// Originally implemented by teammate @frymash, code was reused to perform code coverage for ExportCommandParser
package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.ExportCommand.Format;

public class ExportCommandParserTest {
    private ExportCommandParser parser = new ExportCommandParser();
    @Test
    public void parse_csvFormat_success() {
        ExportCommand expectedCommand = new ExportCommand(Format.CSV);
        assertParseSuccess(parser, "format\\csv", expectedCommand); // Pass "format\csv" as input
    }

    @Test
    public void parse_txtFormat_success() {
        ExportCommand expectedCommand = new ExportCommand(Format.TXT);
        assertParseSuccess(parser, "format\\txt", expectedCommand); // Pass "format\txt" as input
    }

    @Test
    public void parse_missingCompulsoryFormat_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, "  ", expectedMessage);
    }

}
