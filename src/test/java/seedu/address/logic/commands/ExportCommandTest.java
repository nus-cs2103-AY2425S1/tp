package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ExportCommandTest {
    private static final String ADDRESSBOOK_FILE_PATH = "data/addressbook.json";
    private final Model model = new ModelManager();

    @Test
    public void execute_export_success() {
        ExportCommand exportCommand = new ExportCommand();

        File file = new File(ADDRESSBOOK_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
        assertTrue(!file.exists(), "Ensure file does not exist before export command");

        CommandResult result = exportCommand.execute(model);

        assertTrue(file.exists(), "File should be created by the export command");
        assertEquals(ExportCommand.MESSAGE_EXPORT_ACKNOWLEDGEMENT, result.getFeedbackToUser());

        file.delete(); // clean up
    }

    @Test
    public void execute_export_fail() throws IOException {
        ExportCommand exportCommand = new ExportCommand();

        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        dir.setWritable(false); // make the directory read-only

        try {
            CommandResult result = exportCommand.execute(model);
            assertEquals(ExportCommand.MESSAGE_EXPORT_FAIL, result.getFeedbackToUser());
        } finally {
            // clean up after the test
            dir.setWritable(true);
            Files.deleteIfExists(Path.of(ADDRESSBOOK_FILE_PATH));
            Files.deleteIfExists(dir.toPath());
        }
    }
}
