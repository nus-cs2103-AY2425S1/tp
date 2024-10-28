package seedu.address.logic.commands.consultation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalConsultations.getTypicalConsultations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.consultation.Consultation;
import seedu.address.testutil.ModelStub;

public class ExportConsultCommandTest {

    @TempDir
    public Path temporaryFolder;

    private Path dataDir;
    private ModelStubWithConsultations model;
    private Path homeDir;
    private List<Path> filesToCleanup;

    @BeforeEach
    public void setUp() throws IOException {
        dataDir = temporaryFolder.resolve("data");
        Files.createDirectories(dataDir);
        model = new ModelStubWithConsultations(getTypicalConsultations());
        homeDir = Paths.get(System.getProperty("user.home"));
        filesToCleanup = new ArrayList<>();
    }

    @AfterEach
    public void tearDown() throws IOException {
        for (Path file : filesToCleanup) {
            Files.deleteIfExists(file);
        }

        if (Files.exists(dataDir)) {
            Files.walk(dataDir)
                    .sorted((a, b) -> b.compareTo(a))
                    .forEach(ExportConsultCommandTest::ignoreDeletionErrors);
        }
    }

    /**
     * Helper function to delete a file given its path, and to ignore deletion errors in cleanup
     *
     * @param path Path representing the file to be deleted
     */
    private static void ignoreDeletionErrors(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            // Ignore deletion errors during cleanup
        }
    }

    @Test
    public void equals() {
        ExportConsultCommand command1 = new ExportConsultCommand("file1", false, dataDir);
        ExportConsultCommand command2 = new ExportConsultCommand("file2", false, dataDir);
        ExportConsultCommand command3 = new ExportConsultCommand("file1", true, dataDir);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        ExportConsultCommand command1Copy = new ExportConsultCommand("file1", false, dataDir);
        assertTrue(command1.equals(command1Copy));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different file -> returns false
        assertFalse(command1.equals(command2));

        // same file different force flag -> returns false
        assertFalse(command1.equals(command3));
    }

    @Test
    public void getCommandType() {
        ExportConsultCommand exportCommand = new ExportConsultCommand("file1", false, dataDir);
        assertEquals(CommandType.EXPORTCONSULT, exportCommand.getCommandType());
    }

    @Test
    public void execute_normalExport_success() throws CommandException, IOException {
        String filename = "test";
        Path dataFile = dataDir.resolve(filename + ".csv");
        Path testHomeDir = temporaryFolder.resolve("home");
        Files.createDirectories(testHomeDir);
        Path homeFile = testHomeDir.resolve(filename + ".csv");

        filesToCleanup.add(dataFile);
        filesToCleanup.add(homeFile);

        ExportConsultCommand exportCommand = new ExportConsultCommand(filename, false, dataDir) {
            @Override
            protected Path getHomeFilePath(String filename) {
                return testHomeDir.resolve(filename + ".csv");
            }
        };

        CommandResult result = exportCommand.execute(model);

        String expectedMessage = String.format(ExportConsultCommand.MESSAGE_SUCCESS_WITH_COPY,
                getTypicalConsultations().size(), dataFile, homeFile);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        verifyExportedFileContent(dataFile);
        verifyExportedFileContent(homeFile);
    }

    private void verifyExportedFileContent(Path filePath) throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        assertEquals("Date,Time,Students", lines.get(0));

        List<String> expectedLines = getTypicalConsultations().stream()
                .map(this::consultationToCsvLine)
                .collect(Collectors.toList());

        // Skip header line when comparing
        List<String> actualLines = lines.subList(1, lines.size());
        assertEquals(expectedLines, actualLines);
    }

    private String consultationToCsvLine(Consultation consultation) {
        String studentsString = consultation.getStudents().stream()
                .map(student -> student.getName().fullName)
                .collect(Collectors.joining(";"));
        return String.format("%s,%s,%s",
                consultation.getDate().getValue(),
                consultation.getTime().getValue(),
                studentsString);
    }

    @Test
    public void testEscapeSpecialCharactersWithQuotes() {
        ExportConsultCommand exportCommand = new ExportConsultCommand("test", false, dataDir);

        // Test double quotes are properly escaped
        String withDoubleQuotes = "Hello\"World";
        assertEquals("\"Hello\"\"World\"", exportCommand.escapeSpecialCharacters(withDoubleQuotes));

        // Test both quotes and commas
        String withQuotesAndCommas = "Hello\"World,Test";
        assertEquals("\"Hello\"\"World,Test\"", exportCommand.escapeSpecialCharacters(withQuotesAndCommas));
    }

    @Test
    public void execute_directoryCreationFails_throwsCommandException() throws IOException {
        // Create a read-only parent directory
        Path readOnlyDir = temporaryFolder.resolve("readonly");
        Files.createDirectory(readOnlyDir);
        readOnlyDir.toFile().setReadOnly();

        // Try to create a subdirectory in the read-only directory
        Path invalidDir = readOnlyDir.resolve("data");
        ExportConsultCommand exportCommand = new ExportConsultCommand("test", false, invalidDir);

        try {
            assertThrows(CommandException.class, () -> exportCommand.execute(model),
                    String.format(ExportConsultCommand.MESSAGE_FAILURE, "Could not create directory:"));
        } finally {
            // Clean up: Reset directory permissions so it can be deleted
            readOnlyDir.toFile().setWritable(true);
        }
    }

    @Test
    public void execute_homeDirectoryCopyFails_returnsSuccessWithDataFileOnly() throws Exception {
        String filename = "test";
        Path dataFile = dataDir.resolve(filename + ".csv");
        filesToCleanup.add(dataFile);

        // Create a command that simulates home directory copy failure
        ExportConsultCommand exportCommand = new ExportConsultCommand(filename, false, dataDir) {
            @Override
            protected Path getHomeFilePath(String filename) {
                // Return a path that will cause IOException during copy
                return temporaryFolder.resolve("nonexistent").resolve("directory").resolve(filename + ".csv");
            }
        };

        CommandResult result = exportCommand.execute(model);

        // Verify success message only mentions data file
        String expectedMessage = String.format(ExportConsultCommand.MESSAGE_SUCCESS,
                model.getFilteredConsultationList().size(), dataFile);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_writeFailure_cleansUpAndThrowsCommandException() throws IOException {
        String filename = "test";
        Path dataFile = dataDir.resolve(filename + ".csv");

        // Make the directory read-only after creation
        dataDir.toFile().setReadOnly();

        ExportConsultCommand exportCommand = new ExportConsultCommand(filename, false, dataDir);

        try {
            assertThrows(CommandException.class, () -> exportCommand.execute(model),
                    String.format(ExportConsultCommand.MESSAGE_FAILURE, ""));
            assertFalse(Files.exists(dataFile), "Partial file should be cleaned up");
        } finally {
            // Reset permissions for cleanup
            dataDir.toFile().setWritable(true);
        }
    }

    @Test
    public void getHomeFilePath_returnsCorrectPath() {
        ExportConsultCommand exportCommand = new ExportConsultCommand("test", false, dataDir);
        String filename = "testfile";
        Path expected = Paths.get(System.getProperty("user.home"), filename + ".csv");
        Path actual = exportCommand.getHomeFilePath(filename);
        assertEquals(expected, actual);

        // Test with different filename
        String anotherFilename = "another";
        Path anotherExpected = Paths.get(System.getProperty("user.home"), anotherFilename + ".csv");
        Path anotherActual = exportCommand.getHomeFilePath(anotherFilename);
        assertEquals(anotherExpected, anotherActual);
    }

    private class ModelStubWithConsultations extends ModelStub {
        private final List<Consultation> consultations;

        ModelStubWithConsultations(List<Consultation> consultations) {
            this.consultations = new ArrayList<>(consultations);
        }

        @Override
        public ObservableList<Consultation> getFilteredConsultationList() {
            return FXCollections.observableList(consultations);
        }
    }
}
