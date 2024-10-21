package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.LoadCommand;


public class LoadCommandParserTest {
    private static final String INPUT_MISSING_PREFIX = "load mybook.json";
    private static final String VALID_INPUT = "load pa/TestValidInput.json";
    private static final LoadCommandParser PARSER = new LoadCommandParser();

    @Test
    void invalid_input_throwException() {
        assertParseFailure(PARSER, INPUT_MISSING_PREFIX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));
    }

    @Test
    void valid_input() throws Exception {
        Path tempDir = Paths.get("archived");
        if (!Files.exists(tempDir)) {
            tempDir = Files.createDirectory(tempDir);
        }
        Path tempFile = tempDir.resolve("TestValidInput.json");
        Files.createFile(tempFile);
        assertParseSuccess(PARSER, VALID_INPUT, new LoadCommand(tempFile));
        Files.deleteIfExists(tempFile);
    }
}
