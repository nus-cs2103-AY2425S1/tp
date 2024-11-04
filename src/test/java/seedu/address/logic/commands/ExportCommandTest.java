package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalContacts;

public class ExportCommandTest {

    private Model model = new ModelManager(TypicalContacts.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validFilePath_exportSuccess() throws Exception {
        // Prepare a temporary file
        Path tempFile = Files.createTempFile("test", ".csv");
        ExportCommand exportCommand = new ExportCommand(tempFile.toString());

        // Execute the command
        CommandResult result = exportCommand.execute(model);
        assertEquals(String.format(ExportCommand.MESSAGE_SUCCESS, tempFile.toString()), result.getFeedbackToUser());

        // Verify the file content
        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile.toString()))) {
            String header = reader.readLine();
            assertEquals("name,category,studentId/industry,phone,email,address,tags", header);

            // Check if content was written for one of the typical contacts (example: Alex Yeoh)
            String firstLine = reader.readLine();
            assertEquals("Alice Pauline,student,A1234567X,94351253,alice@example.com,"
                    + "\"123, Jurong West Ave 6, #08-111\",friends", firstLine);
        }

        Files.deleteIfExists(tempFile); // Clean up the temporary file after the test
    }

    @Test
    public void execute_invalidFilePath_throwsIllegalArgumentException() {
        String invalidFilePath = "invalid:path?.csv"; // Example with invalid characters

        assertThrows(IllegalArgumentException.class, () -> new ExportCommand(invalidFilePath));
    }


    @Test
    public void equals_sameFilePath_returnsTrue() {
        ExportCommand exportCommand1 = new ExportCommand("/valid/path/to/export1.csv");
        ExportCommand exportCommand2 = new ExportCommand("/valid/path/to/export1.csv");

        assertEquals(exportCommand1, exportCommand2);
    }

    @Test
    public void equals_differentFilePath_returnsFalse() {
        ExportCommand exportCommand1 = new ExportCommand("/valid/path/to/export1.csv");
        ExportCommand exportCommand2 = new ExportCommand("/valid/path/to/export2.csv");

        assertThrows(AssertionError.class, () -> assertEquals(exportCommand1, exportCommand2));
    }

    @Test
    public void execute_absoluteFilePath_exportSuccess() throws Exception {
        Path tempDir = Files.createTempDirectory("exportTest");
        Path absolutePath = tempDir.resolve("export.csv");

        ExportCommand exportCommand = new ExportCommand(absolutePath.toString());

        CommandResult result = exportCommand.execute(model);
        assertEquals(String.format(ExportCommand.MESSAGE_SUCCESS, absolutePath.toString()), result.getFeedbackToUser());

        // Clean up
        Files.deleteIfExists(absolutePath);
        Files.deleteIfExists(tempDir);
    }

    @Test
    public void execute_relativeFilePath_exportSuccess() throws Exception {
        // Define a relative path from the project root or from the current working directory
        String relativeFilePath = "exportTestRelative.csv";
        ExportCommand exportCommand = new ExportCommand(relativeFilePath);

        try {
            // Execute the command
            CommandResult result = exportCommand.execute(model);

            // Construct the absolute path to check if the file was created in the expected location
            Path expectedFilePath = Path.of(relativeFilePath).toAbsolutePath();

            // Assert the feedback message matches the expected output
            assertEquals(String.format(ExportCommand.MESSAGE_SUCCESS, expectedFilePath.toString()),
                    result.getFeedbackToUser());

            // Verify file existence as a side check for successful execution
            assertTrue(Files.exists(expectedFilePath));
        } finally {
            // Clean up after the test
            Files.deleteIfExists(Path.of(relativeFilePath));
        }
    }

    @Test
    public void escapeCsv_fieldWithSpecialCharacters_correctlyEscaped() {
        // Placeholder for context if escapeCsv is non-static
        ExportCommand exportCommand = new ExportCommand("dummy/path");

        // Define test cases with expected escaped output
        assertEquals("\"123, Main St\"", exportCommand.escapeCsv("123, Main St"),
                "Address with comma should be enclosed in quotes");
        assertEquals("\"He said, \"\"Hello!\"\"\"", exportCommand.escapeCsv("He said, \"Hello!\""),
                "Quotes within text should be doubled and text enclosed in quotes");
        assertEquals("\"Line 1\nLine 2\"", exportCommand.escapeCsv("Line 1\nLine 2"),
                "Newline should trigger enclosing quotes");
        assertEquals("SimpleText", exportCommand.escapeCsv("SimpleText"),
                "Text without special characters should remain unchanged");
    }

}
