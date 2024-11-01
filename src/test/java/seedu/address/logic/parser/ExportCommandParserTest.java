package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.File;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExportCommand;

public class ExportCommandParserTest {

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_validArgs_returnsExportCommand() {
        File exportFile = new File("validExportFile");
        File afterParseExportFile = new File("validExportFile.txt");
        assertParseSuccess(parser, exportFile.toString(), new ExportCommand(afterParseExportFile));
    }

    @Test
    public void parse_emptyStringInvalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nullPathInvalidArgs_throwsParseException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }
}
