package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalContacts;

public class ExportCommandTest {

    private Model model = new ModelManager(TypicalContacts.getTypicalAddressBook(), new UserPrefs());

    /**
     * Tests successful export to a valid CSV file path.
     */
    @Test
    public void execute_validFilePath_exportSuccess() throws Exception {
        Path tempFile = Paths.get(System.getProperty("user.dir"), "test-export-valid.csv");
        ExportCommand exportCommand = new ExportCommand(tempFile.toString());

        CommandResult result = exportCommand.execute(model);
        assertEquals(String.format(ExportCommand.MESSAGE_SUCCESS, tempFile.toString()), result.getFeedbackToUser());

        try (BufferedReader reader = Files.newBufferedReader(tempFile)) {
            String header = reader.readLine();
            assertEquals("name,category,studentId/industry,phone,email,address,tags", header);

            String firstLine = reader.readLine();
            assertEquals("Alice Pauline,student,A1234567X,94351253,alice@example.com,"
                    + "\"123, Jurong West Ave 6, #08-111\",friends", firstLine);
        }

        Files.deleteIfExists(tempFile);
    }

    /**
     * Tests that an invalid file path with restricted characters throws an IllegalArgumentException.
     */
    @Test
    public void execute_invalidFilePath_throwsIllegalArgumentException() {
        String invalidFilePath = "invalid:path?.csv";

        assertThrows(IllegalArgumentException.class, () -> new ExportCommand(invalidFilePath));
    }

    /**
     * Tests equality between two ExportCommand objects with the same file path.
     */
    @Test
    public void equals_sameFilePath_returnsTrue() {
        ExportCommand exportCommand1 = new ExportCommand("/valid/path/to/export1.csv");
        ExportCommand exportCommand2 = new ExportCommand("/valid/path/to/export1.csv");

        assertEquals(exportCommand1, exportCommand2);
    }

    /**
     * Tests that ExportCommand objects with different file paths are not equal.
     */
    @Test
    public void equals_differentFilePath_returnsFalse() {
        ExportCommand exportCommand1 = new ExportCommand("/valid/path/to/export1.csv");
        ExportCommand exportCommand2 = new ExportCommand("/valid/path/to/export2.csv");

        assertTrue(!exportCommand1.equals(exportCommand2));
    }

    /**
     * Tests successful export to an absolute file path.
     */
    @Test
    public void execute_absoluteFilePath_exportSuccess() throws Exception {
        Path tempDir = Files.createTempDirectory("exportTest");
        Path absolutePath = tempDir.resolve("export.csv").toAbsolutePath();

        ExportCommand exportCommand = new ExportCommand(absolutePath.toString());

        CommandResult result = exportCommand.execute(model);
        assertEquals(String.format(ExportCommand.MESSAGE_SUCCESS, absolutePath.toString()), result.getFeedbackToUser());

        Files.deleteIfExists(absolutePath);
        Files.deleteIfExists(tempDir);
    }

    /**
     * Tests successful export to a relative file path, verifying file creation in expected location.
     */
    @Test
    public void execute_relativeFilePath_exportSuccess() throws Exception {
        String relativeFilePath = "exportTestRelative.csv";
        ExportCommand exportCommand = new ExportCommand(relativeFilePath);

        try {
            CommandResult result = exportCommand.execute(model);

            Path expectedFilePath = Path.of(relativeFilePath).toAbsolutePath();
            assertEquals(String.format(ExportCommand.MESSAGE_SUCCESS, expectedFilePath.toString()),
                    result.getFeedbackToUser());

            assertTrue(Files.exists(expectedFilePath));
        } finally {
            Files.deleteIfExists(Path.of(relativeFilePath));
        }
    }

    /**
     * Tests that fields with special characters are correctly escaped for CSV format.
     */
    @Test
    public void escapeCsv_fieldWithSpecialCharacters_correctlyEscaped() {
        ExportCommand exportCommand = new ExportCommand("dummy/path.csv");

        assertEquals("\"123, Main St\"", exportCommand.escapeCsv("123, Main St"),
                "Address with comma should be enclosed in quotes");
        assertEquals("\"He said, \"\"Hello!\"\"\"", exportCommand.escapeCsv("He said, \"Hello!\""),
                "Quotes within text should be doubled and text enclosed in quotes");
        assertEquals("\"Line 1\nLine 2\"", exportCommand.escapeCsv("Line 1\nLine 2"),
                "Newline should trigger enclosing quotes");
        assertEquals("SimpleText", exportCommand.escapeCsv("SimpleText"),
                "Text without special characters should remain unchanged");
    }

    /**
     * Tests that file extension validation is enforced, throwing an IllegalArgumentException for non-CSV paths.
     */
    @Test
    public void execute_nonCsvFileExtension_throwsIllegalArgumentException() {
        String nonCsvFilePath = "exportTestInvalid.txt";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, (
                ) -> new ExportCommand(nonCsvFilePath));
        assertEquals(ExportCommand.MESSAGE_INVALID_FILE_EXT, exception.getMessage());
    }
}
