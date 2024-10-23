package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ExportCommandTest {

    @TempDir
    Path temporaryFolder;
    @Test
    public void execute_validDestination_success() {
        Model model = new ModelManager();
        File destinationFile = temporaryFolder.resolve("exported_addressbook.json").toFile();
        ExportCommand exportCommand = new ExportCommand(destinationFile);

        // Act
        CommandResult result = exportCommand.execute(model);

        // Assert
        assertEquals(ExportCommand.MESSAGE_SUCCESS + " Data saved to: " + destinationFile.getAbsolutePath(),
                result.getFeedbackToUser());
        assertTrue(destinationFile.exists());
        assertTrue(destinationFile.length() > 0, "Exported file should not be empty.");
    }

}
