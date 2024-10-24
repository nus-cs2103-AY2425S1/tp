package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.util.JsonUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.security.EncryptionManager;

public class ExportCommandTest {
    private static Path tempSrcPath;
    private static Path tempDstPath;
    private static String tempKeyPath;
    @BeforeEach
    public void setUp() {
        // 1. Create temporary source and destination files to not tamper with current data.
        tempSrcPath = null;
        tempDstPath = null;
        tempKeyPath = "test/key.txt";

        // 2. Populate sample source file with sample json data.
        try {
            Files.createDirectory(Path.of("test"));
            tempSrcPath = Files.createFile(Path.of("test/addressbook.json"));
            tempDstPath = Files.createFile(Path.of("test/dst-addressbook.json"));
            ReadOnlyAddressBook sampleData = SampleDataUtil.getSampleAddressBook();
            String stringData = JsonUtil.toJsonString(sampleData);
            byte[] encryptedData = EncryptionManager.encrypt(stringData, tempKeyPath);
            Files.write(tempSrcPath, encryptedData, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    public void cleanUp() {
        // 1. Clean up temporary files
        try {
            Files.deleteIfExists(tempSrcPath);
            Files.deleteIfExists(tempDstPath);
            Files.deleteIfExists(Path.of(tempKeyPath));
            Files.deleteIfExists(Path.of("test"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_validDestination_success() {
        Model model = new ModelManager();

        // Act
        File src = tempSrcPath.toFile();
        File dst = tempDstPath.toFile();

        ExportCommand exportCommand = new ExportCommand(dst, src, tempKeyPath);
        CommandResult result = exportCommand.execute(model);

        // Assert
        assertEquals(ExportCommand.MESSAGE_SUCCESS + " Data saved to: " + dst.getAbsolutePath(),
                result.getFeedbackToUser());
        assertTrue(dst.exists());
        assertTrue(dst.length() > 0, "Exported file should not be empty.");
    }
}
