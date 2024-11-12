package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ExportCommandTest {

    private Model model;
    private Model expectedModel;
    private Path filePath;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        filePath = ExportCommand.FILE_PATH; // Use the Path from ExportCommand
    }

    @Test
    public void execute_fileDoesNotExist_success() throws IOException {
        // Ensure the directory exists for the test
        Files.createDirectories(filePath.getParent());
        // Delete the file if it already exists before the test
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
        assertCommandSuccess(new ExportCommand(), model, ExportCommand.MESSAGE_SUCCESS, expectedModel);
        assertTrue(Files.exists(filePath));
    }

    @Test
    public void execute_typicalAddressBook_success() throws IOException {
        // Ensure the directory exists for the test
        Files.createDirectories(filePath.getParent());
        assertCommandSuccess(new ExportCommand(), model, ExportCommand.MESSAGE_SUCCESS, expectedModel);

        // Verify the content of the exported CSV
        Path expectedExportedContacts = Paths.get("./src/test/data/CsvTest/ExpectedExportedContacts.csv");

        try (BufferedReader br1 = new BufferedReader(new FileReader(filePath.toFile()));
             BufferedReader br2 = new BufferedReader(new FileReader(expectedExportedContacts.toFile()))) {

            String line;
            String expectedLine;

            // Check if the expected file has all the lines
            while ((expectedLine = br2.readLine()) != null) {
                line = br1.readLine();
                assertTrue(line != null && line.equals(expectedLine), "Mismatch at line: " + expectedLine);
            }
            // Ensure there are no extra lines in the exported file
            assertTrue(br1.readLine() == null, "Exported file has extra lines.");
        }
    }

    @Test
    public void cleanup() throws IOException {
        // Clean up the generated file after tests
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
    }
}
