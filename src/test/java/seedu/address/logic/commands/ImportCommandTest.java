package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;

import org.junit.jupiter.api.Test;

public class ImportCommandTest {

    @Test
    public void importCommand_findsCorrectFile() {
        File expectedFile = new File("Import");
        ImportCommand testImportCommand = new ImportCommand();
        assertEquals(expectedFile, testImportCommand.getToImport());
        assertEquals(expectedFile.getName(), testImportCommand.toString());
    }

    @Test
    public void equals() {
        ImportCommand testImportCommand = new ImportCommand();
        ListCommand testListCommand = new ListCommand();
        assertEquals(testImportCommand, testImportCommand);
        assertNotEquals(testImportCommand, testListCommand);
    }
}
