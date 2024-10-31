package bizbook.logic.parser;

import static bizbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static bizbook.logic.Messages.MESSAGE_UNSUPPORTED_FILE_TYPE;
import static bizbook.logic.parser.CliSyntax.PREFIX_FILE;
import static bizbook.logic.parser.CliSyntax.PREFIX_PATH;
import static bizbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static bizbook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static bizbook.testutil.TypicalFileTypes.FILE_TYPE_CSV;
import static bizbook.testutil.TypicalFileTypes.FILE_TYPE_VCF;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import bizbook.logic.commands.ImportCommand;


public class ImportCommandParserTest {
    private final ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_validArgs_returnsImportCommand() {
        assertParseSuccess(parser, " " + PREFIX_FILE + FILE_TYPE_VCF.name() + " " + PREFIX_PATH + "a/b/c",
                new ImportCommand(FILE_TYPE_VCF, Path.of("a/b/c")));
    }

    @Test
    public void parse_unsupportedFileType_throwsParseException() {
        // This premise is needed for this test to work
        assertFalse(FILE_TYPE_CSV.hasImporter());

        assertParseFailure(parser, " " + PREFIX_FILE + FILE_TYPE_CSV.name() + " " + PREFIX_PATH + "a/b/c",
                String.format(MESSAGE_UNSUPPORTED_FILE_TYPE, ImportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingArg_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_FILE + FILE_TYPE_VCF.name(),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }
}
