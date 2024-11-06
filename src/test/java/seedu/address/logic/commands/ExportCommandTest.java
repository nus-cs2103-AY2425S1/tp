package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleAssignmentsUtil;

/**
 * Contains integration tests for the {@code ExportCommand} class.
 * Verifies the correct behavior of the command under various scenarios.
 */
public class ExportCommandTest {
    private final Model model = new ModelManager(
        getTypicalAddressBook(),
        new UserPrefs(),
        SampleAssignmentsUtil.getSamplePredefinedAssignments());

    /**
     * Tests the successful execution of the ExportCommand when given a valid file path.
     *
     * @throws CommandException if the command execution fails unexpectedly.
     */
    @Test
    public void validExportCommandResult_success() throws CommandException, IOException {
        // Get the project root directory path
        String projectDir = System.getProperty("user.dir");
        String filePath = projectDir + "/src/test/data/testExport.csv";

        ExportCommand command = new ExportCommand(filePath);
        CommandResult commandResult = command.execute(model);
        assertEquals("Exported " + getTypicalAddressBook().getPersonList().size()
            + " persons to CSV.", commandResult.getFeedbackToUser());
        Path path = Paths.get(filePath);
        Files.deleteIfExists(path);
    }


    /**
     * Tests that an exception is thrown when attempting to create directories with an invalid path.
     */
    @Test
    public void createDirectories_invalidPath_exception() {
        // Get the project root directory path
        String projectDir = System.getProperty("user.dir");
        String invalidPath = String.format("%s%s%s",
            projectDir,
            File.separator,
            // Characters that are illegal on both Windows and Unix
            "\0illegal\0path.csv");

        ExportCommand exportCommand = new ExportCommand(invalidPath);
        assertCommandFailure(exportCommand, model, "Error writing to the CSV file: Invalid file path");
    }

    /**
     * Tests that an exception is thrown when there is a path conflict with an existing file.
     *
     * @throws IOException if there is an issue creating the temporary file for testing.
     */
    @Test
    public void createDirectories_pathConflict_exception() throws IOException {
        String projectDir = System.getProperty("user.dir");
        File tempFile = new File(projectDir + "/conflict.txt");
        tempFile.createNewFile(); // Create the file to simulate a conflict
        // Provide a path that conflicts with the existing file
        String conflictingPath = projectDir + "/conflict.txt/nestedDir/test.csv";
        ExportCommand exportCommand = new ExportCommand(conflictingPath);
        assertCommandFailure(exportCommand, model, "Failed to create directory structure for: "
            + conflictingPath);
        tempFile.delete();
    }

}
