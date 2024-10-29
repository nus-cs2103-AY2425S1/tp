package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ImportCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validCsvFile_importSuccess() throws Exception {
        // Prepare a valid CSV file with valid contacts
        String validCsvContent = "name,category,studentID/industry,phone,email,address,tags\n"
                + "John Doe,student,A0123456X,98765432,john@example.com,123 Main St,friends,colleagues\n"
                + "Alice Lee,company,Sponsor,87654321,alice@example.com,456 Business Ave,colleagues\n";

        Path tempFile = createTempFile(validCsvContent);
        ImportCommand importCommand = new ImportCommand(tempFile.toString());

        // Check if command executes successfully
        CommandResult result = importCommand.execute(model);
        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, tempFile.toString())
                + "\nSuccessfully imported: 2 entries", result.getFeedbackToUser());

        Files.deleteIfExists(tempFile);
    }

    @Test
    public void execute_invalidCategoryCsvFile_throwsCommandException() throws Exception {
        // Prepare a CSV file with an invalid category
        String invalidCategoryCsvContent = "name,category,studentID/industry,phone,email,address,tags\n"
                + "Invalid User,invalid,A0123456X,98765432,invaliduser@example.com,123 Invalid St,friends\n";

        Path tempFile = createTempFile(invalidCategoryCsvContent);
        ImportCommand importCommand = new ImportCommand(tempFile.toString());

        // Check if CommandException is thrown
        CommandException exception = assertThrows(CommandException.class, () -> importCommand.execute(model));
        assertEquals(ImportCommand.MESSAGE_INVALID_CATEGORY, exception.getMessage());

        Files.deleteIfExists(tempFile);
    }

    @Test
    public void execute_missingFieldCsvFile_throwsCommandException() throws Exception {
        // Prepare a CSV file with missing fields
        String missingFieldCsvContent = "name,category,studentID/industry,phone,email,address,tags\n"
                + "John Doe,student,A0123456X,98765432,john@example.com\n"; // Missing address and tags

        Path tempFile = createTempFile(missingFieldCsvContent);
        ImportCommand importCommand = new ImportCommand(tempFile.toString());

        // Check if CommandException is thrown
        CommandException exception = assertThrows(CommandException.class, () -> importCommand.execute(model));
        assertEquals("Invalid CSV format", exception.getMessage());

        Files.deleteIfExists(tempFile);
    }

    @Test
    public void execute_missingStudentId_throwsCommandException() throws Exception {
        // Prepare a CSV file where student category has missing studentId
        String missingStudentIdCsvContent = "name,category,studentID/industry,phone,email,address,tags\n"
                + "John Doe,student,,98765432,john@example.com,123 Main St,friends\n"; // Missing studentId

        Path tempFile = createTempFile(missingStudentIdCsvContent);
        ImportCommand importCommand = new ImportCommand(tempFile.toString());

        // Check if CommandException is thrown
        CommandException exception = assertThrows(CommandException.class, () -> importCommand.execute(model));
        assertEquals("Missing Student ID for student category", exception.getMessage());

        Files.deleteIfExists(tempFile);
    }

    @Test
    public void execute_missingIndustry_throwsCommandException() throws Exception {
        // Prepare a CSV file where company category has missing industry
        String missingIndustryCsvContent = "name,category,studentID/industry,phone,email,address,tags\n"
                + "Alice Lee,company,,87654321,alice@example.com,456 Business Ave,colleagues\n"; // Missing industry

        Path tempFile = createTempFile(missingIndustryCsvContent);
        ImportCommand importCommand = new ImportCommand(tempFile.toString());

        // Check if CommandException is thrown
        CommandException exception = assertThrows(CommandException.class, () -> importCommand.execute(model));
        assertEquals("Missing Industry for company category", exception.getMessage());

        Files.deleteIfExists(tempFile);
    }

    @Test
    public void execute_invalidFilePath_throwsCommandException() {
        // Provide an invalid file path
        String invalidFilePath = "/invalid/path/to/file.csv";
        ImportCommand importCommand = new ImportCommand(invalidFilePath);

        // Check if CommandException is thrown
        CommandException exception = assertThrows(CommandException.class, () -> importCommand.execute(model));
        assertEquals(String.format(ImportCommand.MESSAGE_FAILURE, invalidFilePath), exception.getMessage());
    }

    @Test
    public void equals_sameFilePath_returnsTrue() {
        // Test the equals method
        ImportCommand command1 = new ImportCommand("/valid/path/to/file.csv");
        ImportCommand command2 = new ImportCommand("/valid/path/to/file.csv");

        assertEquals(command1, command2);
    }

    @Test
    public void equals_differentFilePath_returnsFalse() {
        // Test the equals method with different file paths
        ImportCommand command1 = new ImportCommand("/valid/path/to/file1.csv");
        ImportCommand command2 = new ImportCommand("/valid/path/to/file2.csv");

        assertNotEquals(command1, command2);
    }

    @Test
    public void hashCode_sameFilePath_returnsSameHash() {
        // Test the hashCode method
        ImportCommand command1 = new ImportCommand("/valid/path/to/file.csv");
        ImportCommand command2 = new ImportCommand("/valid/path/to/file.csv");

        assertEquals(command1.hashCode(), command2.hashCode());
    }

    @Test
    public void hashCode_differentFilePath_returnsDifferentHash() {
        // Test the hashCode method with different file paths
        ImportCommand command1 = new ImportCommand("/valid/path/to/file1.csv");
        ImportCommand command2 = new ImportCommand("/valid/path/to/file2.csv");

        assertNotEquals(command1.hashCode(), command2.hashCode());
    }

    /**
     * Helper method to create a temporary CSV file with the specified content.
     */
    private Path createTempFile(String content) throws Exception {
        Path tempFile = Files.createTempFile("test", ".csv");
        Files.write(tempFile, content.getBytes());
        return tempFile;
    }
}
