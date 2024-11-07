package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILEPATH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.ImportCommand;

public class ImportCommandParserTest {

    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsImportCommand() {
        // no leading and trailing whitespaces
        String filepath = "src/test/data/CsvImportTest/typicalPersonsCsv.csv";
        ImportCommand expectedImportCommand = new ImportCommand(filepath);
        assertParseSuccess(parser, " \\f " + filepath, expectedImportCommand);
    }

    @Test
    public void parse_emptyFile_throwsError() {
        assertParseFailure(parser, " \\f ", "File does not exist!");
    }

    @Test
    public void parse_invalidFile_throwsError() {
        assertParseFailure(parser, " \\f invalidFile", "File does not exist!");
    }

    @Test
    public void parse_duplicatePrefix_throwsError() {
        assertParseFailure(parser, " \\f src \\f src",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_FILEPATH));
    }
}
