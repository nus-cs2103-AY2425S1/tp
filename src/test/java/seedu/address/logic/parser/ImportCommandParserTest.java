package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_FILE_OUTSIDE_PROJECT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ImportCommandParserTest {

    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ImportCommand.MESSAGE_USAGE), () -> parser.parse("     "));
    }

    @Test
    public void parse_validPaths_returnsImportCommand() throws ParseException {
        // Simple filename in parent directory
        String filename = "students.csv";
        ImportCommand expectedCommand = new ImportCommand(filename);
        assertEquals(expectedCommand, parser.parse(filename));

        // Home directory path
        String homePath = "~/documents/students.csv";
        ImportCommand expectedHomeCommand = new ImportCommand(homePath);
        assertEquals(expectedHomeCommand, parser.parse(homePath));

        // Home directory path with multiple levels
        String deepHomePath = "~/semester1/class2/data/students.csv";
        ImportCommand expectedDeepHomeCommand = new ImportCommand(deepHomePath);
        assertEquals(expectedDeepHomeCommand, parser.parse(deepHomePath));
    }

    @Test
    public void parse_invalidPaths_throwsParseException() {
        // Test parent directory traversal
        assertThrows(ParseException.class,
                MESSAGE_FILE_OUTSIDE_PROJECT, () -> parser.parse("../students.csv"));

        // Test multiple parent directory traversal
        assertThrows(ParseException.class,
                MESSAGE_FILE_OUTSIDE_PROJECT, () -> parser.parse("../../students.csv"));

        // Test absolute path
        assertThrows(ParseException.class,
                MESSAGE_FILE_OUTSIDE_PROJECT, () -> parser.parse("/absolute/path/students.csv"));

        // Test current directory reference
        assertThrows(ParseException.class,
                MESSAGE_FILE_OUTSIDE_PROJECT, () -> parser.parse("./students.csv"));

        // Test complex path with both current and parent references
        assertThrows(ParseException.class,
                MESSAGE_FILE_OUTSIDE_PROJECT, () -> parser.parse("./folder/../students.csv"));
    }

    @Test
    public void parse_pathsWithSpaces_returnsImportCommand() throws ParseException {
        // Filename with spaces
        String filename = "student data.csv";
        ImportCommand expectedCommand = new ImportCommand(filename);
        assertEquals(expectedCommand, parser.parse(filename));

        // Home path with spaces
        String homePath = "~/my documents/student data.csv";
        ImportCommand expectedHomeCommand = new ImportCommand(homePath);
        assertEquals(expectedHomeCommand, parser.parse(homePath));
    }
}
