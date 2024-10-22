package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ExportCommandTest {
    private static final Path ADDRESSBOOK_FILE_PATH = Paths.get("data", "addressbook.json");
    private final Model model = new ModelManager();

    @Test
    public void execute_export_success() throws IOException {
        ExportCommand exportCommand = new ExportCommand();

        File file = ADDRESSBOOK_FILE_PATH.toFile();
        if (file.exists()) {
            Files.delete(ADDRESSBOOK_FILE_PATH);
        }
        assertTrue(!file.exists(), "Ensure file does not exist before export command");

        CommandResult result = exportCommand.execute(model);

        assertTrue(file.exists(), "File should be created by the export command");
        assertEquals(ExportCommand.MESSAGE_EXPORT_ACKNOWLEDGEMENT, result.getFeedbackToUser());

        Files.delete(ADDRESSBOOK_FILE_PATH); // clean up
    }

    @Test
    public void execute_export_fail() throws IOException {
        ExportCommand exportCommand = new ExportCommand();

        Path dirPath = Paths.get("data");
        File dir = dirPath.toFile();

        if (!dir.exists()) {
            Files.createDirectories(dirPath);
        }

        dir.setWritable(false); // make the directory read-only

        try {
            CommandResult result = exportCommand.execute(model);
            assertEquals(ExportCommand.MESSAGE_EXPORT_FAIL, result.getFeedbackToUser());
        } finally {
            // clean up after the test
            dir.setWritable(true);
            Files.deleteIfExists(ADDRESSBOOK_FILE_PATH);
            Files.deleteIfExists(dirPath);
        }
    }
}
