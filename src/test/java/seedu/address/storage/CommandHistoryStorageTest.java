package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandHistoryStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "CommandHistoryTest");
    private Path testFilePath;
    private CommandHistoryStorage commandHistoryStorage;
    @BeforeEach
    void setUp() throws IOException {
        Files.createDirectories(TEST_DATA_FOLDER);
        testFilePath = TEST_DATA_FOLDER.resolve("commandHistory.txt");
        commandHistoryStorage = new CommandHistoryStorage();
        commandHistoryStorage.setCommandHistoryFilePath(testFilePath);
    }

    @Test
    void testWriteToFile() throws IOException {
        clearFile();

        String testCommand = "cadd n/winston p/99988811";
        CommandHistoryStorage.writeToFile(testCommand);

        String content = Files.readString(testFilePath);
        assertTrue(content.contains(testCommand));

        clearFile();
    }

    private void clearFile() {
        //Clears file after test
        try {
            FileWriter writer =
                    new FileWriter(String.valueOf(testFilePath), false); // 'false' to overwrite the file
            writer.write(""); // write an empty string to clear the file
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testClearFile() throws IOException {
        clearFile();
        String testCommand = "cadd n/winston p/99988811";
        CommandHistoryStorage.writeToFile(testCommand);

        CommandHistoryStorage.clearFile();

        String content = Files.readString(testFilePath);
        assertEquals("", content);
        clearFile();
    }

    @Test
    void testGetNextCommand() {
        clearFile();
        CommandHistoryStorage.initHistory();

        CommandHistoryStorage.writeToFile("BLANK TEST");
        CommandHistoryStorage.writeToFile("cadd n/winston p/99988811");
        CommandHistoryStorage.writeToFile("cdelete 1");

        commandHistoryStorage.updateCommandHistoryLines();

        String nextCommand = commandHistoryStorage.getNextCommand();
        assertEquals("", nextCommand);

        commandHistoryStorage.getPreviousCommand();
        commandHistoryStorage.getPreviousCommand();
        commandHistoryStorage.getPreviousCommand();

        nextCommand = commandHistoryStorage.getNextCommand();
        assertEquals("cadd n/winston p/99988811", nextCommand);

        nextCommand = commandHistoryStorage.getNextCommand();
        assertEquals("cdelete 1", nextCommand);
        clearFile();
    }

    @Test
    void testGetPreviousCommand() {
        clearFile();
        CommandHistoryStorage.initHistory();

        CommandHistoryStorage.writeToFile("cadd n/winston p/99988811");
        CommandHistoryStorage.writeToFile("cdelete 1");

        commandHistoryStorage.updateCommandHistoryLines();

        String previousCommand = commandHistoryStorage.getPreviousCommand();
        assertEquals("cdelete 1", previousCommand);

        previousCommand = commandHistoryStorage.getPreviousCommand();
        assertEquals("cadd n/winston p/99988811", previousCommand);
        clearFile();
    }

    @Test
    void testCountLinesInFile() {
        clearFile();

        // Write two commands
        CommandHistoryStorage.writeToFile("cadd n/winston p/99988811");
        CommandHistoryStorage.writeToFile("cdelete 1");

        int lineCount = CommandHistoryStorage.countLinesInFile();
        CommandHistoryStorage.initHistory();

        assertEquals(2, lineCount);
        clearFile();
    }
}
