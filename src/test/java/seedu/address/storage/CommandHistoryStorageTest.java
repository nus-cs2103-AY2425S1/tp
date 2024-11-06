package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        testFilePath = TEST_DATA_FOLDER.resolve("commandHistoryTest.txt");
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
    void testGetNextCommand_empty() {
        String nextCommand = commandHistoryStorage.getNextCommand();
        assertEquals("", nextCommand);
    }

    @Test
    void testGetPrevCommand_empty() {
        String prevCommand = commandHistoryStorage.getPreviousCommand();
        assertEquals("", prevCommand);
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

    @Test
    void testEquals_sameInstance() {
        assertTrue(commandHistoryStorage.equals(commandHistoryStorage),
                "An instance should be equal to itself");
    }

    @Test
    void testEquals_null() {
        assertFalse(commandHistoryStorage.equals(null), "An instance should not be equal to null");
    }

    @Test
    void testEquals_differentClass() {
        assertFalse(commandHistoryStorage.equals("some string"),
                "An instance should not be equal to an object of a different class");
    }

    @Test
    void testEquals_identicalFields() {
        CommandHistoryStorage anotherInstance = new CommandHistoryStorage();
        anotherInstance.setCommandHistoryFilePath(testFilePath);

        assertTrue(commandHistoryStorage.equals(anotherInstance),
                "Instances with identical fields should be equal");
    }
}
