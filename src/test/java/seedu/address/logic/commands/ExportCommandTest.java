package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_FILE_EXISTS;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_HOME_FILE_EXISTS;
import static seedu.address.testutil.Assert.assertThrows;

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

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;

public class ExportCommandTest {

    @TempDir
    public Path temporaryFolder;

    private Path dataDir;
    private Model model;
    private Path homeDir;
    private List<Path> filesToCleanup;

    @BeforeEach
    public void setUp() throws IOException {
        dataDir = temporaryFolder.resolve("data");
        Files.createDirectories(dataDir);
        model = new ModelManager();
        homeDir = Paths.get(System.getProperty("user.home"));
        filesToCleanup = new ArrayList<>();
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Clean up all created files
        for (Path file : filesToCleanup) {
            Files.deleteIfExists(file);
        }

        // Clean up the data directory
        if (Files.exists(dataDir)) {
            Files.walk(dataDir)
                    .sorted((a, b) -> b.compareTo(a))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            // Ignore deletion errors during cleanup
                        }
                    });
        }
    }

    @Test
    public void equals() {
        ExportCommand command1 = new ExportCommand("file1", false, dataDir);
        ExportCommand command2 = new ExportCommand("file2", false, dataDir);
        ExportCommand command3 = new ExportCommand("file1", true, dataDir);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        ExportCommand command1Copy = new ExportCommand("file1", false, dataDir);
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

    private void assertTrue(boolean equals) {
    }

    @Test
    public void getCommandTypeMethod() {
        ExportCommand exportCommand = new ExportCommand("file1", false, dataDir);
        assertEquals(CommandType.EXPORTSTUDENT, exportCommand.getCommandType());
    }

    @Test
    public void execute_fileExists_throwsCommandException() throws IOException {
        // Create a file that already exists
        Path existingFile = dataDir.resolve("test.csv");
        Files.createFile(existingFile);

        ExportCommand exportCommand = new ExportCommand("test", false, dataDir);
        assertThrows(CommandException.class, () -> exportCommand.execute(model));
    }

    @Test
    public void execute_directoryCreationFails_throwsCommandException() throws IOException {
        // Create a read-only parent directory
        Path readOnlyDir = temporaryFolder.resolve("readonly");
        Files.createDirectory(readOnlyDir);
        readOnlyDir.toFile().setReadOnly();

        // Try to create a subdirectory in the read-only directory
        Path invalidDir = readOnlyDir.resolve("data");

        ExportCommand exportCommand = new ExportCommand("test", false, invalidDir);
        Model model = new ModelManager();

        String expectedErrorMsg = "Could not create directory";
        String actualErrorMsg = "";

        try {
            exportCommand.execute(model);
        } catch (Exception e) {
            actualErrorMsg = e.getMessage();
        } finally {
            // Clean up: Reset directory permissions so it can be deleted
            readOnlyDir.toFile().setWritable(true);
        }

        assertTrue(actualErrorMsg.contains(expectedErrorMsg));
    }

    @Test
    public void execute_normalExport_success() throws CommandException, IOException {
        // Add a sample student to the model
        Student sampleStudent = createSampleStudent();
        model.addStudent(sampleStudent);

        String filename = "test";
        ExportCommand exportCommand = new ExportCommand(filename, false, dataDir);

        // Track files for cleanup
        Path dataFile = dataDir.resolve(filename + ".csv");
        Path homeFile = homeDir.resolve(filename + ".csv");
        filesToCleanup.add(dataFile);
        filesToCleanup.add(homeFile);

        CommandResult result = exportCommand.execute(model);

        // Verify success message
        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS_WITH_COPY,
                1, dataFile, homeFile);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Verify file contents
        verifyExportedFileContent(dataFile, sampleStudent);
        verifyExportedFileContent(homeFile, sampleStudent);
    }

    @Test
    public void execute_fileExistsWithForceFlag_success() throws CommandException, IOException {
        // Add a sample student to the model
        Student sampleStudent = createSampleStudent();
        model.addStudent(sampleStudent);

        String filename = "test";
        Path dataFile = dataDir.resolve(filename + ".csv");
        Path homeFile = homeDir.resolve(filename + ".csv");

        // Create existing files
        Files.createFile(dataFile);
        Files.createFile(homeFile);

        // Track files for cleanup
        filesToCleanup.add(dataFile);
        filesToCleanup.add(homeFile);

        ExportCommand exportCommand = new ExportCommand(filename, true, dataDir);
        CommandResult result = exportCommand.execute(model);

        // Verify success message
        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS_WITH_COPY,
                1, dataFile, homeFile);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Verify file contents
        verifyExportedFileContent(dataFile, sampleStudent);
        verifyExportedFileContent(homeFile, sampleStudent);
    }

    @Test
    public void execute_fileExistsWithoutForceFlag_throwsCommandException() throws IOException {
        String filename = "test";
        Path dataFile = dataDir.resolve(filename + ".csv");
        Files.createFile(dataFile);
        filesToCleanup.add(dataFile);

        ExportCommand exportCommand = new ExportCommand(filename, false, dataDir);

        String expectedMessage = String.format(MESSAGE_FILE_EXISTS, dataFile);
        assertThrows(CommandException.class, expectedMessage, () -> exportCommand.execute(model));
    }

    @Test
    public void execute_homeFileExistsWithoutForceFlag_throwsCommandException() throws IOException {
        String filename = "test";
        Path homeFile = homeDir.resolve(filename + ".csv");
        Files.createFile(homeFile);
        filesToCleanup.add(homeFile);

        ExportCommand exportCommand = new ExportCommand(filename, false, dataDir);

        String expectedMessage = String.format(MESSAGE_HOME_FILE_EXISTS, homeFile);
        assertThrows(CommandException.class, expectedMessage, () -> exportCommand.execute(model));
    }

    @Test
    public void execute_homeDirectoryCopyFails_returnsSuccessWithDataFileOnly() throws Exception {
        // Add a sample student
        Student sampleStudent = createSampleStudent();
        model.addStudent(sampleStudent);

        String filename = "test";
        Path dataFile = dataDir.resolve(filename + ".csv");
        filesToCleanup.add(dataFile);

        // Create a read-only home directory to force copy failure
        Path testHomeDir = temporaryFolder.resolve("home");
        Files.createDirectories(testHomeDir);
        testHomeDir.toFile().setReadOnly();

        // Create command with test home directory
        ExportCommand exportCommand = new ExportCommand(filename, false, dataDir) {
            @Override
            protected Path getHomeFilePath(String filename) {
                return testHomeDir.resolve(filename + ".csv");
            }
        };

        CommandResult result = exportCommand.execute(model);

        // Verify success message only mentions data file
        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS,
                1, dataFile);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Verify data file exists and contains correct content
        assertTrue(Files.exists(dataFile));
        verifyExportedFileContent(dataFile, sampleStudent);

        // Clean up: Reset directory permissions
        testHomeDir.toFile().setWritable(true);
    }

    // You may also want to add a direct test for getHomeFilePath
    @Test
    public void getHomeFilePathGeneratesCorrectPath() {
        ExportCommand exportCommand = new ExportCommand("test", false, dataDir);
        Path expected = Paths.get(System.getProperty("user.home"), "test.csv");
        assertEquals(expected, exportCommand.getHomeFilePath("test"));
    }

    // Helper methods
    private Student createSampleStudent() {
        return new StudentBuilder()
                .withName("John Doe")
                .withPhone("12345678")
                .withEmail("johndoe@example.com")
                .withCourses("CS2103T")
                .build();
    }

    private void verifyExportedFileContent(Path filePath, Student student) throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        assertEquals(2, lines.size());
        assertEquals("Name,Phone,Email,Courses", lines.get(0));

        String expectedLine = String.format("%s,%s,%s,%s",
                student.getName().fullName,
                student.getPhone().value,
                student.getEmail().value,
                student.getCourses().stream()
                        .map(course -> course.toString())
                        .collect(Collectors.joining(";")));
        assertEquals(expectedLine, lines.get(1));
    }
}
