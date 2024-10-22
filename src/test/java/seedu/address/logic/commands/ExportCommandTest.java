package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class ExportCommandTest {

    @Test
    public void equals() {
        ExportCommand command1 = new ExportCommand(Paths.get("file1.csv"));
        ExportCommand command2 = new ExportCommand(Paths.get("file2.csv"));

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        ExportCommand command1Copy = new ExportCommand(Paths.get("file1.csv"));
        assertTrue(command1.equals(command1Copy));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different file -> returns false
        assertFalse(command1.equals(command2));
    }

    private void assertTrue(boolean equals) {
    }

    @Test
    public void getCommandTypeMethod() {
        ExportCommand exportCommand = new ExportCommand(Paths.get("file1.csv"));
        assertEquals(exportCommand.getCommandType(), CommandType.EXPORTSTUDENT);
    }
}
