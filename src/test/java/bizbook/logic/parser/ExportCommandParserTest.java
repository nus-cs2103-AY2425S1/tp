package bizbook.logic.parser;

import static bizbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static bizbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static bizbook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static bizbook.testutil.TypicalFileTypes.FILE_TYPE_CSV;

import org.junit.jupiter.api.Test;

import bizbook.logic.commands.ExportCommand;


public class ExportCommandParserTest {

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_validArgs_returnsExportCommand() {
        assertParseSuccess(parser, " f/csv", new ExportCommand(FILE_TYPE_CSV));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
    }

}
