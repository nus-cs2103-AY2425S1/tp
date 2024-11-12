package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalPaths.VALID_MISSING_DATA_IMPORT_FILE;
import static seedu.address.testutil.TypicalPaths.VALID_NO_DUPS_IMPORT_FILE;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ImportCommandParserTest {

    private final ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        String userInput = VALID_NO_DUPS_IMPORT_FILE.toString();
        ImportCommand expected = new ImportCommand(VALID_NO_DUPS_IMPORT_FILE);
        assertEquals(parser.parse(userInput), expected);
    }

    @Test
    public void parseInvalidPathFailure() {
        Path projectRootPath = Paths.get(System.getProperty("user.dir"));
        Path importCsvPath = projectRootPath.resolve("src")
            .resolve("test").resolve("data").resolve("ImportCommandTest").resolve("invalid_importFile.csv");
        // TODO
        // change importCsvPath to be an imported static variable to go in accordance with abstraction
        String userInput = importCsvPath.toString();
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parseEmptyPathFailure() {
        String userInput = "";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parseWhitespacePathFailure() {
        String userInput = "   ";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingDataPath_success() throws Exception {
        String userInput = VALID_MISSING_DATA_IMPORT_FILE.toString();
        ImportCommand expected = new ImportCommand(VALID_MISSING_DATA_IMPORT_FILE);
        assertEquals(parser.parse(userInput), expected);
    }
}
