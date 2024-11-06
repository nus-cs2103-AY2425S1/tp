package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_INVALID_CSV_FORMAT;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains tests for the ImportCommand class, which imports contacts from a CSV file.
 */
public class ImportCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Tests successful execution of importing a valid CSV file with contacts.
     */
    @Test
    public void execute_validCsvFile_importSuccess() throws Exception {
        // Prepare a valid CSV file with valid contacts
        String validCsvContent = "name,category,studentID/industry,phone,email,address,tags\n"
                + "John Doe,student,A0123456X,98765432,john@example.com,123 Main St,friends colleagues\n"
                + "Alice Lee,company,Sponsor,87654321,alice@example.com,456 Business Ave,colleagues\n";

        Path tempFile = createTempFile(validCsvContent);
        ImportCommand importCommand = new ImportCommand(tempFile.toString());

        CommandResult result = importCommand.execute(model);
        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, tempFile)
                + "\nSuccessfully imported: 2 entries", result.getFeedbackToUser());

        Files.deleteIfExists(tempFile);
    }

    /**
     * Tests that importing a CSV file with an invalid category throws a CommandException.
     */
    @Test
    public void execute_invalidCategoryCsvFile_throwsCommandException() throws Exception {
        String invalidCategoryCsvContent = "name,category,studentID/industry,phone,email,address,tags\n"
                + "Invalid User,invalid,A0123456X,98765432,invaliduser@example.com,123 Invalid St,friends\n";

        Path tempFile = createTempFile(invalidCategoryCsvContent);
        ImportCommand importCommand = new ImportCommand(tempFile.toString());

        CommandException exception = assertThrows(CommandException.class, () -> importCommand.execute(model));
        assertEquals(ImportCommand.MESSAGE_INVALID_CATEGORY, exception.getMessage());

        Files.deleteIfExists(tempFile);
    }

    /**
     * Tests that importing a CSV file with missing fields throws a CommandException.
     */
    @Test
    public void execute_missingFieldCsvFile_throwsCommandException() throws Exception {
        String missingFieldCsvContent = "name,category,studentID/industry,phone,email,address,tags\n"
                + "John Doe,student,A0123456X,98765432,john@example.com\n"; // Missing address and tags

        Path tempFile = createTempFile(missingFieldCsvContent);
        ImportCommand importCommand = new ImportCommand(tempFile.toString());

        CommandException exception = assertThrows(CommandException.class, () -> importCommand.execute(model));
        assertEquals("Invalid CSV format", exception.getMessage());

        Files.deleteIfExists(tempFile);
    }

    /**
     * Tests that importing a student contact with a missing student ID throws a CommandException.
     */
    @Test
    public void execute_missingStudentId_throwsCommandException() throws Exception {
        String missingStudentIdCsvContent = "name,category,studentID/industry,phone,email,address,tags\n"
                + "John Doe,student,,98765432,john@example.com,123 Main St,friends\n"; // Missing studentId

        Path tempFile = createTempFile(missingStudentIdCsvContent);
        ImportCommand importCommand = new ImportCommand(tempFile.toString());

        CommandException exception = assertThrows(CommandException.class, () -> importCommand.execute(model));
        assertEquals("Missing Student ID for student category", exception.getMessage());

        Files.deleteIfExists(tempFile);
    }

    /**
     * Tests that importing a company contact with a missing industry throws a CommandException.
     */
    @Test
    public void execute_missingIndustry_throwsCommandException() throws Exception {
        String missingIndustryCsvContent = "name,category,studentID/industry,phone,email,address,tags\n"
                + "Alice Lee,company,,87654321,alice@example.com,456 Business Ave,colleagues\n"; // Missing industry

        Path tempFile = createTempFile(missingIndustryCsvContent);
        ImportCommand importCommand = new ImportCommand(tempFile.toString());

        CommandException exception = assertThrows(CommandException.class, () -> importCommand.execute(model));
        assertEquals("Missing Industry for company category", exception.getMessage());

        Files.deleteIfExists(tempFile);
    }

    /**
     * Tests that executing ImportCommand with an inaccessible file path throws a CommandException.
     */
    @Test
    void execute_inaccessibleFilePath_throwsCommandException() {
        String inaccessiblePath = System.getProperty("os.name").toLowerCase().contains("win")
                ? "C:\\Windows\\System32\\restricted-file.txt" // Use a restricted Windows path
                : "/root/restricted-file.txt"; // Unix-based restricted path

        // Initialize ImportCommand with inaccessiblePath
        ImportCommand importCommand = new ImportCommand(inaccessiblePath);

        assertThrows(CommandException.class, () -> {
            importCommand.execute(model);
        });
    }


    /**
     * Tests that ImportCommand objects with the same file path are considered equal.
     */
    @Test
    public void equals_sameFilePath_returnsTrue() {
        ImportCommand command1 = new ImportCommand("/valid/path/to/file.csv");
        ImportCommand command2 = new ImportCommand("/valid/path/to/file.csv");

        assertEquals(command1, command2);
    }

    /**
     * Tests that ImportCommand objects with different file paths are not equal.
     */
    @Test
    public void equals_differentFilePath_returnsFalse() {
        ImportCommand command1 = new ImportCommand("/valid/path/to/file1.csv");
        ImportCommand command2 = new ImportCommand("/valid/path/to/file2.csv");

        assertNotEquals(command1, command2);
    }

    /**
     * Tests that ImportCommand objects with the same file path have the same hash code.
     */
    @Test
    public void hashCode_sameFilePath_returnsSameHash() {
        ImportCommand command1 = new ImportCommand("/valid/path/to/file.csv");
        ImportCommand command2 = new ImportCommand("/valid/path/to/file.csv");

        assertEquals(command1.hashCode(), command2.hashCode());
    }

    /**
     * Tests that ImportCommand objects with different file paths have different hash codes.
     */
    @Test
    public void hashCode_differentFilePath_returnsDifferentHash() {
        ImportCommand command1 = new ImportCommand("/valid/path/to/file1.csv");
        ImportCommand command2 = new ImportCommand("/valid/path/to/file2.csv");

        assertNotEquals(command1.hashCode(), command2.hashCode());
    }

    /**
     * Tests importing a CSV file where address values are enclosed in quotes due to commas.
     */
    @Test
    public void execute_escapedQuotesInAddress_importSuccess() throws Exception {
        String csvContent = "name,category,studentID/industry,phone,email,address,tags\n"
                + "John Doe,student,A0123456X,98765432,john@example.com,\"123, Main St, Apt 4B\",friends\n";

        Path tempFile = createTempFile(csvContent);
        ImportCommand importCommand = new ImportCommand(tempFile.toString());

        CommandResult result = importCommand.execute(model);
        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, tempFile.toString())
                + "\nSuccessfully imported: 1 entries", result.getFeedbackToUser());

        Files.deleteIfExists(tempFile);
    }

    /**
     * Tests that importing an empty CSV file (with only a header) results in no contacts being imported.
     */
    @Test
    public void execute_emptyCsvFile_noContactsImported() throws Exception {
        String emptyCsvContent = "name,category,studentID/industry,phone,email,address,tags\n";

        Path tempFile = createTempFile(emptyCsvContent);
        ImportCommand importCommand = new ImportCommand(tempFile.toString());

        CommandResult result = importCommand.execute(model);
        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, tempFile.toString())
                + "\nSuccessfully imported: 0 entries", result.getFeedbackToUser());

        Files.deleteIfExists(tempFile);
    }

    /**
     * Tests that importing a malformed CSV file with extra fields throws a CommandException.
     */
    @Test
    public void execute_malformedRow_throwsCommandException() throws Exception {
        String malformedCsvContent = "name,category,studentID/industry,phone,email,address,tags\n"
                + "John Doe,student,A0123456X,98765432,john@example.com,123 Main St,friends,colleagues,extra_field\n";

        Path tempFile = createTempFile(malformedCsvContent);
        ImportCommand importCommand = new ImportCommand(tempFile.toString());

        CommandException exception = assertThrows(CommandException.class, () -> importCommand.execute(model));
        assertEquals(MESSAGE_INVALID_CSV_FORMAT, exception.getMessage());

        Files.deleteIfExists(tempFile);
    }

    /**
     * Tests importing a CSV file with duplicate entries and verifies only unique entries are imported.
     */
    @Test
    public void execute_duplicateEntries_importsOnlyUnique() throws Exception {
        String csvContent = "name,category,studentID/industry,phone,email,address,tags\n"
                + "John Doe,student,A0123456X,98765432,john@example.com,123 Main St,friends\n"
                + "John Doe,student,A0123456X,98765432,john@example.com,123 Main St,friends\n";

        Path tempFile = createTempFile(csvContent);
        ImportCommand importCommand = new ImportCommand(tempFile.toString());

        CommandResult result = importCommand.execute(model);
        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, tempFile.toString())
                + "\nSuccessfully imported: 1 entries", result.getFeedbackToUser());

        Files.deleteIfExists(tempFile);
    }

    /**
     * Helper method to create a temporary CSV file with the specified content.
     *
     * @param content the content to be written to the temporary CSV file.
     * @return the Path of the created temporary file.
     * @throws Exception if an error occurs during file creation.
     */
    private Path createTempFile(String content) throws Exception {
        Path tempFile = Files.createTempFile("test", ".csv");
        Files.write(tempFile, content.getBytes());
        return tempFile;
    }
}
