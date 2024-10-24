package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ListArchiveFilesCommandTest {
    private Path archiveDir;
    private Model modelStub;

    @BeforeEach
    public void setUp() throws IOException {
        modelStub = new ModelManager();
        archiveDir = Files.createDirectories(Path.of(modelStub.getAddressBookFilePath().getParent().toString(),
                "archive"));
    }

    @AfterEach
    public void tearDown() throws IOException {
        if (!Files.exists(archiveDir)) {
            return;
        }

        Files.walk(archiveDir)
                .map(Path::toFile)
                .forEach(file -> {
                    if (!file.delete()) {
                        file.deleteOnExit();
                    }
                });
    }

    @Test
    public void execute_noArchiveDirectory_success() throws IOException {
        Files.deleteIfExists(archiveDir);
        assertCommandSuccess(new ListArchiveFilesCommand(), modelStub, ListArchiveFilesCommand.MESSAGE_NO_ARCHIVE,
                modelStub);
    }

    @Test
    public void execute_noArchiveFiles_success() {
        assertCommandSuccess(new ListArchiveFilesCommand(), modelStub, ListArchiveFilesCommand.MESSAGE_NO_ARCHIVE,
                modelStub);
    }

    @Test
    public void execute_withArchiveFiles_success() throws IOException {
        Files.createFile(Path.of(archiveDir.toString(), "archive.json"));
        String expectedMessage = ListArchiveFilesCommand.MESSAGE_SUCCESS + "\n" + "archive.json";
        assertCommandSuccess(new ListArchiveFilesCommand(), modelStub, expectedMessage, modelStub);
    }

    @Test
    public void execute_withMultipleArchiveFiles_success() throws IOException {
        Files.createFile(Path.of(archiveDir.toString(), "multiple_archive1.json"));
        Files.createFile(Path.of(archiveDir.toString(), "multiple_archive2.json"));
        String expectedMessage = ListArchiveFilesCommand.MESSAGE_SUCCESS + "\n" + "multiple_archive1.json\n"
                + "multiple_archive2.json";
        assertCommandSuccess(new ListArchiveFilesCommand(), modelStub, expectedMessage, modelStub);
    }
}
