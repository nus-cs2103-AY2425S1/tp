package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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
