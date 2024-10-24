package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalContacts;

public class ExportCommandTest {

    private Model model = new ModelManager(TypicalContacts.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validFilePath_exportSuccess() throws Exception {
        // Prepare a temporary file
        Path tempFile = Files.createTempFile("test", ".csv");
        ExportCommand exportCommand = new ExportCommand(tempFile.toString());

        // Execute the command
        CommandResult result = exportCommand.execute(model);
        assertEquals(String.format(ExportCommand.MESSAGE_SUCCESS, tempFile.toString()), result.getFeedbackToUser());

        // Verify the file content
        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile.toString()))) {
            String header = reader.readLine();
            assertEquals("name,category,studentId/industry,phone,email,address,tags", header);

            // Check if content was written for one of the typical contacts (example: Alex Yeoh)
            String firstLine = reader.readLine();
            assertEquals("Alice Pauline,student,A1234567X,94351253,alice@example.com,123, Jurong West Ave 6, "
                    + "#08-111,friends", firstLine);
        }

        Files.deleteIfExists(tempFile); // Clean up the temporary file after the test
    }

    @Test
    public void execute_invalidFilePath_throwsCommandException() {
        String invalidFilePath = "/invalid/path/to/file.csv";
        ExportCommand exportCommand = new ExportCommand(invalidFilePath);

        // Check if CommandException is thrown
        CommandException exception = assertThrows(CommandException.class, () -> exportCommand.execute(model));
        assertEquals(String.format(ExportCommand.MESSAGE_FAILURE, invalidFilePath), exception.getMessage());
    }

    @Test
    public void equals_sameFilePath_returnsTrue() {
        ExportCommand exportCommand1 = new ExportCommand("/valid/path/to/export1.csv");
        ExportCommand exportCommand2 = new ExportCommand("/valid/path/to/export1.csv");

        assertEquals(exportCommand1, exportCommand2);
    }

    @Test
    public void equals_differentFilePath_returnsFalse() {
        ExportCommand exportCommand1 = new ExportCommand("/valid/path/to/export1.csv");
        ExportCommand exportCommand2 = new ExportCommand("/valid/path/to/export2.csv");

        assertThrows(AssertionError.class, () -> assertEquals(exportCommand1, exportCommand2));
    }
}
